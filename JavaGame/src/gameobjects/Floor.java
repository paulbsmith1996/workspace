package gameobjects;

import resourceloaders.Images;

public class Floor extends GameObject {

	public Floor(int x, int y) {
		super(x, y, Images.floor);
	}
	
	public Floor() {
		super(0,0,Images.floor);
	}
}
