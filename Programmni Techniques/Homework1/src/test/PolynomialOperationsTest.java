package test;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import controller.PolynomialOperations;
import model.Term;
import model.TermComparator;
import model.Polynomial;

public class PolynomialOperationsTest {

	TermComparator tc = new TermComparator();

	// P1
	Term c1 = new Term(0, 1);
	Term c2 = new Term(1, -1);
	Term c3 = new Term(2, 5);
	Term c4 = new Term(3, 2);
	Term c5 = new Term(4, -3);
	Term c6 = new Term(5, 1);
	Term c7 = new Term(6, 1);
	TreeSet<Term> listOfPol1 = new TreeSet<>(tc);

	// P2
	Term c8 = new Term(0, -5);
	Term c9 = new Term(1, -2);
	Term c10 = new Term(2, 1);
	Term c11 = new Term(3, 1);
	TreeSet<Term> listOfPol2 = new TreeSet<>(tc);

	// Addition result
	Term c12 = new Term(0, -4);
	Term c13 = new Term(1, -3);
	Term c14 = new Term(2, 6);
	Term c15 = new Term(3, 3);
	Term c16 = new Term(4, -3);
	Term c17 = new Term(5, 1);
	Term c18 = new Term(6, 1);
	TreeSet<Term> additionListPol = new TreeSet<>(tc);

	// Subtraction result
	Term c19 = new Term(0, 6);
	Term c20 = new Term(1, 1);
	Term c21 = new Term(2, 4);
	Term c22 = new Term(3, 1);
	Term c23 = new Term(4, -3);
	Term c24 = new Term(5, 1);
	Term c25 = new Term(6, 1);
	TreeSet<Term> subtractionListPol = new TreeSet<>(tc);

	// M<ultiplication result
	Term c26 = new Term(0, -5);
	Term c27 = new Term(1, 3);
	Term c28 = new Term(2, -22);
	Term c29 = new Term(3, -20);
	Term c30 = new Term(4, 15);
	Term c31 = new Term(5, 8);
	Term c32 = new Term(6, -8);
	Term c33 = new Term(7, -4);
	Term c34 = new Term(8, 2);
	Term c35 = new Term(9, 1);
	TreeSet<Term> multiplicationListPol = new TreeSet<>(tc);

	// Division result
	// Quotient
	Term c36 = new Term(0, -8);
	Term c37 = new Term(1, -1);
	// Coefficient c38 = new Coefficient(2, 0);
	Term c39 = new Term(3, 1);
	TreeSet<Term> quotientListPol = new TreeSet<>(tc);
	// Remainder
	Term c40 = new Term(0, 41);
	Term c41 = new Term(1, 10);
	Term c42 = new Term(2, -5);
	TreeSet<Term> remainderListPol = new TreeSet<>(tc);

	// Scalar Multiplication
	Term c43 = new Term(0, 6);
	Term c44 = new Term(1, -6);
	Term c45 = new Term(2, 30);
	Term c46 = new Term(3, 12);
	Term c47 = new Term(4, -18);
	Term c48 = new Term(5, 6);
	Term c49 = new Term(6, 6);
	TreeSet<Term> scalarListPol = new TreeSet<>(tc);

	// Derivate
	Term c50 = new Term(0, -1);
	Term c51 = new Term(1, 10);
	Term c52 = new Term(2, 6);
	Term c53 = new Term(3, -12);
	Term c54 = new Term(4, 5);
	Term c55 = new Term(5, 6);
	TreeSet<Term> derivateListPol = new TreeSet<>(tc);

	// Integrate
	double a = 1 / (double) 7;
	double b = 1 / (double) 6;
	double c = -3 / (double) 5;
	double d = 1 / (double) 2;
	double e = 5 / (double) 3;
	double f = -1 / (double) 2;
	double g = 1;

	Term c56 = new Term(1, g);
	Term c57 = new Term(2, f);
	Term c58 = new Term(3, e);
	Term c59 = new Term(4, d);
	Term c60 = new Term(5, c);
	Term c61 = new Term(6, b);
	Term c62 = new Term(7, a);
	TreeSet<Term> integrateListPol = new TreeSet<>(tc);

	Term c63 = new Term(0, 6);
	Term c64 = new Term(1, -6);
	Term c65 = new Term(2, 30);
	Term c66 = new Term(3, 12);
	Term c67 = new Term(4, -18);
	Term c68 = new Term(5, 6);
	Term c69 = new Term(6, 6);
	TreeSet<Term> scalarMultiplicationListPol = new TreeSet<>(tc);

	private static final double evaluateResult = 51151;
	private static final int scalar = 6;

	Polynomial p1;
	Polynomial p2;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	PolynomialOperations po = new PolynomialOperations();

	@Before
	public void initialization() {
		additionListPol.add(c12);
		additionListPol.add(c13);
		additionListPol.add(c14);
		additionListPol.add(c15);
		additionListPol.add(c16);
		additionListPol.add(c17);
		additionListPol.add(c18);

		subtractionListPol.add(c19);
		subtractionListPol.add(c20);
		subtractionListPol.add(c21);
		subtractionListPol.add(c22);
		subtractionListPol.add(c23);
		subtractionListPol.add(c24);
		subtractionListPol.add(c25);

		multiplicationListPol.add(c26);
		multiplicationListPol.add(c27);
		multiplicationListPol.add(c28);
		multiplicationListPol.add(c29);
		multiplicationListPol.add(c30);
		multiplicationListPol.add(c31);
		multiplicationListPol.add(c32);
		multiplicationListPol.add(c33);
		multiplicationListPol.add(c34);
		multiplicationListPol.add(c35);

		quotientListPol.add(c36);
		quotientListPol.add(c37);
		quotientListPol.add(c39);

		remainderListPol.add(c40);
		remainderListPol.add(c41);
		remainderListPol.add(c42);

		scalarListPol.add(c43);
		scalarListPol.add(c43);
		scalarListPol.add(c44);
		scalarListPol.add(c45);
		scalarListPol.add(c46);
		scalarListPol.add(c47);
		scalarListPol.add(c48);
		scalarListPol.add(c49);

		derivateListPol.add(c50);
		derivateListPol.add(c51);
		derivateListPol.add(c52);
		derivateListPol.add(c53);
		derivateListPol.add(c54);
		derivateListPol.add(c55);

		integrateListPol.add(c56);
		integrateListPol.add(c57);
		integrateListPol.add(c58);
		integrateListPol.add(c59);
		integrateListPol.add(c60);
		integrateListPol.add(c61);
		integrateListPol.add(c62);

		scalarMultiplicationListPol.add(c63);
		scalarMultiplicationListPol.add(c64);
		scalarMultiplicationListPol.add(c65);
		scalarMultiplicationListPol.add(c66);
		scalarMultiplicationListPol.add(c67);
		scalarMultiplicationListPol.add(c68);
		scalarMultiplicationListPol.add(c69);

		listOfPol1.add(c1);
		listOfPol1.add(c2);
		listOfPol1.add(c3);
		listOfPol1.add(c4);
		listOfPol1.add(c5);
		listOfPol1.add(c6);
		listOfPol1.add(c7);

		listOfPol2.add(c8);
		listOfPol2.add(c9);
		listOfPol2.add(c10);
		listOfPol2.add(c11);

		p1 = new Polynomial(listOfPol1);
		p2 = new Polynomial(listOfPol2);
	}

	@Test
	public void additionTest() {
		assertTrue("Addition", additionListPol.equals(po.addition(p1, p2).getCoefficients()));
	}

	@Test
	public void subtractionTest() {

		assertTrue("Subtraction", subtractionListPol.equals(po.subtraction(p1, p2).getCoefficients()));

	}

	@Test
	public void multiplicationTest() {

		assertTrue("Multiplication", multiplicationListPol.equals(po.multiplication(p1, p2).getCoefficients()));

	}

	@Test
	public void divisionTest() {
		assertTrue("Division", quotientListPol.equals(po.division(p1, p2)[0].getCoefficients()));
		assertTrue("Division", remainderListPol.equals(po.division(p1, p2)[1].getCoefficients()));

	}

	@Test
	public void derivateTest() {

		assertTrue("Derivate", derivateListPol.equals(po.derivate(p1).getCoefficients()));

	}

	@Test
	public void integrateTest() {

		assertTrue("Integrate", integrateListPol.equals(po.integrate(p1).getCoefficients()));

	}

	@Test
	public void scalarMultplicationTest() {

		assertTrue("Scalar Multiplication", scalarListPol.equals(po.scalarMultiplication(p1, scalar).getCoefficients()));

	}

	@Test
	public void evaluateTest() {

		assertTrue("Evaluate", evaluateResult == po.evaluate(p1, scalar));

	}
}
