package gameobjects;

import resourceloaders.Images;

public class StoneWall2R extends Obstacle {

	public StoneWall2R(int x, int y) {
		super(x, y, Images.stoneWall2R);
	}
	
	public StoneWall2R() {
		super(0,0,Images.stoneWall2R);
	}
}
