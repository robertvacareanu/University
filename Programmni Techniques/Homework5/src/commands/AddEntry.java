package commands;

import java.util.List;

import model.DictionaryEntry;

public class AddEntry implements Command {
	
	private Commander commander;
	private DictionaryEntry entry;
	
	public AddEntry(List<DictionaryEntry> data) {
		commander = new Commander(data);
	}
	
	public void loadEntry(DictionaryEntry entry) {
		this.entry = entry;
	}
	
	
	@Override
	public void execute() {
		commander.addEntry(entry);
	}
	
}