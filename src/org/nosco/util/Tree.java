package org.nosco.util;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree<T> implements Cloneable {

	public T data = null;
	public List<Tree<T>> children = new LinkedList<Tree<T>>();
	
	public boolean contains(T... path) {
		for (Tree<T> t : children) {
			if (t.contains(0, path)) return true;
		}
		return false;
	}
	
	private boolean contains(int offset, T... path) {
		if (path==null || path.length==offset) return true;
		if ((data == null && path[offset] == null) || (data != null && data.equals(path[offset]))) {
			for (Tree<T> t : children) {
				if (t.contains(offset+1, path)) return true;
			}
		}
		return false;
	}
	
	public Tree<T> clone() {
		Tree<T> t = new Tree<T>();
		t.data = data;
		for (Tree<T> c : children) {
			t.children.add(c.clone());
		}
		return t;
	}

	public void add(T... path) {
		add(null, 0, path);
	}

	public void add(Callback<T> cb, T... path) {
		add(cb, 0, path);
	}

	private void add(Callback<T> cb, int offset, T... path) {
		if (path==null || path.length==offset) return;
		for (Tree<T> t : children) {
			if ((t == null && path[offset] == null) || (t != null && t.equals(path[offset]))) {
				t.add(cb, offset+1, path);
				return;
			}
		}
		Tree<T> t = new Tree<T>();
		t.data = path[offset];
		children.add(t);
		if (cb!=null) cb.call(path[offset], offset+1, path);
		t.add(cb, offset+1, path);
	}
	
	public void visit(Callback<T> cb) {
		visit(cb, 0, new LinkedList<T>());
	}
	
	private void visit(Callback<T> cb, int depth, Queue<T> path) {
		path.add(data);
		@SuppressWarnings("unchecked")
		T[] cbPath = null;
		if (data != null) {
			cbPath = (T[]) Array.newInstance(data.getClass(),path.size());
			path.toArray(cbPath);
		}
		cb.call(data, depth, cbPath);
		for (Tree<T> c : children) {
			c.visit(cb, depth+1, path);
		}
		path.remove();
	}
	
	public static abstract class Callback<T> {
		public abstract void call(T t, int depth, T[] path);
	}

}
