
import controller.Manager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

	public static void main(String[] args) {

		// Dictionary dictionary = new Dictionary();
		// DictionaryEntry entry = new DictionaryEntry(new Word("a"));
		// entry.addWord(new Word("b"));
		// entry.addWord(new Word("C"));
		// dictionary.addEntry(entry);
		// new MainWindow(dictionary.getData());
		new Manager();
//		System.out.println(Pattern.matches("*", "ABCDSA"));
	}

}
