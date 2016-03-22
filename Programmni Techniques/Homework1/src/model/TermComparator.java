package model;

import java.util.Comparator;

public class TermComparator implements Comparator<Term> {

	@Override
	public int compare(Term o1, Term o2) {
		if (o1.getDegree() == o2.getDegree())
			return 0;
		else
			return o1.getDegree() > o2.getDegree() ? 1 : -1;
	}
}
