package landrygeiger.polynomial;

public abstract class PolyContainer extends PolyExpression {
	
	public abstract boolean isTypeSimplifiable();
	public abstract void simplifyPolyTypes();
	public abstract PolyExpression typeSimplified();
	
}
