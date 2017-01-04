package gameobjects;

import resourceloaders.Images;



public class Bush extends Obstacle {

	public Bush(int x, int y) {
		super(x, y, Images.bush);
	}
	
	public Bush() {
		super(0,0,Images.bush);
	}
}
