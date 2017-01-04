package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;

public class Tile9_94 extends Tile {

	public Tile9_94() {
		super(9, 94);
	}
	
	@Override
	public void create()  {
		setArea(new Dirt(), 0, 0, numCols() - 1, 6);
		setArea(new Grass(), 0, 7, numCols() - 1, numRows() - 1);
		
		setArea(new Grass(), numCols() - 4, 0, numCols() - 1, 3);
		setArea(new Grass(), 0, 0, 4, 3);
		
		setArea(new Fence(), numCols() - 4, 0, numCols() - 4, 3);
		setArea(new Fence(), numCols() - 3, 3, numCols() - 1, 3);
		
		
		
		setRow(new Fence(), 7);
		setObject(new Fence(), 0, 0);
		
		addMob(new Goblin(300, 150, 3, 3, 6));
	}
}
