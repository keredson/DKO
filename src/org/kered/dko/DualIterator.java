package org.kered.dko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.sql.DataSource;

class DualIterator {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private boolean done = false;
	private boolean finishedNatually = false;
	private Queue<Object[]> leftQueue = new LinkedList<Object[]>();
	private Queue<Object[]> rightQueue = new LinkedList<Object[]>();
	InternalIterator leftIterator = new InternalIterator(leftQueue);
	InternalIterator rightIterator = new InternalIterator(rightQueue);
	private List<Field<?>> qLfields;
	private List<Field<?>> qRfields;

	public DualIterator(DataSource ds, String sql, List<Field<?>> qLfields, List<Field<?>> qRfields) {
		this.qLfields = qLfields;
		this.qRfields = qRfields;
    	Util.log(sql, null);
    	try {
	    	conn = ds.getConnection();
	    	ps = conn.prepareStatement(sql);
	    	rs = ps.executeQuery();
    	} catch (SQLException e) {
    		throw new RuntimeException(e);
    	}
	}

	public PeekableClosableIterator<Object[]> getLeftIterator() {
		return leftIterator;
	}

	public PeekableClosableIterator<Object[]> getRightIterator() {
		return rightIterator;
	}

	public void close() {
		if (done) return;
		try {
			if (!finishedNatually && rs!=null && !rs.isClosed()) {
				ps.cancel();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (AbstractMethodError e) {
			if ("org.sqlite.RS".equals(rs.getClass().getName())) {
				// ignore - bad jdbc driver
			} else {
				throw e;
			}
		}
		try {
			if (rs!=null && !rs.isClosed()) rs.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (AbstractMethodError e) {
			if ("org.sqlite.RS".equals(rs.getClass().getName())) {
				// ignore - bad jdbc driver
			} else {
				throw e;
			}
		}
		try {
			if (ps!=null && !ps.isClosed()) ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (AbstractMethodError e) {
			if ("org.sqlite.RS".equals(rs.getClass().getName())) {
				// ignore - bad jdbc driver
			} else {
				throw e;
			}
		}
		try {
			if (conn!=null && !conn.isClosed()) conn.close();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		done = true;
	}
	
	public class InternalIterator implements PeekableClosableIterator<Object[]> {

		private Queue<Object[]> queue;
		private boolean closed = false;

		public InternalIterator(Queue<Object[]> queue) {
			this.queue = queue;
		}

		@Override
		public boolean hasNext() {
			if (queue.isEmpty()) getNextRow();
			return !queue.isEmpty();
		}

		@Override
		public Object[] next() {
			return queue.poll();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void close() {
			this.closed = true;
			if (leftIterator.closed && rightIterator.closed) {
				DualIterator.this.close();
			}
		}

		@Override
		public Object[] peek() {
			return queue.peek();
		}
		
	}

	public void getNextRow() {
		try {
			if (!rs.next()) return;
			int leftSize = qLfields.size();
			final Object[] leftRow = new Object[leftSize];
			for (int i=0; i<leftSize; ++i) {
				leftRow[i] = Util.getTypedValueFromRS(rs, i+1, qLfields.get(i));
			}
			leftQueue.add(leftRow);
			int rightSize = qRfields.size();
			final Object[] rightRow = new Object[rightSize];
			for (int i=0; i<rightSize; ++i) {
				rightRow[i] = Util.getTypedValueFromRS(rs, leftSize+i+1, qRfields.get(i));
			}
			rightQueue.add(rightRow);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
