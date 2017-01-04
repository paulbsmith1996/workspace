package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;
import gameobjects.VillageFloor;

public class Tile12_96 extends Tile {

	public Tile12_96() {
		super(12, 96);
	}
	
	@Override
	public void create() {
		setColumn(new Grass(), 0);
		setColumn(new Fence(), 0);
		
		setColumn(new Grass(), numCols() - 1);
		setColumn(new Fence(), numCols() - 1);
		
		setArea(new Dirt(), 1, 0, numCols() - 2, numRows() - 1);
		
		setArea(new VillageFloor(), numCols() / 2 - 1, 0, numCols() / 2 + 1, numRows() - 1);
		
		setObject(new VillageFloor(), numCols() / 2 - 2, numRows() - 1);
		setObject(new VillageFloor(), numCols() / 2 - 3, numRows() - 1);
		setObject(new VillageFloor(), numCols() / 2 - 2, numRows() - 2);
		
		setObject(new VillageFloor(), numCols() / 2 + 2, numRows() - 1);
		setObject(new VillageFloor(), numCols() / 2 + 3, numRows() - 1);
		setObject(new VillageFloor(), numCols() / 2 + 2, numRows() - 2);
	}
}
