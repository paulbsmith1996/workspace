package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;

public class Tile8_96 extends Tile {

	public Tile8_96() {
		super(8, 96);
	}
	
	@Override
	public void create()  {
		setRow(new Grass(), numRows() - 1);
		setColumn(new Grass(), numCols() - 1);
		
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 2);
		
		
		setRow(new Fence(), numRows() - 1);
		setColumn(new Fence(), numCols() - 1);
		setObject(new Fence(), 0, 0);
	}
}
