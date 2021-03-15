package landrygeiger.polynomial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import landrygeiger.algebraic.Fraction;

public class PolyGroup extends PolyCollection {
	
	ArrayList<PolyExpression> polys;
	
	public PolyGroup(List<PolyExpression> list) {
		
		this.polys = new ArrayList<PolyExpression>(list); 
		//simplify();
		
	}
	
	public PolyGroup() {
		this(new ArrayList<PolyExpression>());
	}
	
	public void add(PolyExpression poly) {
		
		polys.add(poly);
		
	}

	public void differentiate() {
		
		polys.forEach(poly -> poly.differentiate());
		Iterator<PolyExpression> it = polys.iterator();
		while(it.hasNext()) {
			PolyExpression poly = it.next();
			if(poly instanceof PolyCollection) {
				if(((PolyCollection) poly).isEmpty()) it.remove();
			}
		}
		
	}
	
	public void multiplyByConstant(Fraction f) {
		
		polys.forEach(poly -> poly.multiplyByConstant(f));
		
	}
	
	public void simplify() {
		
		simplifyPolyTypes();
		
		if(isAllType(Polynomial.class)) {
			
			PolyExpression combined = (Polynomial) polys.get(0);
			for(int i = 1; i < polys.size(); i++) {
				((Polynomial) combined).add((Polynomial) polys.get(i));
			}
			
			polys = (ArrayList<PolyExpression>)Arrays.asList(combined);
			
		}
		
	}
	
	public boolean isAllType(Class<?> polyClass) {
		
		for(PolyExpression poly : polys) {
			
			if(poly.getClass() != polyClass) return false;
			
		}
		
		return true;
		
	}
	
	public void simplifyPolyTypes() {
		
		for(int i = 0; i < polys.size(); i++) {
			
			PolyExpression poly = polys.get(i);
			
			if(poly instanceof PolyContainer && ((PolyContainer) poly).isTypeSimplifiable()) {
				
				PolyExpression simplifiedPoly = ((PolyContainer) poly).typeSimplified();
				polys.set(i, simplifiedPoly);
				
			}
		}
	}
	
	public PolyExpression typeSimplified() {
		
		return polys.get(0);
		
	}
	
	public boolean isTypeSimplifiable() {
		
		if(polys.size() == 1) return true;
		
		return false;
		
	}

	public PolyGroup copy() {
		
		List<PolyExpression> newPolys = new ArrayList<PolyExpression>();
		polys.forEach(poly -> newPolys.add(poly.copy()));
		
		return new PolyGroup(newPolys);
		
	}
	
	public boolean isEmpty() {
		return polys.size() == 0;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < polys.size(); i++) {
			
			builder.append(polys.get(i));
			
			if(i != polys.size()-1) {
				
				builder.append(" + ");
				
			}
			
		}
		
		if(polys.size() != 1) {
			builder.insert(0, "(");
			builder.append(")");
		}
		
		return builder.toString();
		
	}
	
	public List<PolyExpression> getPolys() { return polys; }
	
}
