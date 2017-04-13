package Tiles;

import gameobjects.Dirt;
import gameobjects.Falkon;
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
		
		addMob(new Falkon(250, 300, 4, 2, 6));
		addMob(new Falkon(200, 300, -3, 3, 5));
		addMob(new Falkon(150, 300, -5, -1, 6));
	}
}
