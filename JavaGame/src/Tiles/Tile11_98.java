package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;
import gameobjects.VillageFloor;

public class Tile11_98 extends Tile {

	public Tile11_98() {
		super(11,98);
	}
	
	@Override
	public void create() {
		
		setColumn(new Grass(), 0);
		setColumn(new Grass(), numCols() - 1);
		
		setArea(new Dirt(), 1, 0, numCols() - 2, numRows() - 1);
		
		setArea(new Grass(), 1, numRows() - 4, 4, numRows() - 1);
		setArea(new Grass(), numCols() - 5, numRows() - 4, numCols() - 1, numRows() - 1);
		
		setColumn(new Fence(), 0);
		setColumn(new Fence(), numCols() - 1);
		
		for(int x = 2; x < numCols() - 2; x++) {
			setObject(new VillageFloor(), x, 0);
		}
		
		for(int x = 1; x < 4; x += 2) {
			addBushyTree(x, numRows() - 4);
			addBushyTree(x, numRows() - 2);
		}
		
		for(int x = numCols() - 5; x < numCols() - 2; x += 2) {
			addBushyTree(x, numRows() - 4);
			addBushyTree(x, numRows() - 2);
		}
		
		addMob(new Goblin(150, 150, 4, -1, 2));
		
		
	}
}
