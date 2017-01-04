package gameobjects;

import resourceloaders.Images;

public class BushyTreeLL extends Obstacle {

	public BushyTreeLL(int x, int y) {
		super(x, y, Images.bushyTreeLL);
	}
	
	public BushyTreeLL() {
		super(0,0,Images.bushyTreeLL);
	}
}