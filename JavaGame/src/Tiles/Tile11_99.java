package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;

public class Tile11_99 extends Tile{

	public Tile11_99() {
		super(11,99);
	}
	
	@Override
	public void create() {
		
		setArea(new Dirt(), 1, 0, numCols() - 2, numRows() - 1);
		
		setArea(new Grass(), 2, 0, 4, 1);
		setArea(new Grass(), numCols() - 5, 0, numCols() - 2, 1);
		setArea(new Grass(), 2, numRows() - 2, 4, numRows() - 1);
		setArea(new Grass(), numCols() - 5, numRows() - 2, numCols() - 2, numRows() - 1);
		
		setArea(new Grass(), 0, 0, 1, numRows() - 1);
		setArea(new Grass(), numCols() - 2, 0, numCols() - 1, numRows() - 1);
		
		setColumn(new Fence(), numCols() - 1);
		setColumn(new Fence(), 0);
		
		setObject(new Fence(), 3, numRows() - 2);
		setObject(new Fence(), 3, numRows() - 1);
		setObject(new Fence(), numCols() - 4, numRows() - 2);
		setObject(new Fence(), numCols() - 4, numRows() - 1);
		
		addBushyTree(1, 0);
		addBushyTree(3, 0);
		
		addBushyTree(1, numRows() - 2);
		//addBushyTree(3, numRows() - 2);
		
		addBushyTree(numCols() - 5, 0);
		addBushyTree(numCols() - 3, 0);
		
		//addBushyTree(numCols() - 5, numRows() - 2);
		addBushyTree(numCols() - 3, numRows() - 2);
		
		addMob(new Goblin(75, 200, -3, 3, 1));
		addMob(new Goblin(40, 100, 2, -3, 2));
	}
}

