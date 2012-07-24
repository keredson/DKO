package org.nosco;

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
import java.util.Iterator;

import org.nosco.Field.PK;

/**
 * Reads and writes nosco objects to CSV files (or any writer, such as System.out)
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
	public static <T extends Table> long write(Iterable<T> items, Writer w) throws IOException {
		long count = 0;
		boolean first = true;
		Field<?>[] fields = null;
		for (T t : items) {
			if (first) {
				fields = t.FIELDS();
				PK<T> pk = Util.getPK(t);
				Field[] pks = pk==null ? null : pk.GET_FIELDS();
				if (pks != null) {
					for (int i=0; i<pks.length; ++i) {
						for (int j=0; j<fields.length; ++j) {
							if (pks[i] == fields[j] && i!=j) {
								Field<?> tmp = fields[i];
								fields[i] = fields[j];
								fields[j] = tmp;
							}
						}
					}
				}
				w.write(Util.join(",", fields));
				w.write('\n');
				first = false;
			}
			for (int i=0; i<fields.length; ++i) {
				Field<?> f = fields[i];
				if (t.__NOSCO_FETCHED_VALUES.get(f.INDEX)) {
					Object o = t.get(f);
					if (o != null) {
						String s = o.toString();
						if (s.contains(",") || s.contains("\"") || s.length()==0) {
							s = "\"" + s.replace("\"", "\"\"") + "\"";
						}
						w.write(s);
					}
					if (i < fields.length-1) w.write(',');
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
	public static <T extends Table> long write(Iterable<T> items, File f) throws IOException {
		Writer w = new BufferedWriter(new FileWriter(f));
		long count = write(items, w);
		w.close();
		return count;
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
						final BufferedReader r = new BufferedReader(new FileReader(f));
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
										String line = r.readLine();
										if (line != null) {
											String[] headers = line.split(",");
											fields = new Field<?>[headers.length];
											fieldConstructors  = new Constructor<?>[fields.length];
											Field[] clsFields = cls.newInstance().FIELDS();
											for (int i=0; i<fields.length; ++i) {
												for (int j=0; j<clsFields.length; ++j) {
													if (clsFields[j].NAME.equals(headers[i])) {
														fields[i] = clsFields[j];
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
									Object[] oa = new Object[fields.length];
									int pos = 0;
									String line = r.readLine();
									if (line != null) {
							            StringBuilder sb = new StringBuilder();
							            boolean quoted = false;
										for (int i = 0; i < line.length(); ++i) {
							                char c = line.charAt(i);
							                sb.append(c);
							                if (c == '"') {
							                    quoted = !quoted;
							                }
							                if ((!quoted && c == ',') || i == line.length()-1) {
							                    String s = sb.toString()
							                    		.replaceAll(",$", "")
							                    		.replaceAll("^\"|\"$", "")
							                    		.replace("\"\"", "\"")
							                    		.trim();
							                    if (pos<oa.length) {
								                    Object o = null;
								                    if (s.length()>0) {
								                    	o = fieldConstructors[pos].newInstance(s);
								                    }
							                    	oa[pos++] = o;
							                    }
							                    sb = new StringBuilder();
							                }
							            }
										next = constructor.newInstance(fields, oa, 0, fields.length);
									}

								} catch (IOException e) {
									try {
										r.close();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									throw new RuntimeException(e);
								} catch (InstantiationException e) {
									throw new RuntimeException(e);
								} catch (IllegalAccessException e) {
									throw new RuntimeException(e);
								} catch (IllegalArgumentException e) {
									throw new RuntimeException(e);
								} catch (InvocationTargetException e) {
									throw new RuntimeException(e);
								} catch (SecurityException e) {
									throw new RuntimeException(e);
								} catch (NoSuchMethodException e) {
									throw new RuntimeException(e);
								}
								if (next != null) return true;
								try {
									r.close();
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
								return false;
							}

							@Override
							public T next() {
								if (next == null) hasNext();
								T t = next;
								next = null;
								return t;
							}

							@Override
							public void remove() {
								throw new UnsupportedOperationException();
							}

						};
					} catch (FileNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
			};
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

}
