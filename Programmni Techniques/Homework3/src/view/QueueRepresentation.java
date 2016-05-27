package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Task;

public class QueueRepresentation extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 73406941027409429L;

	JLabel office;

	public QueueRepresentation(Task[] tasks, int waitingTime, int id) {
		setOpaque(false);
		office = new JLabel(new ImageIcon(SimulatorFrame.OFFICE));
		office.setAlignmentX(LEFT_ALIGNMENT);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// this.setAlignmentX(LEFT_ALIGNMENT);
		add(office);
		JList<Task> taskList = new JList<>(tasks);
		taskList.setOpaque(false);
		taskList.setCellRenderer(new ImageCellRenderer(SimulatorFrame.CUSTOMER));
		taskList.setAlignmentX(LEFT_ALIGNMENT);
		JScrollPane sp = new JScrollPane(taskList);
		sp.setAlignmentX(LEFT_ALIGNMENT);
		sp.setMaximumSize(new Dimension(100, 250));
		sp.setOpaque(false);
		// wrapper.add(office);
		add(sp);
		JLabel info = new JLabel("ID: " + id);
		info.setAlignmentX(LEFT_ALIGNMENT);
		JLabel info2 = new JLabel("Waiting: " + waitingTime);
		info2.setAlignmentX(LEFT_ALIGNMENT);
		add(info);
		add(info2);
	}
}
