package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.StoneTablet;

public class Tile9_99 extends Tile {

	public Tile9_99() {
		super(9, 99);
	}
	
	@Override
	public void create() {
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
		
		setColumn(new Fence(), 0);
		setColumn(new Fence(), numCols() - 1);
		
		setRow(new Fence(), 0);
		
		addBushyTree(numCols() - 4, numRows() - 5);
		addBushyTree(2, numRows() - 5);
		
		StoneTablet introTab = new StoneTablet();
		String[] text = {"You have been left here to serve out your exile.",
				"Should you choose to wander the realm, we shall not stop you",
				"but know that return to our kingdom will be punished by death.",
				"Sorry it has come to this"};
		introTab.setDialogue(text);
		
		setObject(introTab, numCols() / 2, 1);
	}
}
