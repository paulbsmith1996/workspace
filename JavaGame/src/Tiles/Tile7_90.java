package Tiles;

import gameobjects.Dirt;
import gameobjects.StoneWall1;
import gameobjects.StoneWall2;
import gameobjects.StoneWall3;
import gameobjects.StoneWall4;
import gameobjects.StoneWall7;
import gameobjects.StoneWall9;
import gameobjects.VillageFloor;
import gameobjects.WizardDoor;

public class Tile7_90 extends Tile{

	// Middle Tile to first city
	public Tile7_90() {
		super(7, 90);
	}
	
	@Override
	public void create()  {		
		setArea(new VillageFloor(), 0, 0, numCols() - 1, numRows() - 1);
		
		setObject(new StoneWall3(), 0, numRows() - 1);		
		setObject(new StoneWall1(), numCols() - 1, numRows() - 1);
		
		setObject(new StoneWall7(), numCols() - 2, 1);
		setObject(new StoneWall9(), 1, 1);
		
		setObject(new StoneWall4(), numCols() - 2, 0);
		setObject(new StoneWall4(), 1, 0);
		
		setObject(new StoneWall2(), numCols() - 1, 1);
		setObject(new StoneWall2(), 0, 1);
		
		setObject(new Dirt(), 0, 0);
		setObject(new Dirt(), numCols() - 1, 0);
		
		setObject(new WizardDoor(), 5, 5);
		
	}
}
