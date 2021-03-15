package landrygeiger.polynomial;

import landrygeiger.algebraic.Fraction;

public abstract class PolyExpression {
	
	public abstract void differentiate();
	public abstract void multiplyByConstant(Fraction f);
	public abstract PolyExpression copy();
	
}
