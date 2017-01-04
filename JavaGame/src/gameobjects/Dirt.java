package gameobjects;

import resourceloaders.Images;

public class Dirt extends GameObject {
	
	public Dirt(int x, int y) {
		super(x,y, Images.dirt);
	}
	
	public Dirt() {
		super(0,0,Images.dirt);
	}
	
}
