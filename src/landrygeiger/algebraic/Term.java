package landrygeiger.algebraic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import landrygeiger.polynomial.Polynomial;

public class Term {
	
	Fraction coefficient;
	
	//each variable is paired with its exponent
	List<Variable> variables = new ArrayList<Variable>();
	
	//create a term from a string
	public Term(String stringTerm) {
		
		 //split up term by variables
		String[] termData = stringTerm.split("(?=[a-z])");
		
		String varsString = stringTerm.replaceAll("[^a-z]", ""); //strip out all numbers and just leave the variables
		List<String> vars = Arrays.asList(varsString.split("(?=[a-z])")); //split up each variable in the term string and add it to the vars list
		if(vars.get(0).equals("")) vars = new ArrayList<String>();
		
		//if the term is just a constant
		if(vars.size() == 0) {
			
			//if coeff is a fraction
			if(stringTerm.contains("/")) {
				
				String[] fraction = stringTerm.split("/");
				coefficient = new Fraction(Integer.parseInt(fraction[0]), Integer.parseInt(fraction[1]));
			
			//not a fraction
			} else {
				
				try {
					coefficient = new Fraction(Integer.parseInt(stringTerm), 1);
				} catch (NumberFormatException e) {
					coefficient = new Fraction(0, 1);
				}
				
			}
			
			return;
			
		} else {
		
			char firstChar = termData[0].toCharArray()[0];
			
			if(Character.isDigit(firstChar)
					|| (firstChar == '-' && termData[0].length() > 1)) { //if the first character is a number, the data at index 0 is the term's coefficient
				
				if(termData[0].contains("/")) { //if the coefficient is a fraction
					
					String[] fraction = termData[0].split("/");
					coefficient = new Fraction(Integer.parseInt(fraction[0]),
												Integer.parseInt(fraction[1]));
					
				} else { coefficient = new Fraction(Integer.parseInt(termData[0]), 1); }
			
			//if the first character of the first element of termData is a letter, there is no coefficient (meaning it's 1)
			} else if(Character.isLetter(termData[0].toCharArray()[0])) { coefficient = new Fraction(1, 1);
			//if the the string is "-" - the coefficient is -1
			} else if(termData[0].equals("-")) { coefficient = new Fraction(-1, 1);
			//basically this covers if the coefficient is negative and |n| > 1
			} else { coefficient = new Fraction(Integer.parseInt(termData[0]), 1); }
		
		}
		
		//determine exponents and put variables in hashmap
		for(int i = 0; i < termData.length; i++) { //start at index 1 and skip the first data value (the coefficient)
			
			if(i == 0 && !coefficient.isOne()) continue; //skip the first data value in termData if it's a coefficient
			
			Fraction exponent;
			
			String expData = termData[i].replaceAll("[a-z\\^]", ""); //strip away ^ and just leave the exponent value
			if(expData.equals("")) { //if there is no exponent
				
				exponent = new Fraction(1, 1);
				
			} else if(expData.contains("/")) { //if exponent is a fraction
				
				String[] fraction = expData.split("/");
				exponent = new Fraction(Integer.parseInt(fraction[0]),
										Integer.parseInt(fraction[1]));

			} else { exponent = new Fraction(Integer.parseInt(expData), 1); }
			
			variables.add(new Variable(termData[i].replaceAll("[^a-z]", ""), exponent));
			
		}
		
		sortVariables();
		
		//display();
		
	}
	
	public Term(Fraction f) {
		this(f.toString());
	}

	public void add(Term t) {
		
		if(addable(this, t)) coefficient.add(t.getCoefficient());

	}
	
	public void multiply(Term t) {
		
		coefficient.multiply(t.getCoefficient());
		
		for(Variable v : t.getVariables()) {
			
			if(contains(v)) { 
				
				Variable thisV = getVariableByName(v.getName());
				thisV.multiply(v);
				if(thisV.getExponent().isZero()) variables.remove(thisV);
				
			} else {
				
				variables.add(v);
				
			}
			
		}
		
		sortVariables();
		
	}
	
	//differentiate by power rule
	public void differentiate() {
		
		if(variables.size() == 1) {
			
			coefficient.multiply(variables.get(0).getExponent());
			variables.get(0).getExponent().add(new Fraction(-1, 1));
			if(variables.get(0).getExponent().getNumerator() == 0) variables.remove(0);
			
		} else if(isConstant()) {
			
			coefficient.multiply(new Fraction(0, 1));
			
		}
		
	}
	
	public static boolean addable(Term t1, Term t2) {
		
		if(t1.getVariables().size() == t2.getVariables().size()) {
			
			Iterator<Variable> it1 = t1.variables.iterator();
			Iterator<Variable> it2 = t2.getVariables().iterator();
			
			while(it1.hasNext() && it2.hasNext()) {
				
				if(!it1.next().equals(it2.next())) return false;
				
			}
			
			return true;
			
		}
		
		return false;

	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		if(coefficient.getNumerator() == -1 && coefficient.getDenominator() == 1) builder.append("-");
		else if(!(coefficient.getNumerator() == 1 && coefficient.getDenominator() == 1 && !isConstant())) builder.append(coefficient);
		
		variables.forEach(var -> builder.append(var));
		
		return builder.toString();
		
	}
	
	public Polynomial toPolynomial() {
		
		return new Polynomial(this);
		
	}
	
	//Display term in table format (debugging)
	public void display() {
		
		System.out.println("Coefficient: " + coefficient);
		System.out.println("Variables: ");
		variables.forEach(var -> System.out.println(var.getName() + ", ^" + var.getExponent()));
		System.out.println("-------");
		
	}
	
	public boolean isConstant() {
		
		if(variables.size() == 0) return true;
		
		return false;
		
	}
	
	public boolean isZero() {
		
		return (coefficient.getNumerator() == 0);
		
	}
	
	public boolean contains(Variable vo) {
		
		for(Variable v : variables) {
			
			if(vo.getName().equals(v.getName())) return true;
			
		}
		
		return false;
		
	}
	
	public void sortVariables() {
		
		Collections.sort(variables, Variable.comparator);
		
	}
	
	public Variable getVariableByName(String name) {
		
		for(Variable v : variables) {
			
			if(v.getName().equals(name)) return v;
			
		}
		
		return null;
		
	}
	
	public List<Variable> getVariables() { return variables; }
	public Fraction getCoefficient() { return coefficient; }
	
}
