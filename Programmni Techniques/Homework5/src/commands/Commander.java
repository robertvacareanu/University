package commands;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import model.DictionaryEntry;
import model.Word;

public class Commander implements I_Dictionary {

	private List<DictionaryEntry> synonymsDictionary;
	// HashMap<Word, Set<Word>> data;

	protected Commander(List<DictionaryEntry> synonymsDictionary) {
		this.synonymsDictionary = synonymsDictionary;

	}

	private void addEntryUtil(DictionaryEntry entry) {
		assert (entry != null) : "Entry should not be null";
		for (DictionaryEntry de : synonymsDictionary) {
			if (de.getWord().getWord().equals(entry.getWord().getWord())) {
				Set<Word> toAdd = new LinkedHashSet<>(de.getSynonyms());
				toAdd.addAll(entry.getSynonyms());
				try {
					for (Word word : de.getSynonyms()) {
						for (DictionaryEntry de2 : synonymsDictionary) {
							if (word.getWord().equals(de2.getWord().getWord())) {
								de2.getSynonyms().addAll(toAdd);
							}
						}
					}
					for (Word word : entry.getSynonyms()) {
						for (DictionaryEntry de2 : synonymsDictionary) {
							if (word.getWord().equals(de2.getWord().getWord())) {
								de2.getSynonyms().addAll(toAdd);
							}
						}
					}
				} catch (ConcurrentModificationException cme) {
					System.out.println("NOT GOOD");
				}

				de.getSynonyms().addAll(entry.getSynonyms());
				return;
			}
		}

		synonymsDictionary.add(entry);
		assert (wellFormed() != false) : "Should remain well formed";

	}

	@Override
	public void addEntry(DictionaryEntry entry) {
		assert(entry != null) : "Should not be null";
		/**
		 * Add in a set all the word inputed. Search in the dictionary for them and add their synonyms. Add
		 */
		Set<Word> toAdd = new LinkedHashSet<>(entry.getSynonyms());
		toAdd.add(entry.getWord());

		Set<Word> copy;

		for (Word w : toAdd) {
			copy = new LinkedHashSet<>(toAdd);
			copy.remove(w);
			addEntryUtil(new DictionaryEntry(new Word(w.getWord()), copy));
		}

		for (DictionaryEntry de : synonymsDictionary) {
			Iterator<Word> it = de.getSynonyms().iterator();
			while (it.hasNext()) {
				if (it.next().getWord().equals(de.getWord().getWord())) {
					it.remove();
				}
			}
		}
		assert (wellFormed() != false) : "Should remain well formed";
	}

	@Override
	public void removeEntry(DictionaryEntry entry) {
		assert(entry != null) : "Should not be null";
		Iterator<DictionaryEntry> it = synonymsDictionary.iterator();

		while (it.hasNext()) {
			DictionaryEntry de = it.next();
			if (de.getWord().getWord().equals(entry.getWord().getWord())) {
				it.remove();
			}
		}
		
		for(DictionaryEntry de : synonymsDictionary) {
			Iterator<Word> wordIt = de.getSynonyms().iterator();
			while(wordIt.hasNext()) {
				Word w = wordIt.next();
				if(w.getWord().equals(entry.getWord().getWord())) {
					wordIt.remove();
				}
			}
		}
		
		if(synonymsDictionary.size() == 1) {
			removeEntry(synonymsDictionary.get(0));
		}
		
		it = synonymsDictionary.iterator();
		
		while(it.hasNext()) {
			
			if(it.next().getSynonyms().size() == 0) {
				it.remove();
			}
		}
		assert (wellFormed() != false) : "Should remain well formed";
	}

	@Override
	public List<DictionaryEntry> searchWord(String regEx) {
		assert(regEx != null) : "Should not be null";
		Stream<DictionaryEntry> stream = synonymsDictionary.stream();
		List<DictionaryEntry> result = new LinkedList<>();
		
		stream.filter(dictionaryEntry -> Pattern.matches(regEx, dictionaryEntry.getWord().getWord()))
			  .forEach(dictionaryEntry -> result.add(dictionaryEntry));
		assert (wellFormed() != false) : "Should remain well formed";
		return result;
	}

	public HashMap<Word, Set<Word>> getWords() {
		HashMap<Word, Set<Word>> result = new HashMap<>();

		for (DictionaryEntry de : synonymsDictionary) {
			result.put(de.getWord(), de.getSynonyms());
		}

		return result;
	}
	
	public boolean wellFormed() {
		boolean result = true;
		
		for(DictionaryEntry de : synonymsDictionary) {
			int total = de.getSynonyms().size();
			for(Word w : de.getSynonyms()) {
				for(DictionaryEntry de2 : synonymsDictionary) {
					if(w.getWord().equals(de2.getWord().getWord())) {
						total--;
					}
				}
			}
			System.out.println("TOTAL: "+ total + " " + result);
			if(total != 0) {
				result = false;
			}
		}
		
		
		return result;
	}

}
