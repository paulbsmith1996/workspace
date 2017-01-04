package gameobjects;

import resourceloaders.Images;



public class Fence extends Obstacle {

	public Fence(int x, int y) {
		super(x, y, Images.fence);
	}
	
	public Fence() {
		super(0,0,Images.fence);
	}
}
