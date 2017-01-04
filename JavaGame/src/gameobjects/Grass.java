package gameobjects;

import resourceloaders.Images;

public class Grass extends GameObject {

	public Grass(int x, int y) {
		super(x, y, Images.grass);
	}
	
	public Grass() {
		super(0,0,Images.grass);
	}
}
