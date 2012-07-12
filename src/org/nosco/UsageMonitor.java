package org.nosco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.nosco.Field.FK;

class UsageMonitor {

	private static final String WARN_OFF = "To turn these warnings off, "
			+ "call: Context.getThreadContext().enableUsageWarnings(false);";

	Map<StackTraceKey,MLong> counter = new HashMap<StackTraceKey,MLong>();
	
	private static final Logger log = Logger.getLogger("org.nosco.recommendations");
	long count = 0;
	private final StackTraceElement[] st;
	private final BitSet accessedField = new BitSet();
	private Field<?>[] selectedFields;
	
	@Override
	protected void finalize() throws Throwable {
		warnBadFKUsage();
		warnUnusedColumns();
		super.finalize();
	}

	private void warnUnusedColumns() {
		final List<String> unused = new ArrayList<String>();
		for (int i=0; i<selectedFields.length; ++i) {
			if (!accessedField.get(i)) {
				unused.add(selectedFields[i].TABLE.getSimpleName() +"."+ selectedFields[i].JAVA_NAME);
			}
		}
		if (!unused.isEmpty()) {
			final String msg = "The following columns were never accessed:\n\t" 
					+ Util.join(", ", unused) + "\nin the query created here:\n\t" 
					+ Util.join("\n\t", (Object[]) st) + "\n" 
					+ "You might consider not querying these fields by using the " 
					+ "deferFields(Field<?>...) method on your query.\n" 
					+ WARN_OFF;
			log.info(msg);
		}
	}

	UsageMonitor() {
		// grab the current stack trace
		final StackTraceElement[] tmp = Thread.currentThread().getStackTrace();
		st = new StackTraceElement[tmp.length-4];
		System.arraycopy(tmp, 4, st, 0, st.length);
	}

	private void warnBadFKUsage() {
		if (count > 4) {
			for (final Entry<StackTraceKey, MLong> e : counter.entrySet()) {
				final MLong v = e.getValue();
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
		MLong x = counter.get(key);
		if (x == null) counter.put(key, x = new MLong());
		x.i++;
	}

	static class MLong {
		long i = 0;
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
				break;
			}
		}
	}

	public void setSelectedFields(final Field<?>[] selectedFields) {
		this.selectedFields = selectedFields;
	}

}
