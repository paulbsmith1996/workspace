package gameobjects;

import resourceloaders.Images;

public class MerchantCenter extends Obstacle{

	public MerchantCenter(int x, int y) {
		super(x, y, Images.merchantCenter);
	}
	
	public MerchantCenter() {
		super(0,0,Images.merchantCenter);
	}
}
