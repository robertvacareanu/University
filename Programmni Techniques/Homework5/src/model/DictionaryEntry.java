package model;

import java.util.LinkedHashSet;
import java.util.Set;

public class DictionaryEntry implements I_DictionaryEntry {

	private Word word;
	private Set<Word> synonyms;
	
	public DictionaryEntry(Word word) {
		synonyms = new LinkedHashSet<>();
		this.word = word;
	}
	
	public DictionaryEntry(Word word, Set<Word> synonyms) {
		this.synonyms = new LinkedHashSet<>(synonyms);
		this.word = word;
	}
	
	@Override
	public Word getWord() {
		return word;
	}

	@Override
	public void addWord(Word word) {
		synonyms.add(word);

	}

	@Override
	public void removeWord(Word word) {
		synonyms.remove(word);

	}

	@Override
	public Set<Word> getSynonyms() {
		return synonyms;
	}

}
