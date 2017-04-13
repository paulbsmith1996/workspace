package gameobjects;

import resourceloaders.Images;



public class StoneWall2 extends Obstacle {

	public StoneWall2(int x, int y) {
		super(x, y, Images.stoneWall2);
	}
	
	public StoneWall2() {
		super(0,0,Images.stoneWall2);
	}
}
