package controller;

import java.util.TreeSet;

import model.Term;
import model.TermComparator;
import model.Polynomial;

/**
 * 
 * @author Robert This class is responsible for reading the user input
 */
public class InputReader {

	/**
	 * 
	 * @param text:
	 *            the string resulted from user input
	 * @return a polynomial parsed from the user input
	 */
	public Polynomial read(String text) {

		TreeSet<Term> coefficients = new TreeSet<>(new TermComparator());
		String input = text.replaceAll("[x+^*]", "");
		String[] parts = input.split(" ");

		for (int i = 1; i <= parts.length; i += 2) {
			coefficients.add(new Term(Integer.parseInt(parts[i]), Integer.parseInt(parts[i - 1])));
		}

		return new Polynomial(coefficients);

	}

}
