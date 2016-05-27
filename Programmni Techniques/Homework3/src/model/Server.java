package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

	private static int genericId = 0;
	private int id;
	BlockingQueue<Task> bq = new LinkedBlockingQueue<>();
	AtomicInteger waitingTime = new AtomicInteger();

	public Server() {
		id = ++genericId;
	}

	public void addTask(Task t) {
		bq.add(t);
		waitingTime.addAndGet(t.getProcessTime());
	}

	@Override
	public void run() {
		while (true) {
			Task currentTask = null;
			try {
				currentTask = bq.take();
				 System.out.println("SLEEP: " + currentTask.getProcessTime() * 1000);
				Thread.sleep(currentTask.getProcessTime() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitingTime.addAndGet((-1) * currentTask.getProcessTime());
		}
	}

	public Task[] getBq() {
		Task[] tasks = new Task[bq.size()];
		bq.toArray(tasks);
		return tasks;
	}

	public int getTotalTime() {
		int result = 0;
		for (Task t : bq) {
			result += t.getProcessTime();
		}
		return result;
	}

	public Task getLastTask() {
//		System.out.println("LENGTH BEFORE: " + bq.size());
//		//Task[] returnTask = getBq();
//		Task t = getBq()[getBq().length- 1];
//		System.out.println("SERVER: " + id + " remove " + bq.remove(t));
//		System.out.println("LENGTH AFTER: " + bq.size());
//		return t;
		Task t = null;
		if(bq.size() >= 2) {
			t = getBq()[getBq().length- 1];
			bq.remove(t);
		} else if (bq.size() >=1) {
			try {
				t= bq.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Task t : bq) {
			sb.append(t + "  ");
		}
		return sb.toString();
	}

	public int getId() {
		return id;
	}

}
