package gameobjects;

import resourceloaders.Images;



public class StoneWall8 extends Obstacle {

	public StoneWall8(int x, int y) {
		super(x, y, Images.stoneWall8);
	}
	
	public StoneWall8() {
		super(0,0,Images.stoneWall8);
	}
}
