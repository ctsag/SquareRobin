package gr.daemon.squarerobin.gui;

import gr.daemon.squarerobin.model.DuplicateEntryException;
import gr.daemon.squarerobin.model.DuplicateTeamsException;
import gr.daemon.squarerobin.model.Game;
import gr.daemon.squarerobin.model.GameAlreadySettledException;
import gr.daemon.squarerobin.model.GameNotSettledException;
import gr.daemon.squarerobin.model.InsufficientTeamsException;
import gr.daemon.squarerobin.model.InvalidRoundsException;
import gr.daemon.squarerobin.model.OddTeamNumberException;
import gr.daemon.squarerobin.model.Round;
import gr.daemon.squarerobin.model.Scheduler;
import gr.daemon.squarerobin.model.Season;
import gr.daemon.squarerobin.model.Slot;
import gr.daemon.squarerobin.model.ThreeInARowException;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SquareRobin extends JFrame implements ActionListener {

	private static final String APPLICATION_NAME = "Square Robin";
	private final ArrayList<String> teams = new ArrayList<>();
	private JPanel contentPane;
	private JButton runButton;		
	private JScrollPane inputScrollPane;
	private JScrollPane scheduleScrollPane;
	private JScrollPane leagueScrollPane;
	private JTable inputTable;
	private JTable scheduleTable;
	private JTable leagueTable;
	private Season season;
	private JButton clearButton;
	private JTextField roundsTextField;
	private JLabel roundsLabel;

	public SquareRobin() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		this.initComponents();
		this.initLayoutManager();
	}

	private boolean parseInput() {
		String cell;

		this.teams.clear();
		for (int i = 0; i < this.inputTable.getRowCount(); i++) {
			cell = (String) this.inputTable.getValueAt(i, 0);
			if (cell != null && !cell.equals("")) {
				this.teams.add(cell);
			}
		}
		try {
			final String[] teams = this.teams.toArray(new String[this.teams.size()]);
			final Scheduler scheduler = new Scheduler("2013", teams, Integer.valueOf(this.roundsTextField.getText()));
			this.season = scheduler.getSeason();			
		} catch(DuplicateTeamsException | InsufficientTeamsException | OddTeamNumberException | InvalidRoundsException | ThreeInARowException | DuplicateEntryException | GameAlreadySettledException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			return false;
		}
		return true;
	}

	private void displaySchedule() throws GameNotSettledException {		
		DefaultTableModel model;
		String[] values = new String[6];

		this.initScheduleModel();
		model = (DefaultTableModel)this.scheduleTable.getModel();
		try {			
			for (final Round round : this.season.getRounds()) {
				Arrays.fill(values, null);
				values[0] = "Round " + round.getIndex();
				model.addRow(values);
				for (final Slot slot : round.getSlots()) {
					Arrays.fill(values, null);
					values[1] = "Slot " + slot.getIndex();
					model.addRow(values);
					for (final Game game : slot.getGames()) {
						Arrays.fill(values, null);
						values[2] = "Game " + game.getIndex();
						model.addRow(values);
						Arrays.fill(values, null);
						values[3] = game.getHomeTeam().getName();
						values[4] = game.getAwayTeam().getName();
						if (game.isSettled()) {
							values[5] = game.getScore();
						}
						model.addRow(values);
					}
				}
			}
			this.scheduleTable.setModel(model);
			model.fireTableDataChanged();
		} catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private void displayLeagueTable() {
//		TreeMap<Integer, String[]> league;
//		DefaultTableModel model;
//		String[] values = new String[6];
//
//		this.initLeagueModel();
//		model = (DefaultTableModel)this.leagueTable.getModel();
//		try {
//			league = scheduler.getLeagueTable(true);
//			for (final int index : league.keySet()) {
//				Arrays.fill(values, null);
//				values[0] = league.get(index)[0];
//				values[1] = league.get(index)[1];
//				values[2] = league.get(index)[2];
//				values[3] = league.get(index)[3];
//				values[4] = league.get(index)[4];
//				values[5] = league.get(index)[5];
//				model.addRow(values);
//			}
//			this.leagueTable.setModel(model);
//			model.fireTableDataChanged();
//		} catch(IllegalArgumentException e) {
//			JOptionPane.showMessageDialog(this, e.getMessage());
//		}
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
		final String[] headers = {"Club"};
		final DefaultTableModel model = new DefaultTableModel(256, headers.length);

		model.setColumnIdentifiers(headers);
		this.inputTable.setModel(model);
	}

	private void initScheduleModel() {
		final String[] headers = {"Round", "Day", "Game", "Home", "Away", "Score"};
		final DefaultTableModel model = new NonEditableTableModel(0, headers.length);

		model.setColumnIdentifiers(headers);
		this.scheduleTable.setModel(model);
	}

	private void initLeagueModel() {
		final String[] headers = {"Position", "Club", "Points", "Scored", "Conceded", "Goal Average"};
		final DefaultTableModel model = new NonEditableTableModel(0, headers.length);

		model.setColumnIdentifiers(headers);
		this.leagueTable.setModel(model);
	}

	private void initLayoutManager() {
		final GroupLayout layout = new GroupLayout(this.contentPane);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.scheduleScrollPane, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.leagueScrollPane, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
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
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.scheduleScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.leagueScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(this.clearButton)
							.addComponent(this.runButton))
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(this.roundsLabel)
							.addComponent(this.roundsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		this.contentPane.setLayout(layout);
	}
	
	public void actionPerformed(final ActionEvent event) {
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

	public static void main(final String[] args) {
		EventQueue.invokeLater(new SwingStartUp());
	}
}