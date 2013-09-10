package gr.daemon.squarerobin.gui;

import gr.daemon.squarerobin.model.Game;
import gr.daemon.squarerobin.model.Round;
import gr.daemon.squarerobin.model.Scheduler;
import gr.daemon.squarerobin.model.Season;
import gr.daemon.squarerobin.model.Slot;
import gr.daemon.squarerobin.model.Team;
import gr.daemon.squarerobin.model.exceptions.DuplicateEntryException;
import gr.daemon.squarerobin.model.exceptions.DuplicateTeamsException;
import gr.daemon.squarerobin.model.exceptions.EndOfLeagueException;
import gr.daemon.squarerobin.model.exceptions.GameAlreadySettledException;
import gr.daemon.squarerobin.model.exceptions.GameNotSettledException;
import gr.daemon.squarerobin.model.exceptions.InsufficientTeamsException;
import gr.daemon.squarerobin.model.exceptions.InvalidRoundsException;
import gr.daemon.squarerobin.model.exceptions.OddTeamNumberException;
import gr.daemon.squarerobin.model.exceptions.BreaksLimitException;
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
	private JButton runGameButton;
	private JButton runSlotButton;
	private JButton runSeasonButton;

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
		} catch(DuplicateTeamsException | InsufficientTeamsException | OddTeamNumberException | InvalidRoundsException | BreaksLimitException | DuplicateEntryException | GameAlreadySettledException e) {
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
		final DefaultTableModel model;
		final String[] values = new String[6];
		final Team[] leagueTable = this.season.getLeagueTable().sortFormally();

		this.initLeagueModel();				
		model = (DefaultTableModel)this.leagueTable.getModel();
		for (final Team team : leagueTable) {
			Arrays.fill(values, null);
			values[0] = team.getRelativePosition();
			values[1] = team.getName();
			values[2] = Integer.toString(team.getPoints());
			values[3] = Integer.toString(team.getGoalsFor());
			values[4] = Integer.toString(team.getGoalsAgainst());
			values[5] = Integer.toString(team.getGoalAverage());
			model.addRow(values);
		}
		this.leagueTable.setModel(model);
		model.fireTableDataChanged();
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

	private void clearView() {
		this.initInputModel();
		this.initScheduleModel();
		this.initLeagueModel();
		this.runGameButton.setEnabled(false);
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
		this.runGameButton = new JButton("Run Game");
		this.runGameButton.setEnabled(false);
		this.runGameButton.addActionListener(this);
		this.runGameButton.setMnemonic(KeyEvent.VK_A);
		this.runSlotButton = new JButton("Run Slot");
		this.runSlotButton.addActionListener(this);
		this.runSlotButton.setMnemonic(KeyEvent.VK_L);
		this.runSlotButton.setEnabled(false);
		this.runSeasonButton = new JButton("Run Season");
		this.runSeasonButton.addActionListener(this);
		this.runSeasonButton.setMnemonic(KeyEvent.VK_E);
		this.runSeasonButton.setEnabled(false);
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
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addGap(2)
							.addComponent(this.roundsLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.roundsTextField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
							.addComponent(this.runButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.runGameButton, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.runSlotButton, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.runSeasonButton, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.clearButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, layout.createSequentialGroup()
							.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.scheduleScrollPane, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.leagueScrollPane, GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))
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
							.addComponent(this.roundsLabel)
							.addComponent(this.roundsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(this.clearButton)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(this.runButton)
							.addComponent(this.runGameButton)
							.addComponent(this.runSlotButton)
							.addComponent(this.runSeasonButton)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		this.contentPane.setLayout(layout);
	}
	
	public void actionPerformed(final ActionEvent event) throws EndOfLeagueException {
		if (event.getSource() == this.runButton) {
			if (this.parseInput()) {
				this.displaySchedule();
				this.displayLeagueTable();
				this.runGameButton.setEnabled(true);
				this.runSlotButton.setEnabled(true);
				this.runSeasonButton.setEnabled(true);
			}
		} else if (event.getSource() == this.clearButton) {
			this.clearView();
		} else if (event.getSource() == this.runGameButton) {
			try {
				this.season.getLeagueRunner().runGame();
				this.displaySchedule();
				this.displayLeagueTable();
			} catch(EndOfLeagueException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());				
			}
		} else if (event.getSource() == this.runSlotButton) {
			try {
				this.season.getLeagueRunner().runSlot();
				this.displaySchedule();
				this.displayLeagueTable();
			} catch(EndOfLeagueException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());				
			}
		} else if (event.getSource() == this.runSeasonButton) {
			try {
				this.season.getLeagueRunner().runSeason();
				this.displaySchedule();
				this.displayLeagueTable();
			} catch(EndOfLeagueException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());				
			}
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