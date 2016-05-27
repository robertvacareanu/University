package commands;

import java.util.List;

import model.DictionaryEntry;

public class SearchWord implements Command {

	private Commander commander;
	private String entry;
	private List<DictionaryEntry> answer;
	
	public SearchWord(List<DictionaryEntry> data) {
		commander = new Commander(data);
	}
	
	
	public void loadPattern(String entry) {
		this.entry = entry;
	}
	
	public List<DictionaryEntry> getAnswer() {
		return answer;
	}
	
	
	@Override
	public void execute() {
		answer = commander.searchWord(entry);
	}

}
