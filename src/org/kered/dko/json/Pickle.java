package org.kered.dko.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.Set;

public class Pickle {

	public Pickle() {}

	private static final Logger log = Logger.getLogger(Pickle.class.getName());

	boolean prettyPrint = false;
	public boolean isPrettyPrint() {
		return prettyPrint;
	}
	ClassLoader cl = this.getClass().getClassLoader();

	public Pickle setPrettyPrint(final boolean prettyPrint) {
		this.prettyPrint = prettyPrint;
		return this;
	}

	int prettyPrintLevel = 3;

	public int getPrettyPrintLevel() {
		return prettyPrintLevel;
	}

	public Pickle setPrettyPrintLevel(final int prettyPrintLevel) {
		this.prettyPrintLevel = prettyPrintLevel;
		return this;
	}

	public String serialize(final Object o) {
		final Object x = serialize(o, new HashSet<Integer>());
		if (x instanceof JSONObject) {
			try {
				final JSONObject jsonObject = (JSONObject)x;
				if (prettyPrint) {
					return jsonObject.toString(prettyPrintLevel);
				} else {
					return jsonObject.toString();
				}
			} catch (final JSONException e) {
				throw new RuntimeException(e);
			}
		}
		return x.toString();
	}

	private static Object serialize(final Object o, final Set<Integer> seen) {
		if (o instanceof String) return o;
		final JSONObject ret = new JSONObject();
		final int id = System.identityHashCode(o);
		try {
			ret.put("-id", id);
			if (seen.contains(id)) return ret;
			seen.add(id);
			final Class<?> cls = o.getClass();
			ret.put("-type", cls.getName());

			if (Collection.class.isAssignableFrom(cls)) {
				final Collection c = (Collection) o;
				final JSONArray ja = new JSONArray();
				for (final Object v : c) ja.put(serialize(v, seen));
				ret.put("-data", ja);
			}

			if (Map.class.isAssignableFrom(cls)) {
				final Map<Object,Object> m = (Map) o;
				final Set<Entry<Object, Object>> entrySet = m.entrySet();
				final JSONArray ja = new JSONArray();
				for (final Entry<Object, Object> e : entrySet){
					final JSONArray je = new JSONArray();
					je.put(serialize(e.getKey(), seen));
					je.put(serialize(e.getValue(), seen));
					ja.put(je);
				}
				ret.put("-data", ja);
			}

			final List<Class> classes = new ArrayList<Class>();
			Class tmpClass = cls;
			while (tmpClass != null) {
				classes.add(tmpClass);
				tmpClass = tmpClass.getSuperclass();
			}
			Collections.reverse(classes);

			for (int k=0; k<classes.size(); ++k) {
				final Class cls2 = classes.get(k);
				for (final Field f : cls2.getDeclaredFields()) {
					final int modifiers = f.getModifiers();
					if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
					f.setAccessible(true);
					final Class<?> fType = f.getType();
					String fName = f.getName();
					for (int j=classes.size()-k-1; j>0; --j) fName = "~"+fName;
					final Object fValue = f.get(o);
					if (fValue == null) {
						ret.put(fName, JSONObject.NULL);
					} else
					if (String.class.equals(fType)) {
						ret.put(fName, fValue);
					} else
					if (Class.class.equals(fType)) {
						final Class fClass = (Class) fValue;
						final JSONObject jo = (JSONObject) serialize(fValue, seen);
						jo.put("name", fClass.getName());
						ret.put(fName, jo);
					} else
					if (fType.isPrimitive()) {
						if (fType == Byte.TYPE) ret.put(fName, f.getByte(o));
						if (fType == Short.TYPE) ret.put(fName, f.getShort(o));
						if (fType == Integer.TYPE) ret.put(fName, f.getInt(o));
						if (fType == Long.TYPE) ret.put(fName, f.getLong(o));
						if (fType == Float.TYPE) ret.put(fName, f.getFloat(o));
						if (fType == Double.TYPE) ret.put(fName, f.getDouble(o));
						if (fType == Boolean.TYPE) ret.put(fName, f.getBoolean(o));
						if (fType == Character.TYPE) ret.put(fName, f.getChar(o));
					} else
					if (fType.isArray()) {
						final Class<?> cType = fType.getComponentType();
						final Object a = fValue;
						final int length = Array.getLength(a);
						final JSONArray ja = new JSONArray();
						for (int i=0; i<length; ++i) {
							if (cType.isPrimitive()) ja.put(Array.get(a, i));
							else ja.put(serialize(Array.get(a, i), seen));
						}
						ret.put(fName, ja);
					} else {
						ret.put(fName, serialize(fValue, seen));
					}
				}
			}
			return ret;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T deserialize(final File f) throws IOException {
		final StringBuffer sb = new StringBuffer();
		final BufferedReader r = new BufferedReader(new FileReader(f));
		String s;
		while ((s = r.readLine()) != null) {
			sb.append(s);
			sb.append('\n');
		}
		r.close();
		return deserialize(sb.toString());
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(final String s) {
		try {
			final JSONObject o = new JSONObject(s);
			return (T) deserialize(o, new HashMap<Integer,Object>());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object deserialize(final Object oo, final Map<Integer, Object> seen) throws JSONException, ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		if (oo instanceof String) return oo;
		if (oo instanceof Byte) return oo;
		if (oo instanceof Short) return oo;
		if (oo instanceof Integer) return oo;
		if (oo instanceof Long) return oo;
		if (oo instanceof Float) return oo;
		if (oo instanceof Double) return oo;
		if (oo instanceof Boolean) return oo;
		if (oo instanceof Character) return oo;
		final JSONObject o = (JSONObject) oo;
		final int id = o.getInt("-id");
		final Object seenObject = seen.get(id);
		if (seenObject != null) return seenObject;
		final Class<?> cls = cl.loadClass(o.getString("-type"));
		final Set<String> keySet = o.keySet();
		final Object obj = buildIt(cls, o, keySet, seen);
		seen.put(id, obj);
		if (Collection.class.isAssignableFrom(cls)) {
			final JSONArray data = o.getJSONArray("-data");
			final Collection c = (Collection) obj;
			for (final JSONObject x : data.asListJSONObject()) {
				c.add(deserialize(x, seen));
			}
		}
		if (Map.class.isAssignableFrom(cls)) {
			final JSONArray data = o.getJSONArray("-data");
			final Map m = (Map) obj;
			final int length = data.length();
			for (int i=0; i<length; ++i) {
				final JSONArray ja = data.getJSONArray(i);
				m.put(deserialize(ja.get(0), seen), deserialize(ja.get(1), seen));
			}
		}
		for (String key : keySet) {
			if (key.startsWith("-")) continue;
			final Object v = o.get(key);
			Class<?> fieldClass = cls;
			final String origKey = key;
			while (key.startsWith("~")) {
				fieldClass = fieldClass.getSuperclass();
				key = key.substring(1);
			}
			final Field f = fieldClass.getDeclaredField(key);
			if (f == null) {
				log.warning("unknown field "+ key +" for class "+ fieldClass.getName());
				continue;
			}
			final int modifiers = f.getModifiers();
			if (Modifier.isFinal(modifiers)) {
				modifiersField.set(f, f.getModifiers() & ~Modifier.FINAL);
			}
			f.setAccessible(true);
			final Class<?> fType = f.getType();
			if (String.class.equals(fType)) {
				f.set(obj, v);
			} else
			if (fType.isPrimitive()) {
				if (fType == Byte.TYPE) f.set(obj, (byte) o.getInt(origKey));
				if (fType == Short.TYPE) f.set(obj, (short) o.getInt(origKey)); else
				if (fType == Integer.TYPE) f.set(obj, o.getInt(origKey)); else
				if (fType == Long.TYPE) f.set(obj, (byte) o.getLong(origKey)); else
				if (fType == Float.TYPE) f.set(obj, (float) o.getDouble(origKey)); else
				if (fType == Double.TYPE) f.set(obj, o.getDouble(origKey)); else
				if (fType == Boolean.TYPE) f.set(obj, o.getBoolean(origKey)); else
				if (fType == Character.TYPE) f.set(obj, o.getString(origKey).charAt(0)); else
				f.set(obj, v);
			} else
			if (fType.isArray()) {
				final Class<?> cType = fType.getComponentType();
				final JSONArray va = (JSONArray)v;
				final int length = va.length();
				final Object a = Array.newInstance(cType, length);
				if (cType.isPrimitive()) {
					for (int i=0; i<length; ++i) {
						Array.set(a, i, va.get(i));
					}
				} else {
					for (int i=0; i<length; ++i) {
						Array.set(a, i, deserialize(va.get(i), seen));
					}
				}
			} else
			if (v instanceof JSONObject) {
				f.set(obj, deserialize(v, seen));
			}
			else throw new RuntimeException("i don't know how to set a "+ v +" to a "+ f);
		}
		return obj;
	}

	private final Field modifiersField;
	{
		try {
			modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	Map<Class<?>,InstanceCreator<?>> creators = new HashMap<Class<?>,InstanceCreator<?>>();

	public <T> Pickle registerInstanceCreator(final Class<T> cls, final InstanceCreator<T> ic) {
		creators.put(cls, ic);
		return this;
	}

	public interface InstanceCreator<T> {
		public T create(Map<Field, Object> data);
	}

	private Object buildIt(final Class<?> cls, final JSONObject o, final Set<String> keySet, final Map<Integer, Object> seen) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, JSONException, SecurityException, ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
		final InstanceCreator<?> ic = creators.get(cls);
		if (ic != null) {
			final Map<Field, Object> data = new HashMap<Field, Object>();
			final Map<Field, String> fields = new HashMap<Field, String>();
			final Set<String> retainSet = new HashSet<String>();
			for (String key : keySet) {
				if (key.startsWith("-")) {
					retainSet.add(key);
					continue;
				}
				Class clss = cls;
				final String origKey = key;
				while (key.startsWith("~")) {
					key = key.substring(1);
					clss = clss.getSuperclass();
				}
				final Field f = clss.getDeclaredField(key);
				f.setAccessible(true);
				data.put(f, deserialize(o.get(origKey), seen));
				fields.put(f, origKey);
			}
			final Object ret = ic.create(data);
			for (final Field f : data.keySet()) {
				retainSet.add(fields.get(f));
			}
			keySet.retainAll(retainSet);
			return ret;
		}
		try {
			final Constructor<?> c = cls.getDeclaredConstructor();
			c.setAccessible(true);
			final Object ret = c.newInstance();
			return ret;
		} catch (final Exception e) {
			//log.warning("no default constructor found for "+ cls.getName() +" ::: "+ e.toString());
			final Set<String> keys = new HashSet<String>();
			for (final String key : keySet) if (!key.startsWith("-") && !key.startsWith("~")) keys.add(key);
			if (keys.size() != 1) {
				throw new RuntimeException("no no-arg constructors found and have too many keys to try to match a 1-arg constructor: "+ keys);
			}
			final String key = keys.iterator().next();
			for (final Constructor<?> c : cls.getDeclaredConstructors()) {
				c.setAccessible(true);
				final Class<?>[] params = c.getParameterTypes();
				if (params.length == 1) {
					final Class<?> param = params[0];
					final Object po = deserialize(o.get(key), seen);
					if (!param.isInstance(po)) continue;
					if (param.isPrimitive()) {
						return c.newInstance(o.get(key));
					} else {
						return c.newInstance(po);
					}
				}
			}
			throw new RuntimeException("could not find a no-arg or 1-arg constructor for "+ cls.getName());
		}
	}

}