package gameobjects;

import resourceloaders.Images;

public class BushyTreeLR extends Obstacle {

	public BushyTreeLR(int x, int y) {
		super(x, y, Images.bushyTreeLR);
	}
	
	public BushyTreeLR() {
		super(0,0,Images.bushyTreeLR);
	}
}