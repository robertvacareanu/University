package controller;

import view.MainWindow;

public class Main {

	public static void main(String[] args) {

		/*
		 * TermComparator tc = new TermComparator(); TreeSet<Term> tree1 = new TreeSet<>(tc); TreeSet<Term> tree2 = new
		 * TreeSet<>(tc);
		 * 
		 * tree1.add(new Term(0, 1)); tree2.add(new Term(0, 1));
		 * 
		 * Polynomial p1 = new Polynomial(tree1); Polynomial p2 = new Polynomial(tree1);
		 * 
		 * System.out.println(p1.hashCode()); System.out.println(p2.hashCode()); System.out.println(p1.equals(p2));
		 */

		new Manager(new MainWindow(), new PolynomialOperations());

	}

}
