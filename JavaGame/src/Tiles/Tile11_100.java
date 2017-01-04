package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;

public class Tile11_100 extends Tile{

	public Tile11_100() {
		super(11,100);
	}
	
	@Override
	public void create() {
		setArea(new Dirt(), 0, 4, numCols() - 5, numRows() - 5);		
		
		addMob(new Goblin(200,150,-2,2,1));
		addMob(new Goblin(140,10,-3,-2,1));
		
		setArea(new Grass(), 0, numRows() - 4, numCols() - 1, numRows() - 1);
		setArea(new Grass(), 0, 0, 3, 3);
		setArea(new Grass(), numCols() - 5, 0, numCols() - 1, numRows() - 5);
		
		for(int x = 0; x + 1 < numCols(); x += 2) {
			addBushyTree(x, 0);
			addBushyTree(x, 2);
			addBushyTree(x, numRows() - 4);
			addBushyTree(x, numRows() - 2);
		}
		
		setArea(new Dirt(), 4, 0, 7, 3);
		setColumn(new Fence(), numCols() - 1);
		
		
	}
}
