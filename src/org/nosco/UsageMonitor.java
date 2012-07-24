package org.nosco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.nosco.DBQuery.Join;
import org.nosco.Field.FK;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;

class UsageMonitor<T extends Table> {

	private static final String TIME_STAMP = "ts";
	private static final String STACK_TRACE = "st";
	protected static final String USED_FIELDS = "uf";

	private static final int MILLIS_ONE_WEEK = 1000*60*60*24*7;

	private static final String WARN_OFF = "To turn these warnings off, "
			+ "call: Context.getThreadContext().enableUsageWarnings(false);";

	Map<StackTraceKey,M.Long> counter = new HashMap<StackTraceKey,M.Long>();

	private static final Logger log = Logger.getLogger("org.nosco.recommendations");
	long count = 0;
	private final StackTraceElement[] st;
	private final BitSet accessedField = new BitSet();
	private Field<?>[] selectedFields;
	private Collection<Field<?>> surpriseFields = null;
	private final String queryHash;

	private DBQuery<T> query;
	private boolean selectOptimized = false;

	@Override
	protected void finalize() throws Throwable {
		warnBadFKUsage();
		warnUnusedColumns();
		super.finalize();
	}

	static ConcurrentHashMap<String,Map<Field<?>,Long>> qc = new ConcurrentHashMap<String,Map<Field<?>,Long>>();
	static Map<String,Long> stLastSeen = Collections.synchronizedMap(new HashMap<String,Long>());
	static Set<String> stSeenThisRun = Collections.synchronizedSet(new HashSet<String>());

	private void warnUnusedColumns() {
		qc.putIfAbsent(queryHash, Collections.synchronizedMap(new HashMap<Field<?>,Long>()));
		final Map<Field<?>,Long> used = qc.get(queryHash);
		final Set<Field<?>> unusedColumns = new LinkedHashSet<Field<?>>();
		for (int i=0; i<selectedFields.length; ++i) {
			if (!accessedField.get(i)) {
				unusedColumns.add(selectedFields[i]);
			} else {
				final Long date = used.get(selectedFields[i]);
				if (date==null || (date+MILLIS_ONE_WEEK)<START) {
					used.put(selectedFields[i], System.currentTimeMillis());
				}
			}
		}
		if (surpriseFields!=null) {
			for (final Field<?> f : surpriseFields) {
				final Long date = used.get(f);
				if (date==null || (date+MILLIS_ONE_WEEK)<START) {
					used.put(f, System.currentTimeMillis());
				}
			}
		}
		removePKFieldsFromSet(unusedColumns);
		final List<String> unusedColumnDescs = new ArrayList<String>();
		for (final Field<?> field : unusedColumns) {
			unusedColumnDescs.add(field.TABLE.getSimpleName() +"."+ field.JAVA_NAME);
		}
		if (!selectOptimized && !unusedColumnDescs.isEmpty()) {
			final String msg = "The following columns were never accessed:\n\t"
					+ Util.join(", ", unusedColumnDescs) + "\nin the query created here:\n\t"
					+ Util.join("\n\t", (Object[]) st) + "\n"
					+ "You might consider not querying these fields by using the "
					+ "deferFields(Field<?>...) method on your query.\n"
					+ WARN_OFF;
			log.info(msg);
		}
	}

	UsageMonitor(final DBQuery<T> query) {
		if (loadPerformanceInfo.getState()!=Thread.State.TERMINATED) {
			try {
				loadPerformanceInfo.join();
			} catch (final InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		this.query = query;
		// grab the current stack trace
		final StackTraceElement[] tmp = Thread.currentThread().getStackTrace();
		st = new StackTraceElement[tmp.length-4];
		System.arraycopy(tmp, 4, st, 0, st.length);

		String hash = null;
		hash = Integer.toHexString(Util.join(",", st).hashCode());
//        try {
//        	final MessageDigest cript = MessageDigest.getInstance("SHA-1");
//        	cript.reset();
//        	for (final StackTraceElement ste : st) {
//        		cript.update(ste.toString().getBytes("UTF-8"));
//        	}
//        	cript.update(Integer.toString(query.hashCode()).getBytes("UTF-8"));
//        	hash = digestToString(cript.digest());
//        	final String s = Util.join(",", st);
//        	System.err.println(hash +"\t"+ Arrays.toString(cript.digest()) +"\t"+ s.hashCode());
//        } catch (final UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (final NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        queryHash = hash;
        stSeenThisRun.add(hash);
        //System.err.println("queryHash "+ queryHash +" "+ query.hashCode());
	}

	private String digestToString(final byte[] buf) {
		final char[] ret = new char[buf.length*2];
		for (int i=0; i<buf.length; ++i) {
			final int low = buf[i] & 0xF;
	        final int high = (buf[i] >> 8) & 0xF;
	        ret[i*2] = Character.forDigit(high, 16);
	        ret[i*2+1] = Character.forDigit(low, 16);
		}
		return new String(ret);
	}

	private void warnBadFKUsage() {
		if (count > 4) {
			for (final Entry<StackTraceKey, M.Long> e : counter.entrySet()) {
				final M.Long v = e.getValue();
				final long percent = v.i*100/count;
				if (percent > 50) {
					final StackTraceKey k = e.getKey();
					final String msg = "This code has lazily accessed a foreign key relationship "+ percent
							+"% of the time.  This caused "+ v.i +" more queries to the "
							+"database than necessary.  You should consider adding .with("
							+ k.fk.referencing.getSimpleName() +"."+ k.fk.name
							+") to your join.  This happened at:\n\t"
							+ Util.join("\n\t", (Object[]) k.a)
							+"\nwhile iterating over a query created here:\n\t"
							+ Util.join("\n\t", (Object[]) st) +"\n"
							+ WARN_OFF;
					log.warning(msg);
				}
			}
		}
	}

	void accessedFkCallback(final Table table, final FK<? extends Table> fk) {
		final StackTraceElement[] tmp = Thread.currentThread().getStackTrace();
		final StackTraceElement[] st = new StackTraceElement[tmp.length-3];
		System.arraycopy(tmp, 3, st, 0, st.length);
		final StackTraceKey key = new StackTraceKey(fk, st);
		M.Long x = counter.get(key);
		if (x == null) counter.put(key, x = new M.Long());
		x.i++;
	}

	static class StackTraceKey {
		private final StackTraceElement[] a;
		private final FK<? extends Table> fk;
		StackTraceKey(final FK<? extends Table> fk, final StackTraceElement[] a) {
			this.a = a;
			this.fk = fk;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(a);
			result = prime * result + ((fk == null) ? 0 : fk.hashCode());
			return result;
		}
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final StackTraceKey other = (StackTraceKey) obj;
			if (!Arrays.equals(a, other.a))
				return false;
			if (fk == null) {
				if (other.fk != null)
					return false;
			} else if (!fk.equals(other.fk))
				return false;
			return true;
		}
	}

	void __NOSCO_PRIVATE_accessedColumnCallback(final Table table, final Field<?> field) {
		for (int i=0; i<selectedFields.length; ++i) {
			if (selectedFields[i].equals(field)) {
				accessedField.set(i);
				return;
			}
		}
		if (surpriseFields==null) surpriseFields = new ArrayList<Field<?>>();
		surpriseFields.add(field);
	}

	void setSelectedFields(final Field<?>[] selectedFields) {
		this.selectedFields = selectedFields;
	}

	DBQuery<T> getSelectOptimizedQuery() {
		if (!query.optimizeSelectFields()) return query;
		if (!Context.selectOptimizationsEnabled()) {
			//System.err.println("getOptimizedQuery !selectOptimizationsEnabled");
			return query;
		}
		try {
			final Map<Field<?>,Long> used = qc.get(queryHash);
			if (used == null) {
				//System.err.println("getOptimizedQuery no runtime info for "+ this.queryHash);
				return query;
			}
			//System.err.println("used "+ used);
			final Set<Field<?>> deffer = new HashSet<Field<?>>();
			final Field<?>[] originalSelectedFields = query.getSelectFields(false);
			for (final Field<?> f : originalSelectedFields) {
				if (!used.containsKey(f)) deffer.add(f);
			}
			if (deffer.isEmpty()) return query;
			removePKFieldsFromSet(deffer);
			if (deffer.size()==originalSelectedFields.length && originalSelectedFields.length>0) {
				// make sure we don't remove every field!
				deffer.remove(originalSelectedFields[0]);
			}
			//System.err.println("getOptimizedQuery optimized!");
			this.selectOptimized  = true;
			return (DBQuery<T>) query.deferFields(deffer);
		} finally {
			query = null;
		}
	}

	private void removePKFieldsFromSet(final Set<Field<?>> deffer) {
		for (final Table table : query.tables) {
			for (final Field<?> f : Util.getPK(table).GET_FIELDS()) {
				deffer.remove(f);
			}
		}
		for (final Join join : query.joinsToOne) {
			for (final Field<?> f : Util.getPK(join.reffedTableInfo.table).GET_FIELDS()) {
				deffer.remove(f);
			}
			for (final Field<?> f : Util.getPK(join.reffingTableInfo.table).GET_FIELDS()) {
				deffer.remove(f);
			}
		}
		for (final Join join : query.joinsToMany) {
			for (final Field<?> f : Util.getPK(join.reffedTableInfo.table).GET_FIELDS()) {
				deffer.remove(f);
			}
			for (final Field<?> f : Util.getPK(join.reffingTableInfo.table).GET_FIELDS()) {
				deffer.remove(f);
			}
		}
	}


	/* ====================== serialization stuff ====================== */

	static void doNothing() {
		// do nothing; just make sure the class loads
		return;
	}

	//private final static File BASE_DIR = new File(System.getProperty("user.home"));
	private final static File BASE_DIR = new File(System.getProperty("java.io.tmpdir"));
	private final static File CACHE_DIR = new File(BASE_DIR, ".nosco_optimizations_"+System.getProperty("user.name"));
	private final static File PERF_CACHE = new File(CACHE_DIR, "performance");
	private final static String README_TEXT = "Welcome to Nosco!\n\n" +
			"This directory contains runtime profiles for programs that use the nosco library.\n" +
			"It is always safe to delete.  Your programs will just run a little slower the next\n" +
			"time or two they start up.  Thanks for visiting!\n\nhttp://code.google.com/p/nosco/\n";
	private final static long START = System.currentTimeMillis();
	private final static long cutoff = START - 1000*60*60*24*100;

	private final static Thread cleanPerformanceInfo = new Thread() {
		@Override
		public void run() {
			try {
				sleep((long) (Math.random() * 1000*60*60*4));
			} catch (final InterruptedException e) {
				//e.printStackTrace();
			}

			try {
				final File tmp = File.createTempFile("nosco_performance_", "");
				final Writer w = new BufferedWriter(new FileWriter(tmp));
				final BufferedReader br = new BufferedReader(new FileReader(PERF_CACHE));
				for (String line = null; (line=br.readLine())!=null;) {
					try {
						final JSONObject o = new JSONObject(line);
						final long ts = o.getLong(TIME_STAMP);
						if (ts < cutoff) continue;
					} catch (final JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					w.write(line);
					w.write('\n');
				}
				br.close();
				w.close();
				final Writer w2 = new BufferedWriter(new FileWriter(PERF_CACHE));
				final BufferedReader br2 = new BufferedReader(new FileReader(tmp));
				for (String line = null; (line=br2.readLine())!=null;) {
					w2.write(line);
					w2.write('\n');
				}
				br2.close();
				w2.close();
			} catch (final IOException e) {
				//e.printStackTrace();
			}
		}
	};

	private final static Thread loadPerformanceInfo = new Thread() {
		@Override
		public void run() {
			if (!CACHE_DIR.isDirectory()) {
				CACHE_DIR.mkdirs();
				try {
					final Writer w = new FileWriter(new File(CACHE_DIR, "README.TXT"));
					w.write(README_TEXT);
					w.close();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			long oldest = Long.MAX_VALUE;

			try {
				final ClassLoader cl = this.getClass().getClassLoader();
				final BufferedReader br = new BufferedReader(new FileReader(PERF_CACHE));
				for (String line = null; (line=br.readLine())!=null;) {
					try {
						final JSONObject o = new JSONObject(line);
						final String st = o.getString(STACK_TRACE);
						final Long lastSeen = o.getLong(TIME_STAMP);
						if (lastSeen < oldest) oldest = lastSeen;
						final JSONObject o2 = o.getJSONObject(USED_FIELDS);
						final Map<Field<?>, Long> map = Collections.synchronizedMap(new HashMap<Field<?>,Long>());
						for (final String x : o2.keySet()) {
							try {
								final long datetime = o2.getLong(x);
								if (datetime < cutoff) continue;
								final int split = x.lastIndexOf('.');
								final String className = x.substring(0, split);
								final String fieldName = x.substring(split+1);
								final Class<?> clz = cl.loadClass(className);
								final java.lang.reflect.Field f = clz.getDeclaredField(fieldName);
								final Field<?> field = (Field<?>) f.get(null);
								map.put(field, datetime);
							} catch (final ClassNotFoundException e) {
								e.printStackTrace();
							} catch (final SecurityException e) {
								e.printStackTrace();
							} catch (final NoSuchFieldException e) {
								e.printStackTrace();
							} catch (final IllegalArgumentException e) {
								e.printStackTrace();
							} catch (final IllegalAccessException e) {
								e.printStackTrace();
							}
						}
						if (st!=null && lastSeen!=null) stLastSeen.put(st, lastSeen);
						if (true || !map.isEmpty()) {
							qc.putIfAbsent(st, Collections.synchronizedMap(new HashMap<Field<?>,Long>()));
							final Map<Field<?>,Long> used = qc.get(st);
							used.putAll(map);
						}
					} catch (final JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//System.err.println("loaded: "+ PERF_CACHE.getPath());
			} catch (final FileNotFoundException e) {
				// no worries
				//System.err.println("does not exist: "+ PERF_CACHE.getPath());
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (oldest < cutoff - 2*MILLIS_ONE_WEEK) {
				cleanPerformanceInfo.start();
			}
		}
	};


	static {
		cleanPerformanceInfo.setDaemon(true);
		loadPerformanceInfo.start();
	}

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		    	try {
					Writer w = null;
					for (final Entry<String, Map<Field<?>, Long>> e : qc.entrySet()) {
						final String stHash = e.getKey();
						if (!stSeenThisRun.contains(stHash)) continue;
						final JSONObject o = new JSONObject();
						final JSONObject o2 = new JSONObject();
						try {
							int count = 0;
							for (final Entry<Field<?>, Long> e2 : e.getValue().entrySet()) {
								final long datetime = e2.getValue();
								if (datetime < START) continue;
								final Field<?> field = e2.getKey();
								o2.put(field.TABLE.getName() +"."+ field.JAVA_NAME, datetime);
								++count;
							}
							final Long lastSeen = stLastSeen.get(stHash);
							if (count > 0 || lastSeen==null || (lastSeen+MILLIS_ONE_WEEK)<START) {
								o.put(STACK_TRACE, stHash);
								o.put(TIME_STAMP, System.currentTimeMillis());
								o.put(USED_FIELDS, o2);
								if (w==null) w = new BufferedWriter(new FileWriter(PERF_CACHE, true));
								o.write(w).write('\n');
							}
						} catch (final JSONException e1) {
							e1.printStackTrace();
						}
					}
					if (w!=null) w.close();
					//System.err.println("wrote: "+ PERF_CACHE);
				} catch (final IOException e) {
					//e.printStackTrace();
				}
		    }
		});
	}

}
