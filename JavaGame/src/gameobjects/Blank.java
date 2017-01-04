package gameobjects;

import resourceloaders.Images;

/**
 * Class represents a background block that is black and cannot
 * be walked through
 * 
 * @author Paul
 *
 */
public class Blank extends Obstacle {

	public Blank(int x, int y) {
		super(x, y, Images.blank);
	}
	
	public Blank() {
		super(0,0,Images.blank);
	}
}
