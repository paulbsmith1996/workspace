package gameobjects;

import resourceloaders.Images;

public class MineEntrance extends GameObject {

	public MineEntrance(int x, int y) {
		super(x, y, Images.mineEntrance);
	}
	
	public MineEntrance() {
		super(0,0,Images.mineEntrance);
	}
}
