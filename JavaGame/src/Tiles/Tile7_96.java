package Tiles;

import gameobjects.Dirt;

public class Tile7_96 extends Tile {

	public Tile7_96() {
		super(7, 96);
	}
	
	@Override
	public void create()  {		
		setArea(new Dirt(), 1, 1, numCols() - 1, numRows() - 1);
	}
}
