package landrygeiger.polynomial;

import landrygeiger.algebraic.Fraction;


public class PolyComposite extends PolyContainer {
	
	/*
	 * When a composite polynomial is differentiate using the chain rule, 
	 * it becomes a pair of a composite polynomial and the differentiate inner polynomial
	 * f(x) = g(h(x))
	 * f'(x) = g'(h(x))h'(x)   <- chain rule
	 */
	
	PolyExpression insidePoly;
	
	Fraction coefficient;
	Fraction exponent;
	
	public PolyComposite(PolyExpression polynomial, Fraction coefficient, Fraction exponent) {
		
		this.insidePoly = polynomial;
		this.coefficient = coefficient;
		this.exponent = exponent;
		
		simplifyPolyTypes();
		
	}
	
	public void differentiate() {
		
		coefficient.multiply(exponent); //general power rule -> nax^n-1
		exponent.add(new Fraction(-1, 1)); //^^^^
		
		PolyExpression insidePolyCopy = insidePoly.copy();
		insidePolyCopy.differentiate();
		
		PolyProduct pc;
		if(exponent.isZero()) {
			
			pc = new PolyProduct(insidePolyCopy, coefficient);
			
		} else {
			
			pc = new PolyProduct(copy());
			pc.add(insidePolyCopy);
			
		}
		
		coefficient = new Fraction(1, 1);
		exponent = new Fraction(1, 1);
		
		insidePoly = pc;
		
	}
	
	public void multiplyByConstant(Fraction f) {
		
		coefficient.multiply(f);
		
	}
	
	public PolyComposite copy() {
		
		return new PolyComposite(insidePoly.copy(), coefficient.copy(), exponent.copy());
		
	}
	
	public boolean isTypeSimplifiable() {
		
		if(coefficient.isOne() && exponent.isOne()) {
			return true;
		} 
		
		return false;
		
	}
	
	public void simplifyPolyTypes() {
		
		if(insidePoly instanceof PolyContainer && ((PolyContainer) insidePoly).isTypeSimplifiable()) {
			
			insidePoly = ((PolyContainer) insidePoly).typeSimplified();
			
		} else if(insidePoly instanceof Polynomial && ((Polynomial) insidePoly).getTerms().size() == 1) {
			
			((Polynomial) insidePoly).getTerms().get(0).getVariables().get(0).getExponent().multiply(exponent);
			exponent = new Fraction(1, 1);
			
		}
		
	}
	
	public PolyExpression typeSimplified() {
		
		return insidePoly;
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		if(!coefficient.isOne()) builder.append(coefficient);
		
		builder.append(insidePoly);
		
		if(!exponent.isOne()) builder.append("^" + exponent);
		
		return builder.toString();
		
	}
	
	public Fraction getCoefficient() { return coefficient; }
	public Fraction getExponent() { return exponent; }
	
}
