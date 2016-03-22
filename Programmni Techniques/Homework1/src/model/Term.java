package model;

/**
 * 
 * @author Robert This class is a term like 5x^3 It contains
 */
public class Term {

	private int degree;
	private double value;

	public Term(int degree, double value) {
		this.degree = degree;
		this.value = value;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public double getValue() {
		if (value == (int) value)
			return (int) value;
		else
			return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double evaluate(int value) {
		return this.value * Math.pow(value, degree);
	}

	@Override
	public String toString() {
		String value = "";
		String degree = "";
		String sign = "";

		String normalizedValue = "";

		if (getValue() > 0) {
			sign = "+";
		} else {
			sign = "-";
		}
		if (getValue() == (int) getValue()) {
			normalizedValue = String.valueOf(Math.abs((int) getValue()));
		} else {
			normalizedValue = String.valueOf(Math.abs(getValue()));
		}

		if (getDegree() == 0) {
			value = sign + normalizedValue;
		} else if (Math.abs(getValue()) == 1 && getDegree() != 0) {
			degree = sign + "x^" + getDegree();
		} else if (Math.abs(getValue()) != 1 && getDegree() != 0) {
			value = sign + normalizedValue;
			degree = "x^" + getDegree();
		}

		return value + degree;
	}

	@Override
	public boolean equals(Object arg0) {
		return getDegree() == ((Term) arg0).getDegree() ? getValue() == ((Term) arg0).getValue() ? true : false : false;
	}

}
