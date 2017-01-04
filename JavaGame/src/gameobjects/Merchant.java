package gameobjects;

import resourceloaders.Images;

public class Merchant extends Obstacle {

	public Merchant(int x, int y) {
		super(x, y, Images.merchant);
	}
	
	public Merchant() {
		super(0,0,Images.merchant);
	}
}
