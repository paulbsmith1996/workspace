package gameobjects;

import resourceloaders.Images;

public class CounterLeft extends Obstacle {

	public CounterLeft(int x, int y) {
		super(x, y, Images.counterLeft);
	}
	
	public CounterLeft() {
		super(0,0,Images.counterLeft);
	}
}
