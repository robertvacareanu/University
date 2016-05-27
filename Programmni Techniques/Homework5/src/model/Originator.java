package model;

public class Originator implements I_Memorable<Word>{

	private Word word;

	public void set(Word word) {
		this.word = word;
	}

	public Memento<Word> storeInMemento() {
		return new Memento<Word>(word);
	}

	public Word restoreFromMemento(Memento<Word> memento) {
		word = memento.getSaved();
		return word;
	}

}
