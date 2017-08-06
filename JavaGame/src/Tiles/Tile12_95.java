package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.GoblinBoss;
import gameobjects.Grass;
import gameobjects.VillageFloor;

public class Tile12_95 extends Tile {

	public Tile12_95() {
		super(12, 95);
	}
	
	@Override
	public void create() {
		setColumn(new Grass(), 0);
		setColumn(new Fence(), 0);
		
		setColumn(new Grass(), numCols() - 1);
		setColumn(new Fence(), numCols() - 1);
		
		setArea(new Dirt(), 1, 0, numCols() - 2, numRows() - 1);
		
		setArea(new VillageFloor(), numCols() / 2 - 1, 2, numCols() / 2 + 1, numRows() - 1);
	}
}
