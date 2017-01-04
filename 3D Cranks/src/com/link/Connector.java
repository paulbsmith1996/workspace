package com.link;
import java.awt.Color;

/**
 * Class for the last trailing end of a crank, which will not move itself.
 * Its coordinates can be determined by only using information from the parent crank.
 * A connector should always have a parent crank
 * @author paulbairdsmith
 *
 */
public class Connector extends LComponent{
	
	// Constructor with specific coordinates provided
	public Connector(int x, int y, int z, Color color) {
		super(x, y, z, color);
	}
	
	// Constructor with only parent crank specified
	public Connector(Crank parent, Color color) {
		super(	parent.getX() + parent.getLength(), 
				parent.getY(), 
				parent.getZ(),
				color
				);
		
		// Connect the connector to its parent crank
		parent.connect(this);
	}
}
