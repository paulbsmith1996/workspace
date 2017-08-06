package Tiles;

import gameobjects.MineFloor;

public class BattleTileMine extends BattleTile {

	public BattleTileMine() {
		super(1);
	}
	
	@Override
	public void create() {
		setArea(new MineFloor(), 0, 0, numCols() - 1, numRows() - 1);
	}
}
