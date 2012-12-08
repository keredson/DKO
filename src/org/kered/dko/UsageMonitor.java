package org.kered.dko;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.DBQuery.JoinInfo;
import org.kered.dko.Field.FK;
import org.kered.dko.Tuple.Tuple3;
import org.kered.dko.json.JSONException;
import org.kered.dko.json.JSONObject;
import org.kered.dko.persistence.QuerySize;

class UsageMonitor<T extends Table> {

	private static final String TIME_STAMP = "ts";
	private static final String STACK_TRACE = "st";
	protected static final String USED_FIELDS = "uf";
	private static final int MIN_WARN_COUNT = 8;

	private static final int MILLIS_ONE_WEEK = 1000*60*60*24*7;

	private static final String WARN_OFF = "To turn these warnings off, "
			+ "call: Context.getThreadContext().enableUsageWarnings(false);";

	Map<StackTraceKey,M.Long> counter = new HashMap<StackTraceKey,M.Long>();

	private static final Logger log = Logger.getLogger("org.kered.dko.recommendations");
	long objectCount = 0;
	private final StackTraceElement[] st;
	private final BitSet accessedField = new BitSet();
	private Field<?>[] selectedFields;
	private Set<Field<?>> surpriseFields = null;
	private final String queryHash;

	private DBQuery<T> query;
	private boolean selectOptimized = false;
	private Set<Field<?>> pks = new HashSet<Field<?>>();
	public long rowCount = 0;
	private final int queryHashCode;
	private final Class<T> queryType;
	private static long warnBadFKUsageCount = 0;

	@Override
	protected void finalize() throws Throwable {
		try {
			warnBadFKUsage();
			questionUnusedColumns();
		} catch (final Throwable t) {
			t.printStackTrace();
			log.severe(t.toString());
		}
		super.finalize();
	}

	final static Map<String,Map<Field<?>,Long>> qc = Collections.synchronizedMap(new HashMap<String,Map<Field<?>,Long>>());
	static Map<String,Long> stLastSeen = Collections.synchronizedMap(new HashMap<String,Long>());
	static Set<String> stSeenThisRun = Collections.synchronizedSet(new HashSet<String>());

	private void questionUnusedColumns() {
		Map<Field<?>,Long> used = qc.get(queryHash);
		if (used==null) {
			synchronized(qc) {
				used = qc.get(queryHash);
				if (used == null) {
					used = Collections.synchronizedMap(new HashMap<Field<?>,Long>());
					qc.put(queryHash, used);
					//System.err.println("questionUnusedColumns new used for "+ queryHash);
				}
			}
			//qc.putIfAbsent(queryHash, Collections.synchronizedMap(new HashMap<Field<?>,Long>()));
			//used = qc.get(queryHash);
		}
		final Set<Field<?>> unusedColumns = new LinkedHashSet<Field<?>>();
		final long now = System.currentTimeMillis();
		for (int i=0; i<selectedFields.length; ++i) {
			if (!accessedField.get(i)) {
				unusedColumns.add(selectedFields[i]);
			} else {
				final Long date = used.get(selectedFields[i]);
				if (date==null || (date+MILLIS_ONE_WEEK)<START) {
					used.put(selectedFields[i], now);
				}
			}
		}
		if (surpriseFields!=null) {
			for (final Field<?> f : surpriseFields) {
				//System.err.println("fixing surpriseField "+ f +" @ "+ this.queryHash);
				used.put(f, now);
			}
		}
		unusedColumns.removeAll(pks);
		final List<String> unusedColumnDescs = new ArrayList<String>();
		for (final Field<?> field : unusedColumns) {
			unusedColumnDescs.add(field.TABLE.getSimpleName() +"."+ field.JAVA_NAME);
		}
		if (!selectOptimized && !unusedColumnDescs.isEmpty() && objectCount > MIN_WARN_COUNT) {
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
				e1.printStackTrace();
			}
		}
		this.query = query;
		this.queryHashCode = query.hashCode();
		this.queryType = query.getType();
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
//			e.printStackTrace();
//		} catch (final NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
        queryHash = hash;
        stSeenThisRun.add(hash);
        //System.err.println("queryHash "+ queryHash +" "+ query.hashCode());
        //System.err.println("queryHash "+ queryHash);

        // get pks for all tables

		for (final TableInfo table : query.tableInfos) {
			for (final Field<?> f : Util.getPK(table.tableClass).GET_FIELDS()) {
				pks.add(f);
			}
		}
		for (final JoinInfo join : query.joinsToOne) {
			for (final Field<?> f : Util.getPK(join.reffedTableInfo.tableClass).GET_FIELDS()) {
				pks.add(f);
			}
			for (final Field<?> f : Util.getPK(join.reffingTableInfo.tableClass).GET_FIELDS()) {
				pks.add(f);
			}
		}
		for (final JoinInfo join : query.joinsToMany) {
			for (final Field<?> f : Util.getPK(join.reffedTableInfo.tableClass).GET_FIELDS()) {
				pks.add(f);
			}
			for (final Field<?> f : Util.getPK(join.reffingTableInfo.tableClass).GET_FIELDS()) {
				pks.add(f);
			}
		}
		pks = Collections.unmodifiableSet(pks);
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
		if (objectCount > MIN_WARN_COUNT) {
			for (final Entry<StackTraceKey, M.Long> e : counter.entrySet()) {
				final M.Long v = e.getValue();
				final long percent = v.i*100/objectCount;
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
					warnBadFKUsageCount  += 1;
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
		if (surpriseFields==null) surpriseFields = Collections.synchronizedSet(new HashSet<Field<?>>());
		if (!surpriseFields.contains(field)) {
			surpriseFields.add(field);
			final StackTraceElement[] tmp = Thread.currentThread().getStackTrace();
			final StackTraceElement[] st = new StackTraceElement[tmp.length-3];
			System.arraycopy(tmp, 3, st, 0, st.length);
			final String msg = "Optimizer was surprised by field "+ field.TABLE.getSimpleName()
					+"."+ field.JAVA_NAME +" here:\n\t"
					+ Util.join("\n\t", st) +"\nIf this happens once it's normal "
					+"(the optimizer will learn to include it the next time this is run).\n"
					+"But if this is happening every time you run "
					+"please report this as a bug to http://code.google.com/p/nosco/";

			log.info(msg);
		}
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
			//System.err.println("used "+ used +" @ "+ this.queryHash);
			final Set<Field<?>> deffer = new HashSet<Field<?>>();
			final List<Field<?>> originalSelectedFields = query.getSelectFields(false);
			for (final Field<?> f : originalSelectedFields) {
				if (!used.containsKey(f)) deffer.add(f);
			}
			if (deffer.isEmpty()) return query;
			deffer.removeAll(pks);
			if (deffer.size()==originalSelectedFields.size() && originalSelectedFields.size()>0) {
				// make sure we don't remove every field!
				deffer.remove(originalSelectedFields.get(0));
			}
			//System.err.println("getOptimizedQuery optimized!");
			this.selectOptimized  = true;
			return (DBQuery<T>) query.deferFields(deffer);
		} finally {
			query = null;
		}
	}


	/* ====================== serialization stuff ====================== */

	static void doNothing() {
		// do nothing; just make sure the class loads
		return;
	}

	private static File PERF_CACHE = null;
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
				final File tmp = File.createTempFile("dko_performance_", "");
				final Writer w = new BufferedWriter(new FileWriter(tmp));
				final BufferedReader br = new BufferedReader(new FileReader(PERF_CACHE));
				for (String line = null; (line=br.readLine())!=null;) {
					try {
						final JSONObject o = new JSONObject(line);
						final long ts = o.getLong(TIME_STAMP);
						if (ts < cutoff) continue;
					} catch (final JSONException e) {
						log.warning("JSON serialization error:"+ e.getMessage());
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
				log.warning("Unable to clean the performance cache: "+ e.getMessage());
			}
		}
	};

	private static Map<String,Collection<Tuple3<String,String,Long>>> classToFieldToTimeToLoad = Collections.synchronizedMap(new HashMap<String,Collection<Tuple3<String,String,Long>>>());

	private static void loadPerfData(final File CACHE_DIR) {
		PERF_CACHE = new File(CACHE_DIR, "performance");

		if (!CACHE_DIR.isDirectory()) {
			CACHE_DIR.mkdirs();
			try {
				final Writer w = new FileWriter(new File(CACHE_DIR, "README.TXT"));
				w.write(README_TEXT);
				w.close();
			} catch (final IOException e) {
				log.warning("Unable to write to the cache dir '"+ CACHE_DIR.getPath()
						+"': "+ e.getMessage());
			}
		}

		long oldest = Long.MAX_VALUE;

		try {
			final BufferedReader br = new BufferedReader(new FileReader(PERF_CACHE));
			for (String line = null; (line=br.readLine())!=null;) {
				try {
					final JSONObject o = new JSONObject(line);
					final String st = o.getString(STACK_TRACE);
					final Long lastSeen = o.getLong(TIME_STAMP);
					if (lastSeen < oldest) oldest = lastSeen;
					final JSONObject o2 = o.getJSONObject(USED_FIELDS);
					for (final String x : o2.keySet()) {
						final long datetime = o2.getLong(x);
						if (datetime < cutoff) continue;
						final int split = x.lastIndexOf('.');
						final String className = x.substring(0, split);
						final String fieldName = x.substring(split+1);
						Collection<Tuple3<String, String, Long>> fieldToTimetoLoad = classToFieldToTimeToLoad.get(className);
						if (fieldToTimetoLoad == null) {
							fieldToTimetoLoad = new ArrayList<Tuple3<String, String, Long>>();
							 classToFieldToTimeToLoad.put(className, fieldToTimetoLoad);
						}
						fieldToTimetoLoad.add(new Tuple3<String, String, Long>(st, fieldName, datetime));
					}
					if (st!=null && lastSeen!=null) stLastSeen.put(st, lastSeen);
				} catch (final JSONException e) {
					e.printStackTrace();
				}
			}
			//System.err.println("loaded: "+ PERF_CACHE.getPath());
		} catch (final FileNotFoundException e) {
			// no worries
			log.fine("Performace cache file '"+ PERF_CACHE.getPath() +"' not found.  " +
					"(this is not an error assuming you've never run this program here before)");
			//System.err.println("does not exist: "+ PERF_CACHE.getPath());
		} catch (final IOException e) {
			log.warning("Unable to read the performance cache: "+ e.getMessage());
		}

		if (oldest < cutoff - 2*MILLIS_ONE_WEEK) {
			cleanPerformanceInfo.start();
		}
	}

	private final static Thread loadPerformanceInfo = new Thread() {
		@Override
		public void run() {
			final File CACHE_DIR;;
			String dir = System.getProperty(Constants.PROPERTY_CACHE_DIR);
			if (dir == null) dir = System.getProperty(Constants.PROPERTY_CACHE_DIR_OLD);
			if (dir == null) {
				final File BASE_DIR = new File(System.getProperty("user.home"));
				CACHE_DIR = new File(BASE_DIR, ".dko_optimizations");
			} else {
				CACHE_DIR = new File(dir);
			}
			loadPerfData(CACHE_DIR);
		}
	};

	static {
		cleanPerformanceInfo.setDaemon(true);
		loadPerformanceInfo.start();
	}

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		    	try {
					Writer w = null;
					final Map<String,Map<Field<?>,Long>> qccp = new HashMap<String,Map<Field<?>,Long>>(qc);
					for (final Entry<String, Map<Field<?>, Long>> e : qccp.entrySet()) {
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
							log.warning("JSON serialization error:"+ e1.getMessage());
						}
					}
					if (w!=null) w.close();
					//System.err.println("wrote: "+ PERF_CACHE);
				} catch (final IOException e) {
					log.warning("Could not write the performace cache file '"+ PERF_CACHE.getPath()
							+"':"+ e.getMessage());
				}
		    }
		});
	}

	static void loadStatsFor(final Class<? extends Table> tableClass) {
		if (loadPerformanceInfo.isAlive())
			try {
				loadPerformanceInfo.join();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		final String className = tableClass.getName();
		final Collection<Tuple3<String, String, Long>> fieldToTimeToLoad = classToFieldToTimeToLoad.remove(className);
		if (fieldToTimeToLoad != null) {
			final ClassLoader cl = tableClass.getClassLoader();
			try {
				final Class<?> clz = cl.loadClass(className);
				for (final Tuple3<String, String, Long> tuple : fieldToTimeToLoad) {
					final String st = tuple.a;
					final String fieldName = tuple.b;
					final Long time = tuple.c;
					try {
						final java.lang.reflect.Field f = clz.getDeclaredField(fieldName);
						final Field<?> field = (Field<?>) f.get(null);
						Map<Field<?>, Long> fieldSeenMap = qc.get(st);
						if (fieldSeenMap==null) {
							synchronized(qc) {
								fieldSeenMap = qc.get(st);
								if (fieldSeenMap == null) {
									fieldSeenMap = Collections.synchronizedMap(new HashMap<Field<?>,Long>());
									qc.put(st, fieldSeenMap);
									//System.err.println("loadStatsFor new fieldSeenMap for "+ st);
								}
							}
						}
						fieldSeenMap.put(field, time);
					} catch (final SecurityException e1) {
						e1.printStackTrace();
					} catch (final NoSuchFieldException e1) {
						e1.printStackTrace();
					} catch (final IllegalArgumentException e) {
						e.printStackTrace();
					} catch (final IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			} catch (final ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		}
	}

	public void iteratorIsDone() {
		saveSizeOfQuery();
	}

	private final static BlockingQueue<UsageMonitor> querySizes = new LinkedBlockingQueue<UsageMonitor>();

	private void saveSizeOfQuery() {
		if (this.queryType.getPackage().getName().startsWith("org.kered.dko"))
			return;
		querySizes.add(this);
	}

	static Thread saveQuerySizes = new Thread() {
		DataSource ds = org.kered.dko.persistence.Util.getDS();

		@Override
		public void run() {
			if (ds==null) {
				log.warning("could not load usage monitor datasource - not collection perf info");
				return;
			}
			while (true) {
				try {
					final UsageMonitor um = querySizes.take();
					// final int id = Math.abs(um.queryHashCode);
					final int id = um.queryHashCode;
					final QuerySize qs = QuerySize.ALL.use(ds).get(
							QuerySize.ID.eq(id));
					// if (qs!=null && qs.getHashCode()!=hash) {
					// qs = QuerySize.ALL.get(QuerySize.HASH_CODE.eq(hash));
					// }
					if (qs == null) {
						new QuerySize()
								.setId(id)
								.setHashCode(um.queryHashCode)
								.setSchemaName(
										Util.getSCHEMA_NAME(um.queryType))
								.setTableName(Util.getTABLE_NAME(um.queryType))
								.setRowCount(um.rowCount).insert(ds);
					} else {
						qs.setRowCount(ma(um.rowCount, qs.getRowCount()));
						qs.update(ds);
					}
				} catch (final InterruptedException e) {
					e.printStackTrace();
				} catch (final SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private long ma(Long a, Long b) {
			final int MA = 5;
			if (a == null)
				a = 0l;
			if (b == null)
				b = 0l;
			return (a + (MA - 1) * b) / MA;
		}
	};
	static {
		saveQuerySizes.setDaemon(true);
		saveQuerySizes.start();
	}

}
