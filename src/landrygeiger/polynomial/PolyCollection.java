package landrygeiger.polynomial;

import java.util.List;

public abstract class PolyCollection extends PolyContainer {
	
	public abstract void add(PolyExpression p);
	public abstract List<PolyExpression> getPolys();
	public abstract boolean isEmpty();
	
}
