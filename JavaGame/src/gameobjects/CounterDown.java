package gameobjects;

import resourceloaders.Images;

public class CounterDown extends Obstacle {

	public CounterDown(int x, int y) {
		super(x, y, Images.counterDown);
	}
	
	public CounterDown() {
		super(0,0,Images.counterDown);
	}
}