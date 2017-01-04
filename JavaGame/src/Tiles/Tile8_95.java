package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;

public class Tile8_95 extends Tile {

	public Tile8_95() {
		super(8, 95);
	}
	
	@Override
	public void create()  {
		setColumn(new Grass(), numCols() - 1);
		setColumn(new Grass(), 0);
		
		setArea(new Dirt(), 1, 0, numCols() - 2, numRows() - 1);
		
		
		setColumn(new Fence(), 0);
		setColumn(new Fence(), numCols() - 1);
	}
}
