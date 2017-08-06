package Tiles;

import gameobjects.Blank;
import gameobjects.Dirt;
import gameobjects.Goblin;
import gameobjects.IronRock;
import gameobjects.MineFloor;

public class Tile9_97 extends Tile {

	public Tile9_97() {
		super(9, 97);
	}
	
	@Override
	public void create() {
		setArea(new MineFloor(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Blank(), numRows() - 1);
		setRow(new Blank(), 0);
		
		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);
		
		setObject(new Dirt(), numCols() / 2, numRows() - 1);
		
		setObject(new Blank(), 1, 1);
		setObject(new Blank(), 1, numRows() - 2);		
		
		setArea(new Blank(), 9, 1, numCols() - 2, 1);
		setArea(new Blank(), 9, numRows() - 2, numCols() - 2, numRows() - 2);
		setColumn(new Blank(), numCols() - 2);
		setObject(new Blank(), 10, 2);
		setObject(new Blank(), 10, numRows() - 3);
		
		setArea(new MineFloor(), 3, 0, 7, 0);
		setArea(new MineFloor(), 3, numRows() - 1, 7, numRows() - 1);		
		setArea(new MineFloor(), 0, 3, 0, numRows() - 4);
		
		addMob(new Goblin(300, 100, -3, 2, 12));
		addMob(new Goblin(150, 150, 3, -1, 11));
		addMob(new Goblin(200, 150, -1, 2, 10));
	}
}
