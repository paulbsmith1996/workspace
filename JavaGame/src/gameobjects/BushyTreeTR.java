package gameobjects;

import resourceloaders.Images;

public class BushyTreeTR extends Obstacle {

	public BushyTreeTR(int x, int y) {
		super(x, y, Images.bushyTreeTR);
	}
	
	public BushyTreeTR() {
		super(0,0,Images.bushyTreeTR);
	}
}