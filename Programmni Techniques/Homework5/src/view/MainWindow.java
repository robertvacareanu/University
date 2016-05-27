package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import commands.Command;
import model.AddWordObserver;
import model.Caretaker;
import model.DictionaryEntry;
import model.Originator;
import model.Word;

public class MainWindow extends JFrame {
	
	private AddWordObserver observer;
	private JPanel top, center;
	private JButton search, add, undo, redo, remove;
	private JTextField wordTextField;
	private JScrollPane centerPane;
	private JTable table;
	
	private Command command;
	
	private List<DictionaryEntry> answer;
	
	private Caretaker<Word> caretaker = new Caretaker<>();
	private Originator originator = new Originator();
	private DictionaryEntry newEntry;
	private int saved = 0; // We start with no word saved
	private int current = -1; // No word saved
	
	public MainWindow(HashMap<String, Set<String>> data) {
		setSize(600, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		observer = new AddWordObserver();
		
		top = new JPanel();
		center = new JPanel();
		search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				originator.set(new Word(wordTextField.getText()));
				caretaker.addMemento(originator.storeInMemento());
				saved++;
				current++;
				undo.setEnabled(true);
				System.out.println(saved + " " + current);
				
			}
		});
		add = new JButton("Add");
		remove = new JButton("Remove");
		undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("UNDO: " + saved + " " + current);
				if(current>0) {
					current--;
					wordTextField.setText(originator.restoreFromMemento(caretaker.getMemento(current)).getWord());
					redo.setEnabled(true);
				} else {
					undo.setEnabled(false);
				}
				
			}
		});
		redo = new JButton("Redo");
		redo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(saved-1 > current) {
					current++;
					wordTextField.setText(originator.restoreFromMemento(caretaker.getMemento(current)).getWord());
					undo.setEnabled(true);
				} else {
					redo.setEnabled(false);
				}
				
			}
		});
		wordTextField = new JTextField();
		wordTextField.setPreferredSize(new Dimension(100, 25));
		
		table = new JTable();
		centerPane = new JScrollPane(table);
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton addEntry = new JButton("Add");
				System.out.println("YES");
				JPanel dialogPanel = new JPanel();
				JLabel word = new JLabel("Word");
				JLabel synonyms = new JLabel("Synonyms");
				JTextField wordText = new JTextField();
				wordText.setPreferredSize(new Dimension(100, 25));
				JTextField synonymsText = new JTextField();
				synonymsText.setPreferredSize(new Dimension(100, 25));
				
				dialogPanel.add(word);
				dialogPanel.add(wordText);
				dialogPanel.add(synonyms);
				dialogPanel.add(synonymsText);
				addEntry.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String setSynonyms = synonymsText.getText();
						String[] set = setSynonyms.split(",");
						Set<Word> synonyms = new LinkedHashSet<>();
						for(String s : set) {
							synonyms.add(new Word(s));
						}
						loadNewEntry(new Word(wordText.getText()), synonyms);
						observer.notifyObservers("Add");
					}
				});
				dialogPanel.add(addEntry);
				
				JDialog dialog = new JDialog();
				dialog.add(dialogPanel);
				dialog.setSize(new Dimension(100, 250));
				dialog.setVisible(true);

			}
		});
		
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadNewEntry(new Word(wordTextField.getText()), new LinkedHashSet<Word>());
				observer.notifyObservers("Remove");
			}
		});
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				observer.notifyObservers("Search");
				JFrame answerFrame = new JFrame();
				answerFrame.setSize(600, 480);
				
				answerFrame.add(new JScrollPane(createTable(getData())));
				
				answerFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				answerFrame.setVisible(true);
				
			}
		});
		
		top.add(undo);
		top.add(redo);
		top.add(add);
		top.add(wordTextField);
		top.add(search);
		top.add(remove);

		
		center.add(centerPane);
		
		updateUI(data);
		
		add(top, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public void updateUI(HashMap<String, Set<String>> data) {
		// Re-Add Tables;
		centerPane.setViewportView(createTable(data));
		revalidate();
	}

	private JTable createTable(HashMap<String, Set<String>> data) {
		Set<String> keysSet = data.keySet();
		String[] keys = new String[keysSet.size()];
		keys = keysSet.toArray(keys);
		List<String> values = new LinkedList<>();
		for(String k : keys) {
			StringBuilder s = new StringBuilder();
			Set<String> synonyms = data.get(k);
			String[] synonymsArray = new String[synonyms.size()];
			synonymsArray = synonyms.toArray(synonymsArray);
			for(String value : synonymsArray) {
				s.append(value);
				if(value != synonymsArray[synonymsArray.length-1]) {
					s.append(", ");
				}
			}
			values.add(s.toString());
		}
		Object[][] tableData = new Object[keys.length][2];
		for(int i=0; i<keys.length; i++) {
			tableData[i][0] = keys[i];
			tableData[i][1] = values.get(i);
		}
		String[] columns = {"Word", "Synonyms"};
		JTable table = new JTable(new DefaultTableModel(tableData, columns));
		
		
		return table;
	}
	
	public Command getCommand() {
		return command;
	}
	
	private void loadNewEntry(Word word, Set<Word> synonyms) {
		newEntry = new DictionaryEntry(word, synonyms);
	}
	
	public DictionaryEntry getDictionaryEntry() {
		return newEntry;
	}
	
	public String getString() {
		return wordTextField.getText();
	}
	
	public void addObserver(Observer o) {
		observer.addObserver(o);
	}
	
	public void getAnswerData(List<DictionaryEntry> data) {
		this.answer = data;
	}
	
	private HashMap<String, Set<String>> getData() {
		HashMap<String, Set<String>> result = new HashMap<>();

		for (DictionaryEntry de : answer) {
			Set<String> words = new LinkedHashSet<>();
			for (Word w : de.getSynonyms()) {
				words.add(w.getWord());
			}
			result.put(de.getWord().getWord(), words);
		}

		return result;
	}

}



