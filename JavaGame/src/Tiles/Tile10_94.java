package Tiles;

import gameobjects.Blank;
import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;

public class Tile10_94 extends Tile{

	public Tile10_94() {
		super(10, 94);
	}
	
	@Override
	public void create() {
		setArea(new Grass(), 0, 0, numCols() - 1, numRows() - 1);
		setArea(new Dirt(), 4, 2, numCols() - 3, numRows() - 3);
		
		setArea(new Dirt(), 0, 3, 2, 6);
		
		
		setArea(new Fence(), 3, 0, numCols() - 1, 0);
		setArea(new Fence(), 3, numRows() - 1, numCols() - 1, numRows() - 1);
		
		setArea(new Fence(), 0, 2, 2, 2);
		setArea(new Fence(), 2, 0, 2, 2);
		
		setArea(new Fence(), 0, 7, 2, 7);
		setArea(new Fence(), 2, 8, 2, numRows() - 1);
		
		setColumn(new Fence(), numCols() - 1);
	}
}
