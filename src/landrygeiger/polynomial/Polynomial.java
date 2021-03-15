package landrygeiger.polynomial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import landrygeiger.algebraic.Fraction;
import landrygeiger.algebraic.Term;

public class Polynomial extends PolyExpression {
	
	List<Term> terms;
	
	public Polynomial(String polyString) {
		
		terms = new ArrayList<Term>();
		
		polyString = polyString.replaceAll("[ ()]", ""); //strip out whitespace
		String[] termStrings = polyString.split("\\+");
		
		for(String termString : termStrings) {
			
			Term term = new Term(termString);
			terms.add(term);
			
		}
		
	}
	
	public Polynomial(Term t) {
		
		terms = Arrays.asList(t);
		
	}
	
	//create an empty polynomial
	public Polynomial() {
		
		terms = new ArrayList<Term>();
		
	}
	
	public void add(Polynomial p) {
		
		for(Term t : p.getTerms()) {
			
			Term thisT = getEquivalentTerm(t);
			if(thisT != null) {
				
				thisT.add(t);
				if(thisT.isZero()) terms.remove(thisT);
				
			} else {
				
				terms.add(t);
				
			}
			
		}
			
	}
	
	public void multiplyByConstant(Fraction f) {
		
		multiply(new Term(f));
		
	}
	
	public void multiply(Term t) {
	
		for(Term thisPolyTerm : terms) {
			
			thisPolyTerm.multiply(t);
			
		}
		
	}
	
	public void multiply(Polynomial p) {
			
		Polynomial newPolynomial = new Polynomial();
		
		for(Term t : terms) {
			
			Polynomial copyPolynomial = new Polynomial(p.toString());
			//System.out.print(p + " * " + t + " = ");
			copyPolynomial.multiply(t);
			//System.out.print(t + "\n");
			newPolynomial.add(copyPolynomial);
			
		}
		
		setTerms(newPolynomial.getTerms());
		
	}
	
	public void differentiate() {
		
		Iterator<Term> it = terms.iterator();
		while(it.hasNext()) {
			
			Term nt = it.next();
			nt.differentiate();
			if(nt.isZero()) it.remove();
			
		}
		
	}
	
	public Term getEquivalentTerm(Term to) {
		
		for(Term t : terms) {
			
			if(Term.addable(t, to)) return t; //terms are addable: same terms, different coefficients
			
		}
		
		return null;
		
	}
	
	public Polynomial copy() {
		
		return new Polynomial(this.toString());
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("(");
		
		for(int i = 0; i < terms.size(); i++) {
			
			builder.append(terms.get(i));
			
			if(i != terms.size()-1) {
				
				builder.append(" + ");
				
			}
			
		}
		
		builder.append(")");
		
		return builder.toString();
		
	}
	
	public PolyComposite toPolyComposite() {
		
		return new PolyComposite(this, new Fraction(1, 1), new Fraction(1, 1));
		
	}
	
	public List<Term> getTerms() { return terms; }
	
	public void setTerms(List<Term> terms) { this.terms = terms; }
	
}
