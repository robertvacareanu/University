package commands;

import java.util.LinkedList;
import java.util.List;

public class Macro {
	
	private final List<Command> commands;
	
	
	public Macro() {
		commands = new LinkedList<>();
	}
	
	public void record(Command command) {
		commands.add(command);
	}
	
	public void run() {
		commands.forEach(Command::execute);
	}
	
}
