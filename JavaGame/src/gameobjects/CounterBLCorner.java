package gameobjects;

import resourceloaders.Images;

public class CounterBLCorner extends Obstacle {

	public CounterBLCorner(int x, int y) {
		super(x, y, Images.counterBLCorner);
	}
	
	public CounterBLCorner() {
		super(0,0,Images.counterBLCorner);
	}
}