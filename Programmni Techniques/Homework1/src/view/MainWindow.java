package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Robert This class is responsible with user interactions. Everything displayed is done through the Manager
 *
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8272784077724439792L;
	private JPanel contentPanel = new JPanel();
	private JTextField text1 = new JTextField();
	private JTextField text2 = new JTextField();
	private JTextField result = new JTextField();
	private JLabel title = new JLabel();
	private JPanel options = new JPanel();
	private JButton submit = new JButton("Submit");
	private JRadioButton[] inputOptions = new JRadioButton[8];
	private static final String INPUT_EXAMPLE = "Input example: 1 x^7 +2 x^5 +3 x^2 +4 x^1 +10 x^0\nTherefore, the input must be: \n\"coefficient\" + \"space\" + \"x\" + \"^\" + \"exponent\" + \"space\"";
	private static final int WIDTH = 335;
	private static final int HEIGHT = 320;
	private ButtonGroup op;
	public MainWindow() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Polynomial Operations");

		setLayout(new GridLayout(0, 1));

		inputOptions[0] = new JRadioButton("addition");
		inputOptions[1] = new JRadioButton("subtraction");
		inputOptions[2] = new JRadioButton("multiplication");
		inputOptions[3] = new JRadioButton("scalarMultiplication");
		inputOptions[4] = new JRadioButton("division");
		inputOptions[5] = new JRadioButton("evaluate");
		inputOptions[6] = new JRadioButton("integrate");
		inputOptions[7] = new JRadioButton("derivate");

		op = new ButtonGroup();
		options.setLayout(new GridLayout(4, 0));
		for (int i = 0; i < inputOptions.length; i++) {
			op.add(inputOptions[i]);
			options.add(inputOptions[i]);
		}

		inputOptions[0].setSelected(true);

		options.setMaximumSize(new Dimension(600, 100));
		options.setAlignmentX(SwingConstants.CENTER);

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));

		text1.setHorizontalAlignment(SwingConstants.CENTER);
		text2.setHorizontalAlignment(SwingConstants.CENTER);

		title.setText("Homework 1");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 40));
		title.addMouseListener(new MouseListener() {

			// We are only interested in mousePressed(MouseEvent e) method

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(MainWindow.this,
						"Input example: 1 x^7 +2 x^5 +3 x^2 +4 x^1 +10 x^0\nTherefore, the input must be: \n\"coefficient\" + \"space\" + \"x\" + \"^\" + \"exponent\" + \"space\"");

			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		JPanel submitPanel = new JPanel();
		submitPanel.add(submit);

		contentPanel.add(title);
		contentPanel.add(text1);
		contentPanel.add(text2);
		contentPanel.add(options);
		contentPanel.add(result);
		contentPanel.add(submitPanel);

		add(contentPanel);

		setVisible(true);

		JOptionPane.showMessageDialog(this, INPUT_EXAMPLE);
	}

	public void addSubmitActionListener(ActionListener al) {
		submit.addActionListener(al);
	}

	public String getSelection() {
		String inputName = inputOptions[0].getText();
		for (int i = 0; i < inputOptions.length; i++) {
			if (inputOptions[i].isSelected()) {
				inputName = inputOptions[i].getText();
				return inputName;
			}
		}
		return inputName;
	}

	public void setResult(String result) {
		if(result.length() == 0){
			this.result.setText("0");
		} else {
			this.result.setText(result);
		}
	}

	public String getFirstText() {
		return this.text1.getText().toString();
	}

	public String getSecondText() {
		return this.text2.getText().toString();
	}

}
