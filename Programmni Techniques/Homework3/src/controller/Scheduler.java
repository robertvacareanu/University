package controller;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import model.Server;
import model.Task;

/**
 * 
 * @author Robert This class is responsible with scheduling tasks among servers
 */
public class Scheduler {

	private int time;

	private Comparator<Server> c = new Comparator<Server>() {

		@Override
		public int compare(Server o1, Server o2) {
			return Integer.compare(o1.getTotalTime(), o2.getTotalTime());
		}

	};
	List<Server> servers = new LinkedList<>();

	public Scheduler(int time) {
		this.time = time;
		Server server = new Server();
//		server.addTask(new Task(1, 1));
//		server.addTask(new Task(2, 2));
//		server.addTask(new Task(3, 3));
//		server.addTask(new Task(4, 4));
//		server.addTask(new Task(5, 5));
//		server.addTask(new Task(6, 6));
//		server.addTask(new Task(7, 7));
//		server.addTask(new Task(8, 8));
//		server.addTask(new Task(9, 9));
		servers.add(server);
		System.out.println("STARTED " + servers.get(0));
		new Thread(servers.get(0)).start();
	}

	public List<Server> getServers() {
		return servers;
	}

	public void addTask(Task t) {
		servers.sort(c);
		if(servers.size() < 1) {
			servers.add(new Server());
		}
		servers.get(0).addTask(t);
		System.out.println("Servers: " + servers);
	}
	
	private synchronized int totalTasks() {
		int result = 0;
		for(Server s : servers) {
			result += s.getBq().length;
		}
		return result;
	}
	
	public synchronized int getMaxTime() {
		int result = 0;
		for(Server s : servers) {
			if(s.getTotalTime() > result) {
				result = s.getTotalTime();
			}
		}
		return result;
	}

	public synchronized double averageTime() {
		double result = 0;
		for (int i = 0; i < servers.size(); i++) {
			result += servers.get(i).getTotalTime();
		}
		System.out.println("RESULT: " + result / servers.size());
		return result / servers.size();
	}

	private void startNewServer() {
		System.out.println("HERE1");
		Server newServer = new Server();
		for (int i = 0; i < servers.size(); i++) {
			System.out.println("INFOR");
			// AVerage the servers out one by one
			while (servers.get(i).getTotalTime() >= averageTime()) {
				System.out.println("YES");
				Task t = servers.get(i).getLastTask();
				if (t != null && newServer.getTotalTime() < averageTime()) {
					System.out.println("MOVE " + t + " TO NEXT SERVER");
					newServer.addTask(t);
					System.out.println("THE NEW SERVER CONTAINS: " + newServer);
				} else {
					// If the new server reached the average is enough;
					break;
				}
			}
		}
		servers.add(newServer);

		new Thread(newServer).start();
	}

	private synchronized void redistribution() {
		List<Server> more = new LinkedList<>();
		List<Server> less = new LinkedList<>();
		for (int i = 0; i < servers.size(); i++) {
			if (servers.get(i).getTotalTime() > averageTime()) {
				more.add(servers.get(i));
			} else {
				less.add(servers.get(i));
			}
		}
		double average = averageTime();
		for (int i = 0; i < less.size(); i++) {
			for (int j = 0; j < more.size(); j++) {
				while(more.get(j).getTotalTime() > average) {
					less.get(i).addTask(more.get(j).getLastTask());
				}
			}
		}
	}

	public void newServer(int maxQueues) {
		// Logic for adding a new server
		boolean result = false;
		for (int i = 0; i < servers.size(); i++) {
			if (servers.get(i).getTotalTime() > time) {
				result = true;
			}
		}
		for (int i = 0; i < servers.size(); i++) {
			if (servers.get(i).getBq().length == 0) {
				if(servers.size()>1&&(totalTasks() < 2 || Math.random()>0.8)){
					System.out.println("EXIT");
					servers.remove(i);
				} else {
					System.out.println("REDISTRIBUTION");
					redistribution();
				}

				result = false;
			}
		}
		if(maxQueues == servers.size()) {
			result = false;
		}
		if (result) {
			startNewServer();
		}
	}
	
	public void reset(int time) {
		this.time = time;
		servers.clear();
	}

}
