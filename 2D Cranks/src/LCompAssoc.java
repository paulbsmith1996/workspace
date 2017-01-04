
/**
 * 
 * @author paulbairdsmith
 * Holds information about a connection between two LComponent objects. This includes
 * distances between the components and the amount that has been traveled through the
 * circle parameterized by the parent component. An LCompAssoc is used by the parent
 * component to store information about its children components.  
 */
public class LCompAssoc {

	// Child component of association
	private LComponent c;
	
	// Distance between components. Not set by constructor
	private double distance;
	
	// This is distance traveled along parameterized curve. Not set by constructor
	private double t;
	
	/**
	 * Constructor for an association between two components
	 * @param c - Child component of the association
	 */
	public LCompAssoc(LComponent c) {
		this.c = c;
		this.distance = 0;
		this.t = 0;
	}
	
	// Get/Set distance
	public void setDistance(double d) { this.distance = d; }
	public double getDistance() { return this.distance; }
	
	// Get/Set child component
	public void setComp(LComponent c) { this.c = c; }
	public LComponent getComp() { return this.c; }
	
	// Get/Set distance traveled along parameterization
	public void setT(double t) { this.t = t; }
	public double getT() { return this.t; }
	
}
