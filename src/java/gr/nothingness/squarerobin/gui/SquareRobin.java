package gr.nothingness.squarerobin.gui;

import gr.nothingness.squarerobin.model.Scheduler;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class SquareRobin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_NAME = "Square Robin";
	private JPanel contentPane;
	private JButton runButton;
	private ArrayList<String> clubs = new ArrayList<>();	
	private JScrollPane inputScrollPane;
	private JScrollPane scheduleScrollPane;
	private JScrollPane leagueScrollPane;
	private JTable inputTable;
	private JTable scheduleTable;
	private JTable leagueTable;
	private Scheduler scheduler;
	private JButton clearButton;
	private JTextField roundsTextField;
	private JLabel roundsLabel;

	private SquareRobin() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.initComponents();
		this.initLayoutManager();		
	}
	
	private boolean parseInput() {
		String cell;
		
		this.clubs.clear();
		for (int i = 0; i < this.inputTable.getRowCount(); i++) {
			cell = (String) this.inputTable.getValueAt(i, 0);
			if (cell != null && !cell.equals("")) {
				this.clubs.add(cell);
			}
		}
		try {
			this.scheduler = new Scheduler(this.clubs, Integer.valueOf(this.roundsTextField.getText()));
			return true;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		return false;
	}
	
	private void displaySchedule() {
		this.initScheduleModel();
		try {
			HashMap<Integer, TreeMap<Integer, ArrayList<String[]>>> schedule = scheduler.getSchedule();
			int rowAt = 0;
			for (int round : schedule.keySet()) {
				this.scheduleTable.setValueAt("Round " + round, rowAt++, 0);
				for (int day : schedule.get(round).keySet()) {
					this.scheduleTable.setValueAt("Day " + day, rowAt++, 1);
					for (String[] pair : schedule.get(round).get(day)) {
						this.scheduleTable.setValueAt(pair[0], rowAt, 2);
						this.scheduleTable.setValueAt(pair[1], rowAt, 3);
						this.scheduleTable.setValueAt(pair[2] + " - " + pair[3], rowAt, 4);						
						rowAt++;
					}
				}
			}
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	private void displayLeagueTable() {
		TreeMap<Integer, String[]> league;
		DefaultTableModel model;		
		String[] values;
		
		this.initLeagueModel();
		model = (DefaultTableModel)this.leagueTable.getModel();
		try {
			league = scheduler.getLeagueTable(true);
			for (int index : league.keySet()) {
				values = new String[6];				
				values[0] = league.get(index)[0];
				values[1] = league.get(index)[1];
				values[2] = league.get(index)[2];
				values[3] = league.get(index)[3];
				values[4] = league.get(index)[4];
				values[5] = league.get(index)[5];
				model.addRow(values);
			}
			this.leagueTable.setModel(model);
			model.fireTableDataChanged();
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private void initComponents() {
		this.setMinimumSize(new Dimension(900, 600));
		this.setTitle(SquareRobin.APPLICATION_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1227, 576);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.initInputTable();
		this.initScheduleTable();
		this.initLeagueTable();
		this.initButtons();
		this.initTextFields();
	}
	
	private void clearTables() {
		this.initInputModel();
		this.initScheduleModel();
		this.initLeagueModel();
	}
	
	private void initInputTable() {		
		this.inputTable = new JTable();
		this.inputScrollPane = new JScrollPane();
		this.inputScrollPane.setViewportView(this.inputTable);
		this.inputTable.putClientProperty("terminateEditOnFocusLost", true);
		this.initInputModel();
	}
	
	private void initScheduleTable() {
		this.scheduleTable = new JTable();
		this.scheduleScrollPane = new JScrollPane();			
		this.scheduleScrollPane.setViewportView(this.scheduleTable);
		this.initScheduleModel();
	}
	
	private void initLeagueTable() {
		this.leagueTable = new JTable();
		this.leagueScrollPane = new JScrollPane();
		this.leagueScrollPane.setViewportView(this.leagueTable);
		this.initLeagueModel();
	}
	
	private void initButtons() {
		this.runButton = new JButton("Go!");
		this.runButton.addActionListener(this);
		this.runButton.setMnemonic(KeyEvent.VK_G);
		this.clearButton = new JButton("Clear");
		this.clearButton.addActionListener(this);
		this.clearButton.setMnemonic(KeyEvent.VK_C);
	}
	
	private void initTextFields() {
		this.roundsLabel = new JLabel("Rounds");
		this.roundsTextField = new JTextField();		
		this.roundsTextField.setColumns(10);
		this.roundsTextField.setText("2");
	}
	
	private void initInputModel() {
		String[] headers = { "Club" };
		DefaultTableModel model = new DefaultTableModel(256, headers.length);
		
		model.setColumnIdentifiers(headers);
		this.inputTable.setModel(model);
	}
	
	private void initScheduleModel() {
		String[] headers = { "Round", "Day", "Home", "Away", "Score" };
		DefaultTableModel model = new NonEditableTableModel(256, headers.length);
		
		model.setColumnIdentifiers(headers);
		this.scheduleTable.setModel(model);
	}
	
	private void initLeagueModel() {
		String[] headers = { "Position", "Club", "Points", "Scored", "Conceded", "Goal Average" };
		DefaultTableModel model = new NonEditableTableModel(0, headers.length);
		
		model.setColumnIdentifiers(headers);
		this.leagueTable.setModel(model);
	}

	private void initLayoutManager() {
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.scheduleScrollPane, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.leagueScrollPane, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(2)
					.addComponent(this.roundsLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.roundsTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(450)
					.addComponent(this.runButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(this.clearButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(523))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.scheduleScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.leagueScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(this.clearButton)
							.addComponent(this.runButton))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(this.roundsLabel)
							.addComponent(this.roundsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		this.contentPane.setLayout(gl_contentPane);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.runButton) {
			if (this.parseInput()) {
				this.displaySchedule();
				this.displayLeagueTable();
			}
		} else if (event.getSource() == this.clearButton) {			
			this.clearTables();
		}
	}
	
	private static final class SwingStartUp implements Runnable {
		
		public void run() {
			new SquareRobin().setVisible(true);
		}
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new SwingStartUp());
	}

}