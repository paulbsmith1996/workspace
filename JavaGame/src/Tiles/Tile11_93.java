package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;
import gameobjects.Wisp;

public class Tile11_93 extends Tile {

	public Tile11_93() {
		super(11, 93);
	}
	
	@Override
	public void create()  {
		
		setArea(new Dirt(), 0, 3, numCols() - 1, numRows() - 3);
		
		setArea(new Grass(), 0, 0, numCols() - 1, 2);
		setArea(new Grass(), 0, numRows() - 2, numCols() - 1, numRows() - 1);
		
		setArea(new Grass(), 0, numRows() - 4, 3, numRows() - 2);
		setArea(new Grass(), numCols() - 5, 1, numCols() - 1, 4);
		
		setRow(new Fence(), 0);
		setObject(new Fence(), numCols() - 1, numRows() - 1);
		setObject(new Fence(), numCols() - 1, 1);
		
		for(int x = 0; x + 1 < numCols(); x += 2) {
			addBushyTree(x, numRows() - 2);
			addBushyTree(x, 1);
			
			if(x < 4) {
				addBushyTree(x, numRows() - 4);
			}
			
			if(x > numCols() - 6) {
				addBushyTree(x, 3);
			}
		}
		
		addMob(new Goblin(30, 150, 3, 2, 4));
		addMob(new Wisp(200, 200, -3, 1, 3));
		
	}
}
