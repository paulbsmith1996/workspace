package gameobjects;

import resourceloaders.Images;



public class StoneWall4 extends Obstacle {

	public StoneWall4(int x, int y) {
		super(x, y, Images.stoneWall4);
	}
	
	public StoneWall4() {
		super(0,0,Images.stoneWall4);
	}
}
