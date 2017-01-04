package gameobjects;

import resourceloaders.Images;

public class MerchantMat extends GameObject {

	public MerchantMat(int x, int y) {
		super(x, y, Images.merchantMat);
	}
	
	public MerchantMat() {
		super(0,0,Images.merchantMat);
	}
}
