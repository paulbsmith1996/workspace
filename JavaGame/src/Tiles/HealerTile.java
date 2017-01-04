package Tiles;

import gameobjects.Floor;
import gameobjects.Healer;
import gameobjects.MerchantMat;
import gameobjects.Wall;

public class HealerTile extends HouseTile {

	public HealerTile() {
		super(1);
	}
	
	@Override
	public void create() {
		setArea(new Floor(), 1, 0, numCols() - 2, numRows() - 1);
		
		setColumn(new Wall(), 0);
		setColumn(new Wall(), numCols() - 1);
		
		setRow(new Wall(), numRows() - 1);
		setRow(new Wall(), 0);
		
		setObject(new MerchantMat(), numCols() / 2, numRows() - 1);
		
		setObject(new Healer(), numCols() / 2, 1);
	}
}
