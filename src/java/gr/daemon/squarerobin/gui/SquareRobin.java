package gr.daemon.squarerobin.gui;

import gr.daemon.squarerobin.model.Scheduler;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	private JTextArea matchesTextArea;
	private ArrayList<String> clubs = new ArrayList<>();
	private JTable inputTable;
	private JScrollPane inputScrollPane;

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
		this.matchesTextArea.setText("");
		for (int i = 0; i < this.inputTable.getRowCount(); i++) {
			cell = (String) this.inputTable.getValueAt(i, 0);
			if (cell != null && !cell.equals("")) {
				this.clubs.add(cell);
			}
		}
	}
	
	private void displaySchedule() {
		String output = "";
		try {
			Scheduler scheduler = new Scheduler(this.clubs);
			HashMap<Integer, HashMap<Integer, ArrayList<String[]>>> schedule = scheduler.getSchedule();
			for (int round : schedule.keySet()) {
				output += "Round " + round + "\n";
				for (int day : schedule.get(round).keySet()) {
					output += "Day " + day + "\n";
					for (String[] pair : schedule.get(round).get(day)) {						
						output += pair[0] + " - " + pair[1] + "\n";
					}
				}
			}
			this.matchesTextArea.setText(output);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private void initComponents() {
		String[] columns = { "Club" };
		DefaultTableModel inputModel = new DefaultTableModel(256, columns.length);
		inputModel.setColumnIdentifiers(columns);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 550, 700);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);		
		this.runButton = new JButton("Go!");
		this.runButton.addActionListener(this);
		this.matchesTextArea = new JTextArea();		
		this.inputTable = new JTable();		
		this.inputTable.setModel(inputModel);
		this.inputTable.putClientProperty("terminateEditOnFocusLost", true);
		this.inputScrollPane = new JScrollPane();
		this.inputScrollPane.setViewportView(this.inputTable);
		this.setMinimumSize(new Dimension(900, 700));
		this.setTitle(SquareRobin.APPLICATION_NAME);
	}
	
	private void initLayoutManager() {
		GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(this.runButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.matchesTextArea, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(285, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.inputScrollPane, GroupLayout.PREFERRED_SIZE, 514, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.matchesTextArea, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.runButton)
					.addContainerGap(110, Short.MAX_VALUE))
		);
		this.contentPane.setLayout(gl_contentPane);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.runButton) {
			this.parseInput();
			this.displaySchedule();
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