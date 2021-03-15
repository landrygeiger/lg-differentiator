package landrygeiger.algebraic;

public class Fraction {
	
	private long numerator;
	private long denominator;
	
	public Fraction(long numerator, long denominator) {
		
		this.numerator = numerator;
		this.denominator = denominator;
		
	}
	
	public void add(Fraction f) {
		
		if(hasCommonDenom(this, f)) numerator += f.getNumerator();
		else {
			
			numerator *= f.getDenominator();
			numerator += f.getNumerator() * denominator;
			denominator *= f.getDenominator();
			
		}
		
		simplify();
		
	}
	
	public void multiply(Fraction f) {
		
		numerator *= f.getNumerator();
		denominator *= f.getDenominator();
		
		simplify();
		
	}
	
	public void simplify() {
		
		long gcd = gcdByEuclidsAlgorithm(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;
		
	}
	
	//Find  GCD by utilizing Euclid's Algorithm (https://www.baeldung.com/java-greatest-common-divisor)
	public static long gcdByEuclidsAlgorithm(long n1, long n2) {
		
	    if (n2 == 0) return n1;
	    else return gcdByEuclidsAlgorithm(n2, n1 % n2);
	    
	}
	
	public static boolean hasCommonDenom(Fraction f1, Fraction f2) {
		
		if(f1.getDenominator() == f2.getDenominator()) return true;
		
		return false;
		
	}
	
	public void setToOne() {
		numerator = 1;
		denominator = 1;
	}
	
	public boolean isZero() {
		
		return (numerator == 0);
		
	}
	
	public boolean isOne() {
		
		return (numerator == 1 && denominator == 1);
		
	}
	
	@Override
	public String toString() {
		
		if(denominator == 1) return Long.toString(numerator);
		
		return numerator + "/" + denominator;
		
	}
	
	public Fraction copy() {
		
		return new Fraction(Long.valueOf(numerator), Long.valueOf(denominator));
		
	}
	
	public boolean equals(Fraction f) {
		
		return numerator == f.getNumerator() && denominator == f.getDenominator();
		
	}
	
	public long getNumerator() { return numerator; }
	public long getDenominator() { return denominator; }
	
}
