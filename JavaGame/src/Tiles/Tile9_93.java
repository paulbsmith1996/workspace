package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;
import gameobjects.Wisp;

public class Tile9_93 extends Tile {

	public Tile9_93() {
		super(9, 93);
	}
	
	@Override
	public void create()  {
		setArea(new Grass(), 0, 0, numCols() - 1, numRows() - 1);
		
		setArea(new Dirt(), 5, 3, numCols() - 1, 5);
		setArea(new Dirt(), 5, 6, 7, numRows() - 1);
		
		setRow(new Fence(), 0);
		setColumn(new Fence(), 0);
		
		for(int y = 1; y < 3; y++) {
			setObject(new Fence(), numCols() - 1, y);
		}
		
		for(int y = numRows() - 4; y < numRows(); y++) {
			setObject(new Fence(), numCols() - 4, y);
		}
		
		for(int x = numCols() - 3; x < numCols(); x++) {
			setObject(new Fence(), x, numRows() - 4);
		}
		
		addMob(new Goblin(300, 40, -4, 2, 5));
		addMob(new Goblin(50, 320, 3, 3, 5));
		addMob(new Wisp(50, 150, 3, -1, 5));
		
	}
}
