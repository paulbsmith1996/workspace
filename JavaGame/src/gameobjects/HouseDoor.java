package gameobjects;

import resourceloaders.Images;

public class HouseDoor extends Obstacle {

	public HouseDoor(int x, int y) {
		super(x, y, Images.houseDoor);
	}
	
	public HouseDoor() {
		super(0,0,Images.houseDoor);
	}
}
