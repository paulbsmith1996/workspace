package Tiles;

import gameobjects.Blank;
import gameobjects.Dirt;
import gameobjects.IronRock;
import gameobjects.MineFloor;

public class Tile4_97 extends Tile {

	public Tile4_97() {
		super(4, 97);
	}
	
	@Override
	public void create() {
		setArea(new MineFloor(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Blank(), numRows() - 1);
		setRow(new Blank(), 0);
		
		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);
		//setArea(new MineFloor(), numCols() - 1, 3, numCols() - 1, numRows() - 4);
		
		
		//setArea(new MineFloor(), numCols() - 1, 3, numCols() - 1, numRows() - 4);
		setArea(new MineFloor(), 3, 0, 7, 0);
		setArea(new MineFloor(), 3, numRows() - 1, 7, numRows() - 1);		
		setArea(new MineFloor(), 0, 3, 0, numRows() - 4);
	}
}
