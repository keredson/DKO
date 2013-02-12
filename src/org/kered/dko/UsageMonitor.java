package org.kered.dko;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.kered.dko.persistence.ColumnAccess;
import org.kered.dko.persistence.QueryExecution;
import org.kered.dko.persistence.QuerySize;

class UsageMonitor<T extends Table> {

	private static final long ONE_DAY = 1000*60*60*24;
	private static final long FORTY_FIVE_DAYS = ONE_DAY * 45;
	private static final int MIN_WARN_COUNT = 8;

	private static final String WARN_OFF = "To turn these warnings off, "
			+ "call: Context.getThreadContext().enableUsageWarnings(false);";

	Map<StackTraceKey,M.Long> counter = new HashMap<StackTraceKey,M.Long>();

	private static final Logger log = Logger.getLogger("org.kered.dko.recommendations");
	long objectCount = 0;
	private final StackTraceElement[] st;
	private Set<Field<?>> surpriseFields = null;

	private DBQuery<T> query;
	private boolean selectOptimized = false;
	private Set<Field<?>> pks = new HashSet<Field<?>>();
	public long rowCount = 0;
	private final int queryHash;
	private final Class<T> queryType;
	private int stackHash;
	private QueryExecution qe;
	private Set<Field<?>> selectedFieldSet;
	private Set<Field<?>> seenFields = new HashSet<Field<?>>();
	private DataSource ds;
	private boolean newQE;
	private boolean shutdown = false;
	private static long warnBadFKUsageCount = 0;

	private synchronized void shutdown() {
		if (shutdown) return;
		try {
			shutdown = true;
			updateColumnAccesses();
			warnBadFKUsage();
			questionUnusedColumns();
		} catch (final Throwable t) {
			t.printStackTrace();
			log.severe(t.toString());
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	private void updateColumnAccesses() {
		Map<String, Map<String, ColumnAccess>> used;
		if (newQE) {
			used = new HashMap<String, Map<String, ColumnAccess>>();
		} else {
			try {
				used = ColumnAccess.ALL.where(ColumnAccess.QUERY_EXECUTION_ID.eq(qe.getId())).mapBy(ColumnAccess.TABLE_NAME, ColumnAccess.COLUMN_NAME);
			} catch (SQLException e) {
				e.printStackTrace();
				used = new HashMap<String, Map<String, ColumnAccess>>();
			}			
		}
		long threshold = System.currentTimeMillis() - ONE_DAY;
		for (Field<?> f : seenFields) {
			String tableName = Util.getTABLE_NAME(f.TABLE);
			Map<String, ColumnAccess> columns = used.get(tableName);
			ColumnAccess ca = columns==null ? null : columns.get(f.NAME);
			if (ca == null) {
				ca = new ColumnAccess()
					.setColumnName(f.NAME)
					.setTableName(tableName)
					.setQueryExecutionIdFK(qe)
					.setLastSeen(System.currentTimeMillis());
				try {
					ca.insert(ds);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (ca.getLastSeen() < threshold) {
				ca.setLastSeen(System.currentTimeMillis());
				try {
					ca.update(ds);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void questionUnusedColumns() {
		final Set<Field<?>> unusedColumns = new LinkedHashSet<Field<?>>(this.selectedFieldSet);
		unusedColumns.removeAll(seenFields);
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
	
	static <T extends Table> UsageMonitor build(final DBQuery<T> query) {
		Class<T> type = query.getType();
		if (QueryExecution.class.equals(type)) return null;
		if (QuerySize.class.equals(type)) return null;
		if (ColumnAccess.class.equals(type)) return null;
		return new UsageMonitor<T>(query);
	}

	private UsageMonitor(final DBQuery<T> query) {
		
		ds = org.kered.dko.persistence.Util.getDS();

		// grab the current stack trace
		final StackTraceElement[] tmp = Thread.currentThread().getStackTrace();
		int i=1;
		while (i<tmp.length && tmp[i].getClassName().startsWith("org.kered.dko")) ++i;
		st = new StackTraceElement[tmp.length-i];
		System.arraycopy(tmp, i, st, 0, st.length);
		stackHash = Util.join(",", st).hashCode();
		queryHash = query.hashCode();

		qe = QueryExecution.ALL.use(ds)
				.where(QueryExecution.STACK_HASH.eq(stackHash))
				.with(ColumnAccess.FK_QUERY_EXECUTION)
				.orderBy(Constants.DIRECTION.DESCENDING, QueryExecution.LAST_SEEN)
				.first();
		this.newQE = qe==null;
		if (newQE) {
			qe = new QueryExecution()
			.setStackHash(stackHash)
			.setQueryHash(queryHash)
			.setDescription(query + " @ "+ st[0])
			.setLastSeen(System.currentTimeMillis());
			try {
				qe.insert(ds);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// update last_seen if older than a day 
		if (qe!=null && (qe.getLastSeen()==null || qe.getLastSeen() < System.currentTimeMillis() - ONE_DAY)) {
			qe.setLastSeen(System.currentTimeMillis());
			try {
				qe.update(ds);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.query = query;
		this.queryType = query.getType();
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
		if (!seenFields.add(field)) return;
		if (selectedFieldSet.contains(field)) return;
		if (surpriseFields==null) surpriseFields = Collections.synchronizedSet(new HashSet<Field<?>>());
		if (surpriseFields.add(field)) {
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
		if (selectedFields==null) throw new IllegalArgumentException("selectedFields cannot be null");
		this.selectedFieldSet = new HashSet<Field<?>>();
		for (Field<?> f : selectedFields) selectedFieldSet.add(f);
	}

	DBQuery<T> getSelectOptimizedQuery() {
		try {
			if (!query.optimizeSelectFields()) return query;
			if (!Context.selectOptimizationsEnabled()) {
				//System.err.println("getOptimizedQuery !selectOptimizationsEnabled");
				return query;
			}
			if (newQE) return query;
			
			Map<String, Map<String, ColumnAccess>> used = qe.getColumnAccessSet().mapBy(ColumnAccess.TABLE_NAME, ColumnAccess.COLUMN_NAME);
			//final Map<Field<?>,Long> used = qc.get(stackTraceHashString);
			//System.err.println("used "+ used +" @ "+ this.queryHash);
			final Set<Field<?>> deffer = new HashSet<Field<?>>();
			final List<Field<?>> originalSelectedFields = query.getSelectFields(false);
			long threshold = qe.getLastSeen() - FORTY_FIVE_DAYS;
			for (final Field<?> f : originalSelectedFields) {
				Map<String, ColumnAccess> columns = used.get(Util.getTABLE_NAME(f.TABLE));
				if (columns==null) continue;
				ColumnAccess ca = columns.get(f.NAME);
				if (ca==null || ca.getLastSeen() < threshold) {
					deffer.add(f);
				}
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
		} catch (SQLException e) {
			e.printStackTrace();
			return query;
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
	private final static String README_TEXT = "Welcome to DKO!\n\n" +
			"This directory contains runtime profiles for programs that use the nosco library.\n" +
			"It is always safe to delete.  Your programs will just run a little slower the next\n" +
			"time or two they start up.  Thanks for visiting!\n\nhttp://code.google.com/p/nosco/\n";
	private final static long START = System.currentTimeMillis();
	private final static long cutoff = START - ONE_DAY * 100;

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
		}
	};

	static {
		loadPerformanceInfo.start();
	}

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		    }
		});
	}


	private final static BlockingQueue<UsageMonitor> querySizes = new LinkedBlockingQueue<UsageMonitor>();

	void saveSizeOfQuery() {
		if (this.queryType.getPackage().getName().startsWith("org.kered.dko"))
			return;
		querySizes.add(this);
	}

	static Thread saveQuerySizes = new Thread() {
		@Override
		public void run() {
			while (true) {
				try {
					final UsageMonitor um = querySizes.take();
					DataSource ds = org.kered.dko.persistence.Util.getDS();
					if (ds==null) {
						log.warning("I could not load the usage monitor's datasource, so I'm stopping collecting performance metrics.");
						return;
					}
					// final int id = Math.abs(um.queryHashCode);
					final int id = um.queryHash;
					final QuerySize qs = QuerySize.ALL.use(ds).get(
							QuerySize.ID.eq(id));
					// if (qs!=null && qs.getHashCode()!=hash) {
					// qs = QuerySize.ALL.get(QuerySize.HASH_CODE.eq(hash));
					// }
					if (qs == null) {
						new QuerySize()
								.setId(id)
								.setHashCode(um.queryHash)
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
