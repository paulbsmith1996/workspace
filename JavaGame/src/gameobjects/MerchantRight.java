package gameobjects;

import resourceloaders.Images;

public class MerchantRight extends Obstacle {

	public MerchantRight(int x, int y) {
		super(x, y, Images.merchantRight);
	}
	
	public MerchantRight() {
		super(0,0,Images.merchantRight);
	}
}
