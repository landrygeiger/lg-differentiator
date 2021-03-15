package landrygeiger.polynomial;

import java.util.ArrayList;
import java.util.Arrays;

import landrygeiger.algebraic.Fraction;

public class PolyFraction extends PolyContainer {
	
	PolyExpression numerator;
	PolyExpression denominator;
	
	public PolyFraction(PolyExpression numerator, PolyExpression denominator) {
		
		this.numerator = numerator;
		this.denominator = denominator;
		
		simplifyPolyTypes();
		
	}
	
	public void differentiate() {
		
		PolyExpression numeratorD = numerator.copy();
		numeratorD.differentiate();
		
		
		PolyExpression denominatorD = denominator.copy();
		denominatorD.differentiate();
		
		PolyProduct p1 = new PolyProduct(Arrays.asList(denominator.copy(), numeratorD));
		PolyProduct p2 = new PolyProduct(new ArrayList<PolyExpression>(Arrays.asList(denominatorD, numerator.copy())), new Fraction(-1, 1));
		
		numerator = new PolyGroup(Arrays.asList(p1, p2));
		denominator = new PolyComposite(denominator, new Fraction(1, 1), new Fraction(2, 1));
		
	}
	
	public void multiplyByConstant(Fraction f) {
		
		numerator.multiplyByConstant(f);
		
	}
	
	public PolyExpression copy() {
		
		return new PolyFraction(numerator.copy(), denominator.copy());
		
	}
	
	//TODO: update
	public boolean isTypeSimplifiable() {
		
		return false;
		
	}
	
	public void simplifyPolyTypes() {
		
		if(numerator instanceof PolyContainer && ((PolyContainer) numerator).isTypeSimplifiable()) {
			
			numerator = ((PolyContainer) numerator).typeSimplified();
			
		}
		
	}
	
	public PolyExpression typeSimplified() {
		
		return numerator;
		
	}
	
	@Override
	public String toString() {
		
		return "[" + numerator + "/" + denominator + "]";
		
	}
	
	public PolyExpression getNumerator() { return numerator; }
	public PolyExpression getDenominator() { return denominator; }
	
}
