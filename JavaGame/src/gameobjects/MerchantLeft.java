package gameobjects;

import resourceloaders.Images;

public class MerchantLeft extends Obstacle{

	public MerchantLeft(int x, int y) {
		super(x, y, Images.merchantLeft);
	}
	
	public MerchantLeft() {
		super(0,0,Images.merchantLeft);
	}
}
