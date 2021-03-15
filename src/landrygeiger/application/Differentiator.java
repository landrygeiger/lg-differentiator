package landrygeiger.application;

import java.util.Arrays;

import landrygeiger.algebraic.*;
import landrygeiger.polynomial.PolyComposite;
import landrygeiger.polynomial.PolyExpression;
import landrygeiger.polynomial.PolyFraction;
import landrygeiger.polynomial.PolyProduct;
import landrygeiger.polynomial.Polynomial;

public class Differentiator {
	
	public static void main(String args[]) {
		final long startTime = System.nanoTime();
		
		//testFractions();
		//testTerms();
		//testPolynomials();
		
		//unitTest();
		
		differentiate();
		
		final long elapsedTime = System.nanoTime() - startTime;
		System.out.println("Completed in " + ((double) elapsedTime)/1000000 + "ms");
	}
	
	public static void unitTest() {
		Polynomial testPolyExpr = new Polynomial("6x^3 + -9x + 4");
		testPolyExpr.differentiate();
		System.out.println(testPolyExpr);
		
		Polynomial testPolyExpr2 = new Polynomial("6x^-3/2 + 1/8x^-4 + -1/3x^-10");
		testPolyExpr2.differentiate();
		System.out.println(testPolyExpr2);
		
		PolyProduct testPolyProd = new PolyProduct(Arrays.asList(new Polynomial("x + -4"),
																	new Polynomial("x^2+2x")));
		testPolyProd.differentiate();
		System.out.println(testPolyProd);
		
		PolyFraction testPolyFrac = new PolyFraction(new Polynomial("x^5 + -5x^3 + 2x"), new Polynomial("x^3"));
		testPolyFrac.differentiate();
		System.out.println(testPolyFrac);
	}
	
	public static void example1() {
		
		Polynomial numerator = new Polynomial("x + 2");
		Polynomial denominator = new Polynomial("x^3 + 4");
		PolyFraction fraction = new PolyFraction(numerator, denominator);
		PolyComposite poly = new PolyComposite(fraction, new Fraction(1, 1), new Fraction(4, 1));
		poly.differentiate();
		System.out.println(poly);
		
	}
	
	public static void differentiate() {
		
		Polynomial inner = new Polynomial("x^2 + 2x + 1");
		PolyComposite outer = new PolyComposite(inner, new Fraction(1, 1), new Fraction(3, 1));
		
		outer.differentiate();
		System.out.println(outer);
		
	}
	
	public static void testFractions() {
		Fraction f1 = new Fraction(4, 1);
		Fraction f2 = new Fraction(-1, 1);
		//f1.add(f2);
		f1.add(f2);
		System.out.println(f1);
	}
	
	public static void testTerms() {
		Term t1 = new Term("2x");
		Term t2 = new Term("1");
		System.out.println(t2);
		t1.multiply(t2);
		
	}
	
	public static void testPolynomials() {
		Polynomial p1 = new Polynomial("2x^-1");
		Polynomial p2 = new Polynomial("4x");
		p1.multiply(p2);
		System.out.println(p1);
	}
	
}
