package controller;

import java.util.Observable;
import java.util.Observer;

import model.Task;
import view.SimulatorFrame;

public class Simulator implements Runnable, Observer {

	private int timeLimit = 50;
	private int minProcTime = 1;
	private int maxProcTime = 10;
	private int currentTime = 0;
	private int maxQueues = 5;
	private int newQueueTime = 15;
	private int maxTime = 0;
	private double averageTime = 0;

	private Scheduler s;
	private SimulatorFrame sf;

	public Simulator() {
		s = new Scheduler(newQueueTime);
		sf = new SimulatorFrame();
		// Observe the change of parameters
		sf.addObserver(this);
	}

	@Override
	public void run() {
		System.out.println("RERUN");
		while (currentTime < timeLimit) {
			currentTime++;

			// Not adding every time
			//if (Math.random() > 0.7) {
				createTask();
			//}
			System.out.println("NR OF SERVERS: " + s.getServers().size());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				sf.displayError("InterruptedException");
				e.printStackTrace();
			}
				s.newServer(maxQueues);

			sf.displayData(s.getServers());
			if (currentTime == timeLimit / 2) {
				averageTime = s.averageTime();
			}
			synchronized (s) {
				int max = s.getMaxTime();
				if (max > maxTime) {
					maxTime = max;
				}
			}

		}
		sf.displayEnd(maxTime, averageTime);

	}

	public void createTask() {
		int processTime = (int) (Math.random() * 1000) % (maxProcTime - minProcTime) + minProcTime;
		Task t = new Task(currentTime, processTime);
		s.addTask(t);

	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("CHANGED12345");
		int[] result = (int[]) arg;
		timeLimit = result[0];
		minProcTime = result[1];
		maxProcTime = result[2];
		maxQueues = result[3];
		newQueueTime = result[4];
		reset();
	}

	private void reset() {
		currentTime = 0;
		maxTime = 0;
		averageTime = 0;
		s.reset(newQueueTime);
	}

}
