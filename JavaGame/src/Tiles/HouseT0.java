package Tiles;

import gameobjects.Blank;
import gameobjects.HouseFloor;
import gameobjects.MVillager;
import gameobjects.MerchantMat;

public class HouseT0 extends HouseTile {

	public HouseT0() {
		super(0);
	}
	
	@Override
	public void create() {
		setArea(new HouseFloor(), 1,1, numCols() - 1, numRows() - 1);
		
		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);
		
		setRow(new Blank(), numRows() - 1);
		setRow(new Blank(), 0);
		
		setObject(new MerchantMat(), numCols() / 2, numRows() - 1);
		MVillager firstEver = new MVillager();
		String[] text = {"Hi there. I'm a villager here.",
				"I would like to thank you for taking", "care of those goblins to the south.",
				"They've been terrorizing the village", "for ages."};
		firstEver.setDialogue(text);
		setObject(firstEver, 4, 5);
	}
}
