package Tiles;

import gameobjects.Dirt;
import gameobjects.StoneWall1;
import gameobjects.StoneWall2;
import gameobjects.StoneWall4;
import gameobjects.StoneWall7;
import gameobjects.VillageFloor;

public class Tile6_90 extends Tile {

	// Left tile to fist city
	public Tile6_90() {
		super(6, 90);
	}
	
	@Override
	public void create() {
		
		// Dirt surrounding city wall
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() -1 );
		
		setArea(new VillageFloor(), 1, 1, numCols() - 1, numRows() - 1);
		
		// Top horizontal stone wall
		setObject(new StoneWall1(), 1, 1);
		setArea(new StoneWall2(), 2, 1, numCols() - 1, 1);
		
		// Vertical stone wall
		setArea(new StoneWall4(), 1, 2, 1, numRows() - 2);
		setObject(new StoneWall7(), 1, numRows() - 1);
		
		// Bottom horizontal stone wall
		setArea(new StoneWall2(), 2, numRows() - 1, numCols() - 1, numRows() - 1);
	
	}
}
