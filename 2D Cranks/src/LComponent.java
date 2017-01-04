import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

/**
 * 
 * @author paulbairdsmith
 * A component of a crank system. Originally, could be a slider, connector, or crank
 * but now just crank or connector. Class holds information like position, color, and
 * whether or not the applet should produce a trail to follow its path. An LComponent
 * can also be connected to another LComponent and keeps track of all components it
 * is connected to. If an LComponent connects() to a second one, the latter becomes
 * the CHILD and the former becomes the PARENT.  
 */
public class LComponent {

	// x and y coordinates of the component
	private int x, y;
	
	// Holds all the children of the component
	private Vector<LCompAssoc> connected;
	
	// Instructions for the movement of the LComponent
	// Should always be size 1. If it is not, system will most likely fail
	private Vector<double[]> instructions;
	
	// Decides if component will leave trail
	private boolean follow;
	
	// Color of the trail left by the component
	private Color color;
	
	// The radius of all components in the system
	public final int COMPONENT_SIZE = 10; 
	
	// Decides if component has exactly one location to be in
	// If component needs to be in 2 places at once, functioning = false
	private boolean functioning;
	
	/**
	 * Constructs a component using coordinates and color
	 * @param x - x coordinate of component
	 * @param y - y coordinate of component
	 * @param color - color of trail left by component
	 */
	public LComponent(int x, int y, Color color) {
		
		// Instantiate global variables
		this.x = x;
		this.y = y;
		this.color = color;
		
		this.connected = new Vector<LCompAssoc>();
		this.instructions = new Vector<double[]>();
		this.functioning = true;
		
		// Default is to not leave a trail
		follow = false;
	}
	
	// Get/Set Color of component trail
	public void setColor(Color c) { this.color = c; }
	public Color getColor() { return this.color; }
	
	// Get/Set x coordinate of component
	public void setX(int x) { this.x = x; }
	public int getX() { return this.x; }
	
	// Return the middle of the component's circle with midY()
	public int midX() { return this.x + COMPONENT_SIZE / 2; }
	
	// Get/Set y coordinate of component	
	public void setY(int y) { this.y = y; }
	public int getY() { return this.y; }
	
	// Return the middle of the component's circle with midX()	
	public int midY() { return this.y + COMPONENT_SIZE / 2; }
	
	// Get/Set whether or not component leaves a trail
	public void setFollowed(boolean follow) { this.follow = follow; }
	public boolean getFollowed() { return this.follow; }
	
	// Check/Tell if component is functioning (i.e. needs to be in
	// exactly one place at the time of check)
	public void setFunctioning(boolean b) { this.functioning = b; }
	public boolean isGood() { return this.functioning; }
	
	// Provide a new instruction, remove an old instruction, or check
	// the current instructions for the component
	public void addInst(double[] inst) { instructions.add(inst); }
	public Vector<double[]> getInstructions() { return this.instructions; }
	public void clearInst() { this.instructions = new Vector<double[]>(); }
	
	// Returns the children of the component
	public Vector<LCompAssoc> getConnected() { return this.connected; }
	
	/**
	 *  Connects component to child component c. Creates an LCompAssoc object to 
	 *  store information about the new child component and stores this in the
	 *  connected vector
	 * @param c - the child component to be connected to
	 */
	public void connect(LComponent c) {  
		
		// Find distance between the child and parent components, using
		// Pythagorean Theorem
		int deltaX = getX() - c.getX();
		int deltaY = getY() - c.getY();		
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		
		// Initialize initial angle to 0. This will be changed according to
		// component type
		double t = 0;
		
		if(this instanceof Crank) {
			// If parent component is a crank, check the initial angle provided
			t = ((Crank)this).getTheta0();
		} else {
			// Otherwise, use coordinates of both components to determine
			// original angle
			t = (double)deltaY / (double)deltaX;
		}
		
		// Create new LCompAssoc
		LCompAssoc newConnect = new LCompAssoc(c);
		
		// Update distance and initial angle appropriately
		newConnect.setDistance(distance);
		newConnect.setT(t);
		
		// Add LCompAssoc to the vector of connections, making c a child of
		// this component
		connected.add(newConnect); 
	}
	
	/**
	 * Draws the component in the applet
	 */
	public void draw(Graphics g) {
		
		// Difference in color signifies whether or not the instructions for the
		// motion of the LComponent are a valid set
		if(functioning) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.RED);
		}
		
		g.drawOval(x, y, COMPONENT_SIZE, COMPONENT_SIZE);
		
		g.setColor(Color.BLACK);
		
		// Draws rods connecting the parent to all its children. Rod starts at middle
		// of parent component's circle and ends at middle of child component's circle
		for(LCompAssoc cAssoc: connected) {
			
			LComponent currComp = cAssoc.getComp();
			
			g.drawLine(	midX(), midY(), 
						currComp.midX(), currComp.midY());
		}
	}
}
