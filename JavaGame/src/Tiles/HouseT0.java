package Tiles;

import gameobjects.Blank;
import gameobjects.HouseFloor;
import gameobjects.MVillager;
import gameobjects.MerchantMat;

public class HouseT0 extends HouseTile {

	public HouseT0() {
		super(HouseReference.FIRST_HOUSE);
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
		String[] text = {"Hello. I'm a villager here.",
						 "I would like to thank you for taking care of those goblins to the south.",
						 "They've been terrorizing the village for ages.",
						 "I hate to be a bother but we've been having some trouble in our mine recently.",
						 "The same group of goblins seem to have taken a liking to the ores in there.",
						 "Would you mind helping our little village?"};
		firstEver.setDialogue(text);
		setObject(firstEver, 4, 5);
	}
}
