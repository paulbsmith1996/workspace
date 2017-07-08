package gameobjects;

import resourceloaders.Images;



public class StoneWall2L extends Obstacle {

	public StoneWall2L(int x, int y) {
		super(x, y, Images.stoneWall2L);
	}
	
	public StoneWall2L() {
		super(0,0,Images.stoneWall2L);
	}
}
