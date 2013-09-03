package org.kered.dko.util;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.kered.dko.ant.SchemaExtractor;

public class DumpDatabase {

	private static final String CARD_1 = "CARD_1";
	private static final String CARD_2 = "CARD_2";
	private static final String CARD_3 = "CARD_3";

	private static JButton next;
	private static JButton prev;
	private static JPanel cards;
	private static CardLayout cl;
	private static Map<String,NextListener> nexters = new HashMap<String,NextListener>();
	private static JTextField server = null;
	private static JTextField username = null;
	private static JPasswordField password = null;
	private static JButton finish;
	private static JFrame frame;

	private static final Stack<String> cardStack = new Stack<String>();

	static Map<String,Map<String,Map<String,String>>> schemas = null;

	private static class SchemaGetter implements Runnable {
		@Override
		public void run() {
			schemas = null;
			SchemaExtractor se = new SchemaExtractor();
			se.setUsername(username.getText());
			se.setPassword(new String(password.getPassword()));
			try {
				se.setURL(server.getText());
				schemas = se.getDatabaseTableColumnTypes();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (CARD_3.equals(cardStack.peek())) nexters.get(CARD_3).show();
		}
	}

	private static void createAndShowGUI() {
		frame = new JFrame("DKO Dump Database");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cards = new JPanel(new CardLayout());
		cl = (CardLayout)(cards.getLayout());
		frame.getContentPane().add(cards);

		buildCard1();
		buildCard2();
		buildCard3();

		buildButtonBar();

		cardStack.add(CARD_1);
		nexters.get(CARD_1).show();

		frame.pack();
		frame.setVisible(true);
	}

	public static void buildCard1() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Welcome to the DKO Database Dumper!");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(new EmptyBorder(15, 20, 15, 20));
		card.add(title);
		JLabel question = new JLabel("What kind of database are you pulling from?");
		question.setAlignmentX(Component.CENTER_ALIGNMENT);
		question.setBorder(new EmptyBorder(5, 10, 5, 10));
		card.add(question);
		String[] databaseTypes = {"SQL Server"};
		final JComboBox dbTypeSelectBox = new JComboBox(databaseTypes);
		dbTypeSelectBox.setBorder(new EmptyBorder(5, 10, 5, 10));
		card.add(dbTypeSelectBox);
		server = new JTextField("jdbc:sqlserver://server:1433");
		server.setBorder(new EmptyBorder(2, 10, 2, 10));
		username = new JTextField("sa");
		username.setBorder(new EmptyBorder(2, 10, 2, 10));
		password = new JPasswordField("password");
		password.setBorder(new EmptyBorder(2, 10, 2, 10));
		card.add(server);
		card.add(username);
		card.add(password);
		cards.add(card, CARD_1);
		final NextListener nexter = new NextListener() {
			@Override
			public void goNext() {
				new Thread(new SchemaGetter()).start();
				cardStack.add(CARD_2);
				cl.show(cards, CARD_2);
			}
			@Override
			public void show() {
				next.setVisible(true);
				finish.setVisible(false);
				Object selectedItem = dbTypeSelectBox.getModel().getSelectedItem();
				next.setEnabled(!"".equals(selectedItem) && !server.getText().isEmpty());
			}
		};
		dbTypeSelectBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nexter.show();
			}
		});
		nexters.put(CARD_1, nexter);
	}

	protected static File file = null;

	public static void buildCard2() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Where would you like to dump to?");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(new EmptyBorder(15, 20, 15, 20));
		card.add(title);
		final JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File("test2.db"));

		final JButton open = new JButton("Select a SQLite3 File...");
		open.setAlignmentX(Component.CENTER_ALIGNMENT);
		card.add(open);

		final JLabel fnLabel = new JLabel();
		fnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		fnLabel.setBorder(new EmptyBorder(15, 20, 15, 20));
		card.add(fnLabel);

		final NextListener nexter = new NextListener() {
			@Override
			public void goNext() {
				cardStack.add(CARD_3);
				cl.show(cards, CARD_3);
			}
			@Override
			public void show() {
				next.setVisible(true);
				finish.setVisible(false);
				next.setEnabled(fc.getSelectedFile() != null);
	            file = fc.getSelectedFile();
	            if (file == null) {
		            fnLabel.setText("");
	            } else {
		            fnLabel.setText(file.getName() + (file.exists() ? "" : " (new)"));
	            }
			}
		};

		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(open);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
				}
				nexter.show();
			}
		});

		cards.add(card, CARD_2);
		nexters.put(CARD_2, nexter);
	}

	public static void buildCard3() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("What table do you want to dump?");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(new EmptyBorder(15, 20, 15, 20));
		card.add(title);

		final JComboBox schemaSelectBox = new JComboBox();
		schemaSelectBox.setBorder(new EmptyBorder(5, 10, 5, 10));
		schemaSelectBox.setMaximumSize(new Dimension(schemaSelectBox.getMaximumSize().width,
				schemaSelectBox.getPreferredSize().height));
		card.add(schemaSelectBox);

		final JComboBox tableSelectBox = new JComboBox();
		tableSelectBox.setBorder(new EmptyBorder(5, 10, 5, 10));
		tableSelectBox.setMaximumSize(new Dimension(tableSelectBox.getMaximumSize().width,
				tableSelectBox.getPreferredSize().height));
		card.add(tableSelectBox);

		schemaSelectBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object schema = schemaSelectBox.getSelectedItem();
				Map<String, Map<String, String>> tableData = schemas==null ? null : schemas.get(schema);
				Set<String> tables = tableData==null ? new HashSet<String>() : tableData.keySet();
				tableSelectBox.setModel(new DefaultComboBoxModel(tables.toArray(new String[0])));
				tableSelectBox.setSelectedIndex(tableSelectBox.getSelectedIndex());
			}
		});

		final JTextArea query = new JTextArea(6, 6);
		final JRadioButton top1000 = new JRadioButton("Select first 1000 rows");
		final JRadioButton all = new JRadioButton("Select all rows            ");
		final JRadioButton custom = new JRadioButton("Select custom query  ");

		final ActionListener genSQL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object schema = schemaSelectBox.getSelectedItem();
				Object table = tableSelectBox.getSelectedItem();
				if (schema==null || table==null) {
					query.setText("Loading database schema, please wait...");
					query.setEditable(false);
					return;
				}
				Set<String> columns = schemas.get(schema).get(table).keySet();
				StringBuffer sb = new StringBuffer();
				for (String column : columns) {
					sb.append(column).append(", ");
				}
				sb.delete(sb.length()-2, sb.length());
				String colString = sb.toString();
				if (top1000.isSelected()) {
					query.setText("select top 1000 "+ colString +"\nfrom "+ schema +".."+ table +";");
					query.setEditable(false);
				}
				if (all.isSelected()) {
					query.setText("select "+ colString +"\nfrom "+ schema +".."+ table +";");
					query.setEditable(false);
				}
				if (custom.isSelected()) {
					query.setText("select "+ colString +"\nfrom "+ schema +".."+ table +"\nwhere ???;");
					query.setEditable(true);
				}
			}
		};

		tableSelectBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				genSQL.actionPerformed(null);
			}
		});

		schemaSelectBox.setSelectedIndex(schemaSelectBox.getSelectedIndex());

		top1000.setAlignmentX(Component.CENTER_ALIGNMENT);
		top1000.setSelected(true);
		top1000.addActionListener(genSQL);
		all.setAlignmentX(Component.CENTER_ALIGNMENT);
		all.addActionListener(genSQL);
		custom.setAlignmentX(Component.CENTER_ALIGNMENT);
		custom.addActionListener(genSQL);
		final ButtonGroup group = new ButtonGroup();
		group.add(top1000);
		group.add(all);
		group.add(custom);
		card.add(top1000);
		card.add(all);
		card.add(custom);

		query.setLineWrap(true);
		query.setEditable(true);
		query.setVisible(true);
	    JScrollPane scroll = new JScrollPane(query);
	    card.add(scroll);

		card.add(Box.createRigidArea(new Dimension(0,10)));
		final JButton dump = new JButton("Dump");
		dump.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Connection srcConn = null;
						Connection destConn = null;
						Statement srcStmt = null;
						try {
							destConn = DriverManager.getConnection("jdbc:sqlite:"+ file.getAbsolutePath());
							srcConn = DriverManager.getConnection(server.getText(), username.getText(),
									new String(password.getPassword()));
							srcStmt = srcConn.createStatement();
							ResultSet rs = srcStmt.executeQuery(query.getText());
							ResultSetMetaData metaData = rs.getMetaData();
							for (int i=0; i<metaData.getColumnCount(); ++i) {
								System.out.println(metaData.getColumnName(i+1));
							}
							while (rs.next()) {

							}
						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							if (srcStmt!=null) {
								try {
									srcStmt.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
							if (srcConn!=null) {
								try {
									srcConn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
							if (destConn!=null) {
								try {
									destConn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}

					}
				}).start();
			}
		});
		dump.setAlignmentX(Component.CENTER_ALIGNMENT);
		card.add(dump);
		card.add(Box.createRigidArea(new Dimension(0,5)));

		final NextListener nexter = new NextListener() {
			@Override
			public void goNext() {}
			@Override
			public void show() {
				List<String> schemaList = new ArrayList<String>();
				if (schemas != null) {
					for (String schema : schemas.keySet()) {
						if ("msdb".equals(schema) || "master".equals(schema)) continue;
						schemaList.add(schema);
					}
				}
				schemaSelectBox.setModel(new DefaultComboBoxModel(schemaList.toArray(new String[0])));
				schemaSelectBox.setSelectedIndex(schemaSelectBox.getSelectedIndex());
				next.setVisible(false);
				finish.setVisible(true);
			}
		};

		cards.add(card, CARD_3);
		nexters.put(CARD_3, nexter);
	}

	public static void buildButtonBar() {
		JPanel buttonBar = new JPanel();
		buttonBar.setLayout(new FlowLayout());
		buttonBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		buttonBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		prev = new JButton("Previous");
		next = new JButton("Next");
		finish = new JButton("Finish");
		prev.setEnabled(false);
		next.setEnabled(false);
		finish.setVisible(false);
		prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardStack.pop();
				String cardId = cardStack.peek();
				cl.show(cards, cardId);
				nexters.get(cardId).show();
				//prev.setEnabled(cardStack.size() > 1);
			}
		});
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NextListener nextListener = nexters.get(cardStack.peek());
				nextListener.goNext();
				prev.setEnabled(!cardStack.isEmpty());
				next.setEnabled(false);
				nexters.get(cardStack.peek()).show();
			}
		});
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		buttonBar.add(finish);
		buttonBar.add(next);
		buttonBar.add(prev);
		frame.getContentPane().add(buttonBar);
	}

	private interface NextListener {
		void show();
		void goNext();
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
