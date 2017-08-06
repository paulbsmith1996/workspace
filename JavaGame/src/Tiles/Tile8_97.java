package Tiles;

import gameobjects.Blank;
import gameobjects.Dirt;
import gameobjects.IronRock;
import gameobjects.MineFloor;

public class Tile8_97 extends Tile {

	public Tile8_97() {
		super(8, 97);
	}
	
	@Override
	public void create() {
		setArea(new MineFloor(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Blank(), numRows() - 1);
		setRow(new Blank(), 0);
		
		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);
		setArea(new MineFloor(), numCols() - 1, 3, numCols() - 1, numRows() - 4);		
		
		setObject(new Blank(), 1, 1);
		setObject(new Blank(), 1, numRows() - 2);		
		setObject(new Blank(), numCols() - 2, 1);
		setObject(new Blank(), numCols() - 2, numRows() - 2);
		
		setArea(new MineFloor(), numCols() - 1, 3, numCols() - 1, numRows() - 4);	
		setArea(new MineFloor(), 0, 3, 0, numRows() - 4);
	}
}
