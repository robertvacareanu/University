package controller;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import commands.Commander;
import model.DictionaryEntry;
import model.Word;

public class Dictionary {

	private List<DictionaryEntry> synonymsDictionary;

	public Dictionary() {
		synonymsDictionary = new LinkedList<>();
	}

	public List<DictionaryEntry> getDictionaryData() {
		return synonymsDictionary;
	}

	public HashMap<String, Set<String>> getData() {
		HashMap<String, Set<String>> result = new HashMap<>();

		for (DictionaryEntry de : synonymsDictionary) {
			Set<String> words = new LinkedHashSet<>();
			for (Word w : de.getSynonyms()) {
				words.add(w.getWord());
			}
			result.put(de.getWord().getWord(), words);
		}

		return result;
	}

	public HashMap<Word, Set<Word>> getWords() {
		HashMap<Word, Set<Word>> result = new HashMap<>();

		for (DictionaryEntry de : synonymsDictionary) {
			result.put(de.getWord(), de.getSynonyms());
		}

		return result;
	}

}
