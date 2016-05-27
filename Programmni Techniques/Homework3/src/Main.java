import javax.swing.JOptionPane;

import controller.Simulator;
import view.SimulatorFrame;

public class Main {

	public static void main(String[] args) {
		new Thread(new Simulator()).start();;
	}

}
