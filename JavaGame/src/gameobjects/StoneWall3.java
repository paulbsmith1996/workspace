package gameobjects;

import resourceloaders.Images;



public class StoneWall3 extends Obstacle {

	public StoneWall3(int x, int y) {
		super(x, y, Images.stoneWall3);
	}
	
	public StoneWall3() {
		super(0,0,Images.stoneWall3);
	}
}
