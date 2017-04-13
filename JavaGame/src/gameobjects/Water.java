package gameobjects;

import resourceloaders.Images;

public class Water extends Obstacle {

	public Water(int x, int y) {
		super(x, y, Images.water);
	}
	
	public Water() {
		super(0,0,Images.water);
	}
}