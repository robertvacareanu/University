package model;

public class Task {

	private int arrivalTime;
	private int processTime;
	
	public Task(int arrivalTime, int processTime) {
		this.arrivalTime = arrivalTime;
		this.processTime = processTime;
	}

	public int getArrivalTim() {
		return arrivalTime;
	}

	public void setArrivalTim(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getProcessTime() {
		return processTime;
	}

	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return arrivalTime + "  " + processTime;
	}
	
}
