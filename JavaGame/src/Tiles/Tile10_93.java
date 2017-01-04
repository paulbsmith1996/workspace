package Tiles;

import gameobjects.Bush;
import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;
import gameobjects.Wisp;

public class Tile10_93 extends Tile {

	public Tile10_93() {
		super(10, 93);
	}
	
	@Override
	public void create()  {
		setArea(new Grass(), 0, 0, numCols() - 1, 2);
		
		setArea(new Dirt(), 0, 3, numCols() - 1, 5);
		setArea(new Dirt(), 3, 6, 9, 9);
		
		setArea(new Grass(), 0, 6, 2, 8);
		setArea(new Bush(), 0, 7, 2, 8);
		
		setArea(new Grass(), numCols() - 3, numRows() - 5, numCols() - 1, numRows() - 3);
		setArea(new Bush(), numCols() - 3, numRows() - 4, numCols() - 1, numRows() - 3);
		
		setArea(new Bush(), 0, 1, numCols() - 1, 2);
		
		setArea(new Grass(), 0, numRows() - 2, numCols() - 1, numRows() - 1);
		setRow(new Bush(), numRows() - 2);
		
		setRow(new Fence(), 0);
		setRow(new Fence(), numRows() - 1);
		
		addMob(new Wisp(150, 150, 2, -4, 4));
		addMob(new Wisp(250, 150, -3, 2, 5));
		
		
	}
}
