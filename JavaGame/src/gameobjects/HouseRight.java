package gameobjects;

import resourceloaders.Images;



public class HouseRight extends Obstacle {

	public HouseRight(int x, int y) {
		super(x, y, Images.houseRight);
	}
	
	public HouseRight() {
		super(0,0,Images.houseRight);
	}
}
