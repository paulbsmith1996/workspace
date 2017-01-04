package gameobjects;

import resourceloaders.Images;

public class CounterBRCorner extends Obstacle {

	public CounterBRCorner(int x, int y) {
		super(x, y, Images.counterBRCorner);
	}
	
	public CounterBRCorner() {
		super(0,0,Images.counterBRCorner);
	}
}