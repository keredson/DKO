package org.kered.dko;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kered.dko.Field.PK;

/**
 * Reads and writes DKO objects to CSV files (or any writer, such as System.out)
 *
 * @author Derek Anderson
 */
public class CSV {

	/**
	 * Writes out an iterable of table objects to a writer.
	 * Writes a header row.
	 * @param items
	 * @param w
	 * @return the number of objects written
	 * @throws IOException
	 */
	public static <T extends Table> long write(final Iterable<T> items, final Writer w) throws IOException {
		final long count = 0;
		boolean first = true;
		List<Field<?>> fields = null;
		for (final T t : items) {
			if (first) {
				fields = new ArrayList<Field<?>>(Util.getFIELDS(t.getClass()));
				final PK<T> pk = Util.getPK(t);
				final List<Field<?>> pks = pk==null ? null : pk.GET_FIELDS();
				if (pks != null) {
					final int s1 = pks.size();
					for (int i=0; i<s1; ++i) {
						final int s2 = fields.size();
						for (int j=0; j<s2; ++j) {
							if (pks.get(i) == fields.get(j) && i!=j) {
								final Field<?> tmp = fields.get(i);
								fields.set(i, fields.get(j));
								fields.set(j, tmp);
							}
						}
					}
				}
				w.write(Util.joinFields(Constants.DB_TYPE.SQL92, ",", fields));
				w.write('\n');
				first = false;
			}
			for (int i=0; i<fields.size(); ++i) {
				final Field<?> f = fields.get(i);
				if (t.__NOSCO_FETCHED_VALUES.get(f.INDEX)) {
					final Object o = t.get(f);
					if (o != null) {
						String s = o.toString();
						if (s.contains(",") || s.contains("\"") || s.length()==0) {
							s = "\"" + s.replace("\"", "\"\"") + "\"";
						}
						w.write(s);
					}
					if (i < fields.size()-1) w.write(',');
				}
			}
			w.write('\n');
		}
		return count;
	}

	/**
	 * Writes out an iterable of table objects to a file.
	 * Writes a header row.
	 * @param items
	 * @param f file to be written
	 * @return the number of objects written
	 * @throws IOException
	 */
	public static <T extends Table> long write(final Iterable<T> items, final File f) throws IOException {
		final Writer w = new BufferedWriter(new FileWriter(f));
		final long count = write(items, w);
		w.close();
		return count;
	}

	/**
	 * Creates an iterable of objects of type cls from a CSV file.
	 * Note that this iterable will always try to read from the reader.  (no caching)
	 * @param cls
	 * @param f
	 * @return
	 */
	public static <T extends Table> Iterable<T> read(final Class<T> cls, final Reader r) {
		try {
			final Constructor<T> constructor = (Constructor<T>) cls.getDeclaredConstructor(
					new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
			constructor.setAccessible(true);
			return new Iterable<T>() {
				@Override
				public Iterator<T> iterator() {
					return createIterator(cls, constructor, r, false);
				}
			};
		} catch (final SecurityException e) {
			throw new RuntimeException(e);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates an iterable of objects of type cls from a CSV file.
	 * @param cls
	 * @param f
	 * @return
	 */
	public static <T extends Table> Iterable<T> read(final Class<T> cls, final File f) {
		try {
			final Constructor<T> constructor = (Constructor<T>) cls.getDeclaredConstructor(
					new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
			constructor.setAccessible(true);
			return new Iterable<T>() {
				@Override
				public Iterator<T> iterator() {
					try {
						return createIterator(cls, constructor, new FileReader(f), true);
					} catch (final FileNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
			};
		} catch (final SecurityException e) {
			throw new RuntimeException(e);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static <T extends Table> Iterator<T> createIterator(
			final Class<T> cls, final Constructor<T> constructor,
			final Reader _r, final boolean closeOnFinish) {
		final BufferedReader r = new BufferedReader(_r);
		return new Iterator<T>() {

			private T next = null;
			private boolean first = true;
			private Field<?>[] fields = null;
			private Constructor<?>[] fieldConstructors = null;

			@Override
			public boolean hasNext() {
				if (next != null) return true;
				try {
					if (first) {
						first  = false;
						final String line = r.readLine();
						if (line != null) {
							final String[] headers = line.split(",");
							fields = new Field<?>[headers.length];
							fieldConstructors  = new Constructor<?>[fields.length];
							final List<Field<?>> clsFields = Util.getFIELDS(cls);
							for (int i=0; i<fields.length; ++i) {
								for (int j=0; j<clsFields.size(); ++j) {
									if (clsFields.get(j).NAME.equals(headers[i])) {
										fields[i] = clsFields.get(j);
										break;
									}
								}
								fieldConstructors[i] = fields[i].TYPE
										.getDeclaredConstructor(String.class);
								if (!fieldConstructors[i].isAccessible()) {
									fieldConstructors[i].setAccessible(true);
								}
							}

						}
					}
					final Object[] oa = new Object[fields.length];
					int pos = 0;
					final String line = r.readLine();
					if (line != null) {
			            StringBuilder sb = new StringBuilder();
			            boolean quoted = false;
			            boolean valueWasQuoted = false;
						for (int i = 0; i < line.length(); ++i) {
			                final char c = line.charAt(i);
			                sb.append(c);
			                if (c == '"') {
			                    quoted = !quoted;
			                    valueWasQuoted |= quoted;
			                }
			                if ((!quoted && c == ',') || i == line.length()-1) {
			                    final String s = sb.toString()
			                    		.replaceAll(",$", "")
			                    		.replaceAll("^\"|\"$", "")
			                    		.replace("\"\"", "\"")
			                    		.trim();
			                    if (pos<oa.length) {
				                    Object o = null;
				                    if (s.length()>0 || valueWasQuoted) {
				                    	o = fieldConstructors[pos].newInstance(s);
				                    }
			                    	oa[pos++] = o;
			                    	valueWasQuoted = false;
			                    }
			                    sb = new StringBuilder();
			                }
			            }
						next = constructor.newInstance(fields, oa, 0, fields.length);
					}

				} catch (final IOException e) {
					try {
						if (closeOnFinish) r.close();
					} catch (final IOException e1) {
						e1.printStackTrace();
					}
					throw new RuntimeException(e);
				} catch (final InstantiationException e) {
					throw new RuntimeException(e);
				} catch (final IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (final IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (final InvocationTargetException e) {
					throw new RuntimeException(e);
				} catch (final SecurityException e) {
					throw new RuntimeException(e);
				} catch (final NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
				if (next != null) return true;
				try {
					if (closeOnFinish) r.close();
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
				return false;
			}

			@Override
			public T next() {
				if (next == null) hasNext();
				final T t = next;
				next = null;
				return t;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

}
