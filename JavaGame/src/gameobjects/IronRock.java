package gameobjects;

import resourceloaders.Images;
import Items.IronOre;

public class IronRock extends RawOre {

	public IronRock(int x, int y) {
		super(x, y, new IronOre(), Images.ironRock);
	}
	
	public IronRock() {
		super(0, 0, new IronOre(), Images.ironRock);
	}
}
