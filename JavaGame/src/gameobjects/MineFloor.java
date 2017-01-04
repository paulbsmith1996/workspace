package gameobjects;

import resourceloaders.Images;

public class MineFloor extends GameObject {

	public MineFloor(int x, int y) {
		super(x, y, Images.mineFloor);
	}
	
	public MineFloor() {
		super(0,0,Images.mineFloor);
	}
}
