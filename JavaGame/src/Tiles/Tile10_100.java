package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;

public class Tile10_100 extends Tile{

	public Tile10_100() {
		super(10,100);
	}
	
	@Override
	public void create() {
		setRow(new Grass(), 0);
		setRow(new Grass(), numRows() - 1);
		setRow(new Grass(), 1);
		setRow(new Grass(), numRows() - 2);
		
		setArea(new Dirt(), 0, 2, numCols() - 1, numRows() - 3);
		
		addMob(new Goblin(200, 200, 3, 1, 1));
		addMob(new Goblin(200, 100, 3, -1, 1));
		
		for(int x = 1; x + 1 < numCols(); x += 2) {
			addBushyTree(x, 0);
			addBushyTree(x, numRows() - 2);
		}
		
		setArea(new Grass(), 3, 2, 4, 3);
		setArea(new Grass(), 3, numRows() - 4, 4, numRows() - 3);
		setArea(new Grass(), numCols() - 2, 2, numCols() - 1, 3);
		setArea(new Grass(), numCols() - 2, numRows() - 4, numCols() - 1, numRows() - 3);
		
		addBushyTree(3, 2);
		addBushyTree(3, numRows() - 4);
		
		addBushyTree(numCols() - 2, 2);
		addBushyTree(numCols() - 2, numRows() - 4);
		
		setObject(new Fence(), 0, 0);
		setObject(new Fence(), 0, numRows() - 1);

	}
}
