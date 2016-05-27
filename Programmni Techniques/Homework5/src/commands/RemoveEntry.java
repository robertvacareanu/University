package commands;

import java.util.List;

import model.DictionaryEntry;

public class RemoveEntry implements Command {

	private Commander commander;
	private DictionaryEntry entry;
	
	public RemoveEntry(List<DictionaryEntry> data) {
		commander = new Commander(data);
	}
	
	public void loadEntry(DictionaryEntry entry) {
		this.entry = entry;
	}
	
	@Override
	public void execute() {
		commander.removeEntry(entry);
	}

}
