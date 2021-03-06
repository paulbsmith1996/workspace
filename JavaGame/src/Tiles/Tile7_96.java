package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;

public class Tile7_96 extends Tile {

	public Tile7_96() {
		super(7, 96);
	}
	
	@Override
	public void create()  {		
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Fence(), numRows() - 1);
		setColumn(new Fence(), 0);
		
		setObject(new Fence(), numCols() - 1, 0);
	}
}
