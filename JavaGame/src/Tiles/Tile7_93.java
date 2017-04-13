package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;

public class Tile7_93 extends Tile {
	
	public Tile7_93() {
		super(7, 93);
	}
	
	@Override
	public void create()  {		
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
		
		setColumn(new Fence(), 0);
		setColumn(new Fence(), numCols() - 1);
	}
	
}
