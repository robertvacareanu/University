package model;

import java.util.Set;

public interface I_DictionaryEntry {

	public Word getWord();
	public void addWord(Word word);
	public void removeWord(Word word);
	public Set<Word> getSynonyms();
	
	
}
