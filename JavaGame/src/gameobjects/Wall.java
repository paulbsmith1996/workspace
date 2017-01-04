package gameobjects;

import resourceloaders.Images;

public class Wall extends Obstacle {

	public Wall(int x, int y) {
		super(x, y, Images.wall);
	}
	
	public Wall() {
		super(0,0,Images.wall);
	}
}
