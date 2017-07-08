package Tiles;

import misc.Game;

public class HouseTile extends Tile {

	private int houseID;
	
	public HouseTile(int houseID) {
		super(0, houseID);
		this.houseID = houseID;
	}
	
	public HouseTile(int houseID, Game game) {
		super(0, houseID, game);
		this.houseID = houseID;
	}
}
