package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;

public class Tile12_94 extends Tile {

	public Tile12_94() {
		super(12, 94);
	}
	
	@Override
	public void create() {
		setArea(new Dirt(), 1, 0, numCols() - 2, numRows() - 1);
		
		setColumn(new Grass(), 0);
		setColumn(new Fence(), 0);
		
		setColumn(new Grass(), numCols() - 1);
		setColumn(new Fence(), numCols() - 1);
	}
}
