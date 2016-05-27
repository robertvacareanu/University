package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.ParameterObserver;
import model.Server;
import model.Task;

public class SimulatorFrame extends JFrame {

	public static final String CUSTOMER = "G:\\Workspace\\FacultyProgramming\\Java\\ProgramminTechniques\\Homework3\\src\\resources\\Customer.png";
	public static final String OFFICE = "G:\\Workspace\\FacultyProgramming\\Java\\ProgramminTechniques\\Homework3\\src\\resources\\Office.png";

	private JPanel options;
	private JPanel panel;
	private ParameterObserver po = new ParameterObserver();

	private int[] results = new int[5];

	public SimulatorFrame() {
		// getResults();
		setLayout(new BorderLayout());
		options = new JPanel();
		JButton optionsButton = new JButton("Options");
		optionsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getResults();
			}
		});

		options.add(optionsButton);

		panel = new JPanel(new GridLayout(1, 0));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		// stop = new JPanel();
		// JButton stopB = new JButton("Stop");
		// stopB.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// }
		// });
		// stop.add(stopB);
		add(options, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		// add(stop, BorderLayout.NORTH);
		setSize(300, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void displayData(Task[] tasks, int waitingTime, int id) {
		panel.add(new QueueRepresentation(tasks, waitingTime, id));
		panel.revalidate();
		panel.repaint();

	}

	public void displayData(List<Server> servers) {
		panel.removeAll();
		panel.revalidate();
		servers.sort(new Comparator<Server>() {
			@Override
			public int compare(Server o1, Server o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});

		// JList<Task> taskList[] = new JList[servers.size()];
		// JScrollPane sp[] = new JScrollPane[servers.size()];
		for (int i = 0; i < servers.size(); i++) {
			displayData(servers.get(i).getBq(), servers.get(i).getTotalTime(), servers.get(i).getId());
		}

		panel.revalidate();
		panel.repaint();
	}

	public void displayError(String error) {
		JOptionPane.showMessageDialog(null, error, "Error", ERROR, null);
	}

	public void displayEnd(int maxTime, double averageTime) {
		JOptionPane.showMessageDialog(this, "The End\nMax Time: " + maxTime + "\nAverage Time: " + averageTime, "End", JOptionPane.PLAIN_MESSAGE, null);
	}

	private void getResults() {

		JPanel message = new JPanel();
		message.setMinimumSize(new Dimension(200, 500));
		message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));

		JPanel p1 = new JPanel();
		JLabel timeLimit = new JLabel("Time Limit: ");
		JTextField timeLimitField = new JTextField();
		timeLimitField.setPreferredSize(new Dimension(140, 20));

		JPanel p2 = new JPanel();
		JLabel minProcTime = new JLabel("Minimum Process Time: ");
		JTextField minProcTimeField = new JTextField();
		minProcTimeField.setPreferredSize(new Dimension(140, 20));

		JPanel p3 = new JPanel();
		JLabel maxProcTime = new JLabel("Maximum Process Time: ");
		JTextField maxProcTimeField = new JTextField();
		maxProcTimeField.setPreferredSize(new Dimension(140, 20));

		JPanel p4 = new JPanel();
		JLabel maxQueues = new JLabel("Max Queues: ");
		JTextField maxQueuesField = new JTextField();
		maxQueuesField.setPreferredSize(new Dimension(140, 20));

		JPanel p5 = new JPanel();
		JLabel newQueue = new JLabel("New Queues: ");
		JTextField newQueueField = new JTextField();
		newQueueField.setPreferredSize(new Dimension(140, 20));

		p1.add(timeLimit);
		p1.add(timeLimitField);
		p2.add(minProcTime);
		p2.add(minProcTimeField);
		p3.add(maxProcTime);
		p3.add(maxProcTimeField);
		p4.add(maxQueues);
		p4.add(maxQueuesField);
		p5.add(newQueue);
		p5.add(newQueueField);

		message.add(p1);
		message.add(p2);
		message.add(p3);
		message.add(p4);
		message.add(p5);

		int option = JOptionPane.showConfirmDialog(null, message, "Parameters", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			results[0] = Integer.valueOf(timeLimitField.getText());
			results[1] = Integer.valueOf(minProcTimeField.getText());
			results[2] = Integer.valueOf(maxProcTimeField.getText());
			results[3] = Integer.valueOf(maxQueuesField.getText());
			results[4] = Integer.valueOf(newQueueField.getText());
			po.setChanged();
			po.notifyObservers(results);
			// System.out.println(results[0] + "\t" + results[1] + "\t" + results[2] + "\t" + results[3]);
		} else {
			System.exit(1);
		}

	}

	public void addObserver(Observer o) {
		po.addObserver(o);
	}

}
