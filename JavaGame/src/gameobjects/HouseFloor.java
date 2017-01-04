package gameobjects;

import resourceloaders.Images;

public class HouseFloor extends GameObject {

	public HouseFloor(int x, int y) {
		super(x, y, Images.houseFloor);
	}
	
	public HouseFloor() {
		super(0,0,Images.houseFloor);
	}
}
