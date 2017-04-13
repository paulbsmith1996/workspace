package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;

public class Tile7_92 extends Tile {
	
	public Tile7_92() {
		super(7, 92);
	}
	
	@Override
	public void create()  {		
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
		
		setColumn(new Fence(), 0);
		setColumn(new Fence(), numCols() - 1);
	}
	
}
