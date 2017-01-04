package gameobjects;

import resourceloaders.Images;

public class CounterRight extends Obstacle {

	public CounterRight(int x, int y) {
		super(x, y, Images.counterRight);
	}
	
	public CounterRight() {
		super(0,0,Images.counterRight);
	}
}