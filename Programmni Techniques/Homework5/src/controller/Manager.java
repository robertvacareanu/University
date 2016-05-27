package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import commands.AddEntry;
import commands.Command;
import commands.RemoveEntry;
import commands.SearchWord;
import model.DictionaryEntry;
import model.Word;
import view.MainWindow;

public class Manager implements Observer{
	private Dictionary dictionary;// = Dictionary.getInstance();
	private MainWindow mainWindow;
	private HashMap<String, Command> commands;
	private static final String FILENAME = "managerData.txt";
	
	public Manager() {
		try {
			FileInputStream input = new FileInputStream(new File(FILENAME));
			BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
			String s = new String();
			StringBuilder sb = new StringBuilder();
			while((s = buffer.readLine()) != null) {
				sb.append(s);
			}
			String json = sb.toString();
			dictionary = new Gson().fromJson(json, Dictionary.class);
			
		} catch (IOException e) {
			
			dictionary = new Dictionary();
			e.printStackTrace();
		}
		

		mainWindow = new MainWindow(dictionary.getData());
		mainWindow.addObserver(this);
		commands = new HashMap<>();
		addCommand("Add", new AddEntry(dictionary.getDictionaryData()));
		addCommand("Remove", new RemoveEntry(dictionary.getDictionaryData()));
		addCommand("Search", new SearchWord(dictionary.getDictionaryData()));
	}
	
	public void addCommand(String key, Command command) {
		commands.put(key, command);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(((String) arg).equals("Add")) {
			((AddEntry) commands.get("Add")).loadEntry(mainWindow.getDictionaryEntry());
			commands.get("Add").execute();
		} else if(((String) arg).equals("Remove")){
			((RemoveEntry) commands.get("Remove")).loadEntry(mainWindow.getDictionaryEntry());
			commands.get("Remove").execute();
		} else if(((String) arg).equals("Search")) {
			((SearchWord) commands.get("Search")).loadPattern(mainWindow.getString());
			commands.get("Search").execute();
			mainWindow.getAnswerData(((SearchWord) commands.get("Search")).getAnswer());
		}
		
		Gson gson = new Gson();
		String data = gson.toJson(dictionary);
		FileOutputStream output;
		
		try {
			output = new FileOutputStream(new File(FILENAME));
			output.write(data.getBytes());
			output.close();
			
		} catch (Exception e) {
			
		} 
		
		

		mainWindow.updateUI(dictionary.getData());
	}
}
