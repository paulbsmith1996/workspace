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
	public Connector(int x, int y, Color color) {
		super(x, y, color);
	}
	
	// Constructor with only parent crank specified
	public Connector(Crank parent, Color color) {
		super(	(int)(parent.getX() + parent.getLength() * Math.cos(parent.getTheta0())),
				(int)(parent.getY() + parent.getLength() * Math.sin(parent.getTheta0())),
				color
				);
		
		
		// Connect the connector to its parent crank
		parent.connect(this);
	}
}
