package Tiles;

import gameobjects.Blank;
import gameobjects.Dirt;
import gameobjects.IronRock;
import gameobjects.MineFloor;

public class Tile9_96 extends Tile {

	public Tile9_96() {
		super(9, 96);
	}
	
	@Override
	public void create() {
		setArea(new MineFloor(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Blank(), numRows() - 1);
		setRow(new Blank(), 0);
		
		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);
		setArea(new MineFloor(), numCols() - 1, 3, numCols() - 1, numRows() - 4);
		
		setObject(new Dirt(), numCols() / 2, numRows() - 1);
		
		setObject(new IronRock(), numCols() / 2, numRows() / 2);
		
		setObject(new IronRock(), numCols() / 2 - 3, numRows() / 2 - 2);
		setObject(new IronRock(), numCols() / 2 + 3, numRows() / 2 - 2);
	}
}
