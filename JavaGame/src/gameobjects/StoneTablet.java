package gameobjects;

import resourceloaders.Images;

public class StoneTablet extends Interactable{

	public StoneTablet(int x, int y) {
		super(x, y, Images.stoneTablet);
	}
	
	public StoneTablet() {
		super(Images.stoneTablet);
	}
}
