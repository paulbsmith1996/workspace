package com.link;
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
	 *  Normal vector to crank and speed, length, and initial angle of the crank
	 */
	private int speed;
	private double[] norm;
	private int length = 0;
	private double theta0;
	
	/**
	 * Crank constructor using coordinates and normal vector
	 * @param x - x coordinate of crank
	 * @param y - y coordinate of crank
	 * @param z - z coordinate of crank
	 * @param norm - noraml vector to crank
	 * @param speed - crank speed
	 * @param color - color of crank trail
	 */
	public Crank(int x, int y, int z, double[] norm, int speed, Color color) {
		super(x, y, z, color);
		this.norm = norm;
		this.speed = speed;
	}
	
	/**
	 * Crank constructor using length, initial angle, and normal vector 
	 * @param length - crank length 
	 * @param theta0 - crank's initial angle
	 * @param norm - normal vector to crank
	 * @param speed - crank speed
	 * @param color - color of crank trail
	 */
	public Crank(int length, double theta0, double[] norm, int speed, Color color) {
		// Build LComponent in center of applet window
		super(300, 300, 0, color);
		
		this.length = length;
		this.theta0 = theta0;
		this.norm = norm;
		this.speed = speed;
		
	}
	
	public Crank(Crank parent, int length, double theta0, double[] norm, 
			int speed, Color color) {
		
		// Build LComponent at the correct distance away from parent. Original
		// position will be fixed by first call to move() method as long as
		// the distance beteen parent and child is correct
		super(	parent.getX() + parent.getLength(),
				parent.getY(), 
				parent.getZ(), 
				color);
		
		this.length = length;
		this.theta0 = theta0;
		this.norm = norm;
		this.speed = speed;
		
		// Connect this crank to its parent
		parent.connect(this);
		
	}
	
	// Return length of crank
	public int getLength() { return this.length; }
	
	// Return initial angle of crank
	public double getTheta0() { return this.theta0; }
	
	// Moves the crank's children according to its own information for
	// speed and length
	public void move() {
		
		// Loop through all child components' information (stored in LCompAssoc objects)
		for(LCompAssoc compAssoc: getConnected()) {
			
			LComponent currComp = compAssoc.getComp();
			
			// Get correct distance between components
			double distance = compAssoc.getDistance();
			
			
			
			/************ Calculate norm and unit vector in direction of norm **********/
			
			
			// Calculate length of normal vector
			double normLength = Math.sqrt(norm[0]*norm[0] 
					+ norm[1]*norm[1] 
					+ norm[2]*norm[2]);

			// Create new 3d vector for unit vector 
			double[] normUnit = new double[3];

			// Calculate unit vector in direction of normal vector
			for(int i = 0; i < 3; i++) {
				normUnit[i] = (double)norm[i] / normLength;
			}
			
			
						
			/** Calculate planar vector and unit vector in direction of planar vector **/
			
			
			// Get the planar vector stored in the LCompAssoc object
			double[] planar = compAssoc.getPlanar();
			
			
			// Calculate length of planar vector
			double planarLength = 0;
			
			for(int i = 0; i < 3; i++) {
				planarLength += planar[i] * planar[i];
			}
			
			planarLength = Math.sqrt(planarLength);
			
			// Create new 3d vector for unit vector in direction of planar vector
			double[] planarUnit = new double[3];
			
			// Calculate unit vector in direction of planar vector
			for(int i = 0; i < 3; i++) {
				if(planarLength != 0) {
					planarUnit[i] = planar[i] / planarLength;
				}
			}
			
			
			
			/***** Calculate cross product of planar and norm vector and unit vector in  
			 that direction *****/
			
			
			// Create new 3d vector for cross product of planar and normal vector
			double[] crossProd = new double[3];
			
			// Calculate cross product of the normal and planar unit vectors.
			// This means the cross product vector will have length 1
			// and be orthogonal to both planar and normal unit vectors.
			crossProd[0] = normUnit[1]*planarUnit[2] - normUnit[2]*planarUnit[1];
			crossProd[1] = -normUnit[0]*planarUnit[2] + normUnit[2]*planarUnit[0];
			crossProd[2] = normUnit[0]*planarUnit[1] - normUnit[1]*planarUnit[0];
			
			// Handle case where the cross product vector is not length 1
			// Calculate length of cross product vector
			double crossProdLen = 0;
			
			for(int i = 0; i < 3; i++) {
				crossProdLen += crossProd[i] * crossProd[i];
			}
			
			crossProdLen = Math.sqrt(crossProdLen);
			
			// Create new 3d vector for unit vector in direction of cross product
			double[] crossUnit = new double[3];
			
			
			// Calculate unit vector in direction of cross product
			for(int i = 0; i < 3; i++) {
				if(crossProdLen != 0) {
					crossUnit[i] = crossProd[i] / crossProdLen;
				}
			}
			
			
			
			/************* Update coordinates and angles appropriately ***************/ 
			
			
			
			// Get correct place in parametrized curve
			double t = compAssoc.getT();
			
			// Update angle of parameterization
			compAssoc.setT(t + speed * 0.01);
			
			// Calculate x, y, and z coordinates, using the equation for the parameterization
			// of a circle in 3 dimensions
			double newX = (double) getX() + distance * (double) Math.cos(t) * planarUnit[0] 
					  + distance * Math.sin(t) * crossProd[0];

			double newY = (double) getY() + distance *(double) Math.cos(t) * planarUnit[1]
					  + distance * Math.sin(t) * crossProd[1];

			double newZ = (double) getZ() + distance * (double) Math.cos(t) * planarUnit[2]
					  + distance * Math.sin(t) * crossProd[2]; 
			
			// Update x, y, and z coordinates
			currComp.setX((int)newX);
			currComp.setY((int)newY);
			currComp.setZ((int)newZ);
			
		}
	}
}
