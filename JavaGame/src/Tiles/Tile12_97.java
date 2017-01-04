package Tiles;

import gameobjects.Fence;
import gameobjects.Grass;
import gameobjects.VillageFloor;

public class Tile12_97 extends Tile {

	public Tile12_97() {
		super(12, 97);
	}
	
	@Override
	public void create() {
		setArea(new VillageFloor(), 0, 0, numCols() - 2, numRows() - 2);
		
		setRow(new Grass(), numRows() - 1);
		setRow(new Fence(), numRows() - 1);
		
		setColumn(new Grass(), numCols() - 1);
		setColumn(new Fence(), numCols() - 1);
		
		setObject(new Grass(), 0, 0);
		setObject(new Fence(), 0, 0);
	}
}
