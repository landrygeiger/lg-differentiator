package landrygeiger.polynomial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import landrygeiger.algebraic.Fraction;

public class PolyProduct extends PolyCollection {
	
	List<PolyExpression> polys;
	Fraction coefficient;
	
	public PolyProduct(ArrayList<PolyExpression> list, Fraction coefficient) {
		
		this.polys = list;
		this.coefficient = coefficient;
		
		for(PolyExpression poly : list) {
			
			if(poly instanceof PolyComposite) {
				
				coefficient.multiply(((PolyComposite) poly).getCoefficient());
				((PolyComposite) poly).getCoefficient().setToOne();
			}
		}
		
		simplify();
		
	}
	
	public PolyProduct(List<PolyExpression> list) {
		this(new ArrayList<PolyExpression>(list), new Fraction(1, 1));
	}
	
	public PolyProduct(PolyExpression poly, Fraction coefficient) {
		this(new ArrayList<PolyExpression>(Arrays.asList(poly)), coefficient);
	}
	
	public PolyProduct(PolyExpression poly) {
		this(new ArrayList<PolyExpression>(Arrays.asList(poly)), new Fraction(1, 1));
	}
	
	public PolyProduct() {
		this(new ArrayList<PolyExpression>(), new Fraction(1, 1));
	}
	
	public void differentiate() {
		
		List<PolyExpression> derivedPolys = copy().getPolys();
		derivedPolys.forEach(poly -> poly.differentiate());
		
		PolyGroup polyGroup = new PolyGroup();
		
		for(int i = 0; i < polys.size(); i++) {
			
			PolyProduct polyProduct = new PolyProduct();
			
			for(int j = 0; j < polys.size(); j++) {
				
				if(i == j) polyProduct.add(derivedPolys.get(j).copy());
				else polyProduct.add(polys.get(j).copy());
				
			}
			
			polyGroup.add(polyProduct);
			
		}
		
		polys = Arrays.asList(polyGroup);
		
	}
	
	public void simplify() {
		
		simplifyPolyTypes();
		
		boolean allPolys = true;
		for(PolyExpression poly : polys) {
			
			if(!(poly instanceof Polynomial)) allPolys = false;
			
		}
		
		if(allPolys) {
			
			if(polys.size() == 0) return;
			PolyExpression combined = (Polynomial) polys.get(0);
			for(int i = 1; i < polys.size(); i++) {
				
				((Polynomial) combined).multiply((Polynomial) polys.get(i));
				
			}
			polys = new ArrayList<PolyExpression>(Arrays.asList(combined));
			
		}
		
	}
	
	public boolean isTypeSimplifiable() {
		
		if(polys.size() == 1) return true;
		
		return false;
		
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
		
		PolyExpression poly = polys.get(0);
		poly.multiplyByConstant(coefficient);
		return poly;
		
	}
	
	public PolyProduct copy() {
		
		PolyProduct npc = new PolyProduct();
		polys.forEach(poly -> npc.add(poly.copy()));
		return npc;
		
	}
	
	public void multiply(PolyExpression pc) {
		
		polys.add(pc);
		
	}
	
	public void multiplyByConstant(Fraction f) {
		
		coefficient.multiply(f);
		
	}
	
	public void add(PolyExpression poly) {
		
		multiply(poly);
		
	}
	
	public boolean isEmpty() {
		return polys.size() == 0;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		if(coefficient.equals(new Fraction(-1, 1))) builder.append("-");
		else if(!coefficient.isOne()) builder.append(coefficient);
		
		for(PolyExpression poly : polys) {
			
			builder.append(poly.toString());
			
		}
		
		if(polys.size() != 1) {
			builder.insert(0, "(");
			builder.append(")");
		}
		
		return builder.toString();
		
	}
	
	public List<PolyExpression> getPolys() { return polys; }
	
}
