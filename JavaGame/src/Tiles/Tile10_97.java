package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;
import gameobjects.MVillager;
import gameobjects.MineEntrance;

public class Tile10_97 extends Tile {

	public Tile10_97() {
		super(10,97);
	}
	
	@Override
	public void create() {
		setArea(new Grass(), 1, 1, numCols() - 1, numRows() - 2);
		setArea(new Dirt(), 2, 2, numCols() - 2, numRows() - 3);
		setRow(new Fence(), 0);
		setRow(new Fence(), numRows() - 1);
		setColumn(new Fence(), 0);
		
		setObject(new MineEntrance(), numCols() / 2, 0);
		
		MVillager gardenVillager = new MVillager();
		
		String[] text = {"Welcome to LargePebble Village!","Our mine is little but believe me,",
				"we're an industrious bunch.","I hope you'll love this place as much as we do."};
		gardenVillager.setDialogue(text);
		
		setObject(gardenVillager, 3, 5);
	}
}
