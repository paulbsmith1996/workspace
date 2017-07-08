package Tiles;

import gameobjects.Blank;
import gameobjects.Dirt;
import gameobjects.IronRock;
import gameobjects.MineFloor;

public class Tile10_96 extends Tile {

	public Tile10_96() {
		super(10, 96);
	}
	
	@Override
	public void create() {
		setArea(new MineFloor(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Blank(), numRows() - 1);
		setRow(new Blank(), 0);
		
		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);
		
		setObject(new Dirt(), numCols() / 2, numRows() - 1);
		
		setObject(new IronRock(), numCols() / 2, numRows() / 2);
		
		setObject(new IronRock(), numCols() / 2 - 3, numRows() / 2 - 2);
		setObject(new IronRock(), numCols() / 2 + 3, numRows() / 2 - 2);
		
		setArea(new MineFloor(), 0, 3, 0, numRows() - 4);
	}
}
