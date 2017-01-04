package gameobjects;

import resourceloaders.Images;


public class HouseLeft extends Obstacle {

	public HouseLeft(int x, int y) {
		super(x, y, Images.houseLeft);
	}
	
	public HouseLeft() {
		super(0,0,Images.houseLeft);
	}
}
