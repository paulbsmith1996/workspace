package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.StoneTablet;

public class BattleTileDirt extends BattleTile {

	public BattleTileDirt() {
		super(0);
	}
	
	@Override
	public void create() {
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
	}
}
