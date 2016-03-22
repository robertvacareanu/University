package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class Polynomial {

	static private TermComparator tc = new TermComparator();
	private TreeSet<Term> coefficients;

	public Polynomial(Collection<Term> coefficients) {
		this.coefficients = new TreeSet<>(tc);
		this.coefficients.addAll(coefficients);
		normalize();
	}

	public TreeSet<Term> getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(TreeSet<Term> coefficients) {
		this.coefficients = coefficients;
	}

	public void addCoefficient(Term coefficient) {
		coefficients.add(coefficient);
	}

	private void normalize() {
		Iterator<Term> it = coefficients.iterator();
		while (it.hasNext()) {
			Term c = it.next();
			if (c.getValue() == 0) {
				it.remove();
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		normalize();

		for (Term c : coefficients) {
			sb.append(c);
		}
		if (sb.length() > 0 && sb.charAt(0) == '+') {
			sb.replace(0, 1, "");
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		return this.coefficients.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof Polynomial) {
			if (this.hashCode() == ((Polynomial) obj).hashCode()) {
				if (this.coefficients.equals(((Polynomial) obj).getCoefficients())) {
					return true;
				}
			}
		}
		return false;
	}

}
