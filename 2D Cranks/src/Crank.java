import java.awt.Color;

/**
 * 
 * @author paulbairdsmith
 * A crank is defined by a speed, length, and initial angle. A crank can have exactly one 
 * parent crank, and its movement takes this into account. It can also have exactly one 
 * child crank or connector (if this is the last crank of the system).
 *
 */
public class Crank extends LComponent{
	
	/**
	 *  Speed, length, and initial angle of the crank
	 */
	private int speed;
	private int length = 0;
	private double theta0 = 0;
	
	
	/**
	 * Constructor for the fixed crank of the system, centered at coordinates 300, 300
	 * @param length - crank length
	 * @param theta0 - crank's initial angle 
	 * @param speed - crank speed
	 * @param color - crank trail color
	 */
	public Crank(int length, double theta0, int speed, Color color) {
		// Build LComponent in center of applet window
		super(300, 300, color);
		
		this.speed = speed;
		this.length = length;	
		this.theta0 = theta0;
		
	}
	
	/**
	 * Constructor for crank given a parent crank
	 * @param parent - parent crank of this object
	 * @param length - crank length
	 * @param theta0 - crank's initial angle
	 * @param speed - crank speed
	 * @param color - crank trail color
	 */
	public Crank(Crank parent, int length, double theta0, int speed, Color color) {
		
		// Build LComponent at trailing end of parent crank
		super(	(int)(parent.getX() + parent.getLength() * Math.cos(parent.getTheta0())),
				(int)(parent.getY() + parent.getLength() * Math.sin(parent.getTheta0())),
				color
				);
		
		this.speed = speed;
		this.length = length;	
		this.theta0 = theta0;
		
		// Connect this crank to its parent
		parent.connect(this);
		
	}
	
	// Return length of crank
	public int getLength() { return this.length; }
	
	// Return initial angle of crank
	public double getTheta0() { return theta0; }
	
	// Moves the crank's children according to its own information for
	// speed and length
	public void move() {
		for(LCompAssoc compAssoc: getConnected()) {
			LComponent currComp = compAssoc.getComp();
			
			// Get correct distance
			double distance = compAssoc.getDistance();
			
			// Get correct place in parametrized curve
			double t = compAssoc.getT();
			
			// Update t value
			compAssoc.setT(t + speed * 0.01);
			
			// Reassign coordinates appropriately
			double newX = (double)getX() + (double)distance * Math.cos(t);
			double newY = (double)getY() + (double)distance * Math.sin(t);
			
			currComp.setX((int)newX);
			currComp.setY((int)newY);
			
		}
	}
}
