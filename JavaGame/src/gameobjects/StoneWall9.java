package gameobjects;

import resourceloaders.Images;



public class StoneWall9 extends Obstacle {

	public StoneWall9(int x, int y) {
		super(x, y, Images.stoneWall9);
	}
	
	public StoneWall9() {
		super(0,0,Images.stoneWall9);
	}
}
