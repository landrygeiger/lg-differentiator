package landrygeiger.algebraic;
import java.util.Comparator;

public class Variable {
	
	public static Comparator<Variable> comparator = new Comparator<Variable>() {
		
		public int compare(Variable v1, Variable v2) {
			
			return v1.getName().compareTo(v2.getName());
			
		}
		
	};
	
	String name;
	Fraction exponent;
	
	public Variable(String name, Fraction exponent) {
		
		this.name = name;
		this.exponent = exponent;
		
	}
	
	public void multiply(Variable v) {
		
		exponent.add(v.getExponent());
		
	}
	
	public static boolean multipliable(Variable v1, Variable v2) {
		
		if(v1.getName().equals(v2.getName())) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean equals(Variable v) {
		
		if(name.equals(v.getName()) && exponent.equals(v.getExponent())) {
			
			return true;
			
		} else { return false; }
		
	}
	
	@Override
	public String toString() {
		
		if(exponent.getNumerator() == 1 && exponent.getDenominator() == 1) {
			
			return name;
			
		}
		
		return name + "^" + exponent;
		
	}
	
	public String getName() { return name; }
	public Fraction getExponent() { return exponent; }
	
}
