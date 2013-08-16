package gr.daemon.squarerobin.gui;

import gr.daemon.squarerobin.cli.State;
import gr.daemon.squarerobin.model.Scheduler;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SquareRobin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String APPLICATION_NAME = "Square Robin";
	private JPanel contentPane;
	private JButton runButton;
	private JTextArea inputTextArea;
	private JTextArea matchesTextArea;
	private ArrayList<String> clubs = new ArrayList<>();

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
		this.matchesTextArea.setText("");
		for (String line : this.inputTextArea.getText().split("\n")) {
			if (!line.equals("\n")) {
				this.clubs.add(line);
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 550, 700);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.runButton = new JButton("Go!");
		this.runButton.addActionListener(this);
		this.inputTextArea = new JTextArea();		
		this.matchesTextArea = new JTextArea();
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
							.addGap(4)
							.addComponent(this.inputTextArea, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.matchesTextArea, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(280, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.inputTextArea, GroupLayout.PREFERRED_SIZE, 512, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.matchesTextArea, GroupLayout.PREFERRED_SIZE, 512, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.runButton)
					.addContainerGap(111, Short.MAX_VALUE))
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