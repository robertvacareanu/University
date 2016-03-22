package controller;

import java.util.Iterator;
import java.util.TreeSet;

import model.Term;
import model.TermComparator;
import model.Polynomial;

/**
 * 
 * @author Robert This class is responsible with all the operations between polynomials The operands are not changed The resulted polynomial is a new polynomial
 */
public class PolynomialOperations {

	private TermComparator tc = new TermComparator();

	/**
	 * 
	 * @param tree
	 * @return A new that doesn't contain any elements with 0 value. It deletes things like: 0x^5
	 */
	private TreeSet<Term> normalize(TreeSet<Term> tree) {
		TreeSet<Term> coefficients = copyTree(tree);
		Iterator<Term> it = coefficients.iterator();
		while (it.hasNext()) {
			Term c = it.next();
			if (c.getValue() == 0) {
				it.remove();
			}
		}
		return coefficients;
	}

	/**
	 * 
	 * @param tree-
	 *            coefficients of a polynomial
	 * @return an exact copy of the input
	 */
	private TreeSet<Term> copyTree(TreeSet<Term> tree) {
		TreeSet<Term> treeSet = new TreeSet<>(tc);

		for (Term c : tree) {
			treeSet.add(new Term(c.getDegree(), c.getValue()));
		}

		return treeSet;
	}

	/**
	 * 
	 * @param tree1,
	 *            the coefficients of a polynomial
	 * @param tree2,
	 *            the coefficients of a polynomial
	 * @return a new tree that has the sum of the coefficients from the input trees
	 */
	private TreeSet<Term> addCoefficients(TreeSet<Term> tree1, TreeSet<Term> tree2) {

		TreeSet<Term> c1;
		TreeSet<Term> c2;

		if (tree1.size() > tree2.size()) {
			c1 = copyTree(tree1);
			c2 = copyTree(tree2);
		} else {
			c1 = copyTree(tree2);
			c2 = copyTree(tree1);
		}

		int i = 0;
		for (Iterator<Term> pol1 = c1.iterator(); pol1.hasNext() && i < c1.size();) {
			Term coeff1 = pol1.next();
			for (Iterator<Term> pol2 = c2.iterator(); pol2.hasNext();) {
				Term coeff2 = pol2.next();
				if (coeff1.getDegree() == coeff2.getDegree()) {
					coeff1.setValue(coeff1.getValue() + coeff2.getValue());
					pol2.remove();
				}
			}
			i++;
		}
		c1.addAll(c2);

		return normalize(c1);
	}

	/**
	 * 
	 * @param tree1,
	 *            the coefficients of a polynomial
	 * @param tree2,
	 *            the coefficients of a polynomial
	 * @return a new tree that has the coefficients of the multiplication between the input trees
	 */
	private TreeSet<Term> multiplyCoefficients(TreeSet<Term> tree1, TreeSet<Term> tree2) {

		TreeSet<Term> temp = new TreeSet<>(tc);
		TreeSet<Term> result = new TreeSet<>(tc);

		for (Term c1 : tree1) {
			for (Term c2 : tree2) {
				temp.add(new Term(c1.getDegree() + c2.getDegree(), c1.getValue() * c2.getValue()));
			}
			result = addCoefficients(result, temp);
			temp.clear();
		}

		return result;
	}

	/**
	 * 
	 * @param tree,
	 *            the coefficients of a polynomial
	 * @return the tree having the values of the coefficients inverted
	 */
	private TreeSet<Term> reverseSign(TreeSet<Term> tree) {
		for (Term c : tree) {
			c.setValue(c.getValue() * (-1));
		}
		return tree;
	}

	/**
	 * 
	 * @param p1
	 *            a polynomial
	 * @param p2
	 *            a polynomial
	 * @return a new polynomial computed as the sum of the inputs
	 */
	public Polynomial addition(Polynomial p1, Polynomial p2) {
		return new Polynomial(addCoefficients(p1.getCoefficients(), p2.getCoefficients()));
	}

	/**
	 * 
	 * @param p1,
	 *            a polynomial
	 * @param p2,
	 *            a polynomial
	 * @return a new polynomial computed as the difference between the inputs
	 */
	public Polynomial subtraction(Polynomial p1, Polynomial p2) {

		TreeSet<Term> c2 = copyTree(p2.getCoefficients());

		c2 = reverseSign(c2);

		return addition(p1, new Polynomial(c2));
	}

	/**
	 * 
	 * @param p1,
	 *            a polynomial
	 * @param p2,
	 *            a polynomial
	 * @return the polynomial resulted from the multiplication of the input polynomials
	 */
	public Polynomial multiplication(Polynomial p1, Polynomial p2) {
		return new Polynomial(multiplyCoefficients(p1.getCoefficients(), p2.getCoefficients()));
	}

	/**
	 * 
	 * 
	 * @param p1,
	 *            a polynomial
	 * @param p2,
	 *            a polynomial
	 * @return the polynomials, quotient and remainder resulted from the division of the input polynomials
	 */
	public Polynomial[] division(Polynomial p1, Polynomial p2) {

		TreeSet<Term> c1 = copyTree(p1.getCoefficients());
		TreeSet<Term> c2 = copyTree(p2.getCoefficients());
		TreeSet<Term> aux = new TreeSet<>(tc);
		TreeSet<Term> result = new TreeSet<>(tc);

		while (c1.size() > 0 && c1.last().getDegree() >= c2.last().getDegree()) {
			Term c = new Term(c1.last().getDegree() - c2.last().getDegree(), c1.last().getValue() / c2.last().getValue());
			result.add(c);
			aux.add(c);

			c1 = addCoefficients(c1, reverseSign(multiplyCoefficients(aux, c2)));
			aux.clear();

		}
		Polynomial[] r = new Polynomial[2];
		r[0] = new Polynomial(result);
		r[1] = subtraction(p1, multiplication(r[0], p2));

		return r;
	}

	/**
	 * 
	 * @param p1,
	 *            a polynomial
	 * @return a new polynomial resulted from differentiating the input
	 */
	public Polynomial derivate(Polynomial p1) {
		TreeSet<Term> coefficients = new TreeSet<>(tc);
		for (Term c : p1.getCoefficients()) {
			coefficients.add(new Term(c.getDegree() - 1, c.getValue() * c.getDegree()));
		}
		return new Polynomial(coefficients);
	}

	/**
	 * 
	 * @param p1,
	 *            a polynomial
	 * @return a new polynomial resulted from integrating the input
	 */
	public Polynomial integrate(Polynomial p1) {
		TreeSet<Term> coefficients = new TreeSet<>(tc);
		for (Term c : p1.getCoefficients()) {
			coefficients.add(new Term(c.getDegree() + 1, c.getValue() / (c.getDegree() + 1)));
		}
		return new Polynomial(coefficients);

	}

	/**
	 * 
	 * @param p1,
	 *            a polynomial
	 * @param value,
	 *            the number to be evaluated in the input polynomial
	 * @return a number resulted from evaluating the polynomial p1 with the value
	 */
	public double evaluate(Polynomial p1, int value) {

		double result = 0;
		for (Term c : p1.getCoefficients()) {
			result += c.getValue() * Math.pow(value, c.getDegree());
		}

		return result;
	}

	/**
	 * 
	 * @param p1,
	 *            a polynomial
	 * @param scalar,
	 *            the number to be multiplied with the coefficients of p1
	 * @return a polynomial resulted from multiplying every coefficient of p1 with scalar
	 */
	public Polynomial scalarMultiplication(Polynomial p1, int scalar) {
		TreeSet<Term> c1 = copyTree(p1.getCoefficients());

		for (Term c : c1) {
			c.setValue(c.getValue() * scalar);
		}

		return new Polynomial(c1);
	}

}
