package gameobjects;

import resourceloaders.Images;

public class VillageFloor extends GameObject {

	public VillageFloor(int x, int y) {
		super(x, y, Images.villageFloor);
	}
	
	public VillageFloor() {
		super(0,0,Images.villageFloor);
	}
}
