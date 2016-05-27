package commands;

import java.util.List;

import model.DictionaryEntry;

public interface I_Dictionary {
	/**
	 * 
	 * @param entry -- Not null
	 * Should mantain the collection well formed
	 */
	public void addEntry(DictionaryEntry entry);
	public void removeEntry(DictionaryEntry entry);
	public List<DictionaryEntry> searchWord(String regEx);
	
}
