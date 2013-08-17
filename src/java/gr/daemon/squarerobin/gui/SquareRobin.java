package gr.daemon.squarerobin.gui;

import gr.daemon.squarerobin.model.Scheduler;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class SquareRobin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_NAME = "Square Robin";
	private JPanel contentPane;
	private JButton runButton;
	private ArrayList<String> clubs = new ArrayList<>();
	private JTable inputTable;
	private JScrollPane inputScrollPane;
	private JScrollPane scheduleScrollPane;
	private JTable scheduleTable;
	private JScrollPane leagueScrollPane;
	private JTable leagueTable;
	private Scheduler scheduler;

	public SquareRobin() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.initComponents();
		this.initLayoutManager();
	}
	
	private void parseInput() {
		String cell = "";
		
		this.clubs.clear();
		for (int i = 0; i < this.inputTable.getRowCount(); i++) {
			cell = (String) this.inputTable.getValueAt(i, 0);
			if (cell != null && !cell.equals("")) {
				this.clubs.add(cell);
			}
		}
		this.scheduler = new Scheduler(this.clubs, 1);
	}
	
	private void displaySchedule() {
		try {
			HashMap<Integer, HashMap<Integer, ArrayList<String[]>>> schedule = scheduler.getSchedule();
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
		HashMap<String, String[]> league;
		DefaultTableModel model = (DefaultTableModel) this.leagueTable.getModel();		
		String[] values;
		
		try {			
			league = scheduler.getLeagueTable();
			for (String club : league.keySet()) {
				values = new String[6];				
				values[1] = club;
				values[2] = league.get(club)[0];
				values[4] = league.get(club)[1];
				values[5] = league.get(club)[2];
				model.addRow(values);
				this.leagueTable.setModel(model);
				model.fireTableDataChanged();
			}
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private void initComponents() {
		String[] inputColumns = { "Club" };
		String[] scheduleColumns = { "Round", "Day", "Home", "Away", "Score" };
		String[] leagueColumns = { "Position", "Club", "Points", "Goal Average", "Scored", "Conceded" };
		DefaultTableModel inputModel = new DefaultTableModel(256, inputColumns.length);
		DefaultTableModel scheduleModel = new DefaultTableModel(256, scheduleColumns.length);
		DefaultTableModel leagueModel = new DefaultTableModel(0, leagueColumns.length);
		inputModel.setColumnIdentifiers(inputColumns);
		scheduleModel.setColumnIdentifiers(scheduleColumns);
		leagueModel.setColumnIdentifiers(leagueColumns);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1227, 700);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);		
		this.runButton = new JButton("Go!");
		this.runButton.addActionListener(this);
		this.inputTable = new JTable();		
		this.inputTable.setModel(inputModel);
		this.inputTable.putClientProperty("terminateEditOnFocusLost", true);
		this.inputScrollPane = new JScrollPane();
		this.inputScrollPane.setViewportView(this.inputTable);		
		this.scheduleTable = new JTable();
		this.scheduleTable.setModel(scheduleModel);
		this.scheduleScrollPane = new JScrollPane();		
		//this.scheduleScrollPane.setColumnHeaderView(this.scheduleTable);
		this.scheduleScrollPane.setViewportView(this.scheduleTable);
		this.leagueTable = new JTable();
		this.leagueTable.setModel(leagueModel);
		this.leagueScrollPane = new JScrollPane();
		//this.leagueScrollPane.setRowHeaderView(this.leagueTable);
		this.leagueScrollPane.setViewportView(this.leagueTable);
		this.setMinimumSize(new Dimension(900, 700));
		this.setTitle(SquareRobin.APPLICATION_NAME);
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
					.addContainerGap()
					.addComponent(this.runButton)
					.addContainerGap(1142, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.runButton))
						.addComponent(this.scheduleScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.leagueScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(109, Short.MAX_VALUE))
		);
		this.contentPane.setLayout(gl_contentPane);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.runButton) {
			this.parseInput();
			this.displaySchedule();
			this.displayLeagueTable();
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