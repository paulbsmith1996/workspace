package gameobjects;

import resourceloaders.Images;



public class StoneWall1 extends Obstacle {

	public StoneWall1(int x, int y) {
		super(x, y, Images.stoneWall1);
	}
	
	public StoneWall1() {
		super(0,0,Images.stoneWall1);
	}
}
