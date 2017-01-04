package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;

public class Tile8_94 extends Tile {

	public Tile8_94() {
		super(8, 94);
	}
	
	@Override
	public void create()  {
		setRow(new Grass(), 0);
		setColumn(new Grass(), 0);
		
		setArea(new Dirt(), 1, 1, numCols() - 1, numRows() - 1);
		
		
		setRow(new Fence(), 0);
		setColumn(new Fence(), 0);
		setObject(new Fence(), numCols() - 1, numRows() - 1);
	}
}
