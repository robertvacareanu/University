package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JOptionPane;

import view.MainWindow;

/**
 * 
 * @author Robert This class is the manager of the system Glues together the model, the view and the controller All the
 *         updates for the view must come from this class
 */
public class Manager {

	private HashMap<String, Runnable> commands = new HashMap<>();
	private InputReader input = new InputReader();
	private MainWindow frame;
	private PolynomialOperations polOperations;
	private static final String ERROR_MESSAGE = "There is an error.\nPlease check your input.";

	public Manager(MainWindow frame, PolynomialOperations pol) {
		this.frame = frame;
		this.polOperations = pol;
		commands.put("addition", () -> frame.setResult(polOperations.addition(input.read(frame.getFirstText()), input.read(frame.getSecondText())).toString()));
		commands.put("multiplication", () -> frame.setResult(polOperations.multiplication(input.read(frame.getFirstText()), input.read(frame.getSecondText())).toString()));
		commands.put("division", () -> frame.setResult("Q:" + polOperations.division(input.read(frame.getFirstText()), input.read(frame.getSecondText()))[0].toString() + "\tR:"
				+ polOperations.division(input.read(frame.getFirstText()), input.read(frame.getSecondText()))[1].toString()));
		commands.put("integrate", () -> frame.setResult(polOperations.integrate(input.read(frame.getFirstText())).toString()));
		commands.put("subtraction", () -> frame.setResult(polOperations.subtraction(input.read(frame.getFirstText()), input.read(frame.getSecondText())).toString()));
		commands.put("scalarMultiplication", () -> frame.setResult(polOperations.scalarMultiplication(input.read(frame.getFirstText()), Integer.valueOf(frame.getSecondText())).toString()));
		commands.put("evaluate", () -> frame.setResult(String.valueOf(polOperations.evaluate(input.read(frame.getFirstText()), Integer.valueOf(frame.getSecondText())))));
		commands.put("derivate", () -> frame.setResult(polOperations.derivate(input.read(frame.getFirstText())).toString()));
		frame.addSubmitActionListener(new ButtonPressed());
	}

	private class ButtonPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				commands.get(frame.getSelection()).run();
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, ERROR_MESSAGE, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
