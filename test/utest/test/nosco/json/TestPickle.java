package test.nosco.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.kered.dko.json.Pickle;

import junit.framework.TestCase;

public class TestPickle extends TestCase {

	Pickle pickle = new Pickle();

	public void testList1() {
		final String s = pickle.serialize(new ArrayList<Integer>());
		System.out.println(s);
		final List<Integer> res = pickle.deserialize(s);
		assertEquals(0, res.size());
		assertTrue(s.contains("\"size\":0"));
		assertTrue(s.contains("\"-data\":[]"));
		assertTrue(s.contains("\"-type\":\"java.util.ArrayList\""));
	}

	public void testList2() {
		final ArrayList<Integer> src = new ArrayList<Integer>();
		src.add(1234);
		src.add(4567);
		final String s = pickle.serialize(src);
		System.out.println(s);
		final List<Integer> dest = pickle.deserialize(s);
		assertEquals(2, dest.size());
		assertEquals((Integer)1234, dest.get(0));
		assertEquals((Integer)4567, dest.get(1));
	}

	public void testMap() {
		final Map<Integer,String> src = new HashMap<Integer,String>();
		src.put(1234, "2345");
		src.put(4567, "5678");
		final String s = pickle.serialize(src);
		System.out.println(s);
		final Map<Integer,String> dest = pickle.deserialize(s);
		assertEquals(2, dest.size());
		assertEquals("2345", dest.get(1234));
		assertEquals("5678", dest.get(4567));
	}

	public void testInteger() {
		final String s = pickle.serialize(1234);
		System.out.println(s);
		final Integer dest = pickle.deserialize(s);
		assertEquals((Integer)1234, dest);
	}

	public void testLinkedMap() {
		final Map<Integer,String> src = new LinkedHashMap<Integer,String>();
		src.put(1234, "2345");
		src.put(4567, "5678");
		final String s = pickle.serialize(src);
		System.out.println(s);
		final Map<Integer,String> dest = pickle.deserialize(s);
		assertEquals(2, dest.size());
		final Iterator<Entry<Integer, String>> i = dest.entrySet().iterator();
		Entry<Integer, String> e = i.next();
		assertEquals((Integer)1234,e.getKey());
		assertEquals("2345",e.getValue());
		e = i.next();
		assertEquals((Integer)4567,e.getKey());
		assertEquals("5678",e.getValue());
	}

}
