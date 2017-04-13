package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;
import gameobjects.StoneWall2;
import gameobjects.StoneWall4;
import gameobjects.StoneWall6;
import gameobjects.StoneWall7;
import gameobjects.StoneWall9;
import gameobjects.VillageFloor;

public class Tile7_91 extends Tile{

	public Tile7_91() {
		super(7, 91);
	}
	
	@Override
	public void create()  {		
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
		setArea(new VillageFloor(), 0, 0, numCols() - 1, 4);
		setArea(new Grass(), 1, 5, numCols() - 2, 6);
		
		setColumn(new Fence(), 0);
		setColumn(new Fence(), numCols() - 1);
		
		setArea(new StoneWall4(), 0, 0, 0, 3);
		setArea(new StoneWall6(), numCols() - 1, 0, numCols() - 1, 3);
		
		setArea(new StoneWall2(), 1, 4, 3, 4);
		setArea(new StoneWall2(), 9, 4, 11, 4);
		
		setObject(new StoneWall7(), 0, 4);
		setObject(new StoneWall9(), numCols() - 1, 4);
		
	}
}
