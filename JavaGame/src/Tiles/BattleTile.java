package Tiles;

import misc.Game;

public class BattleTile extends Tile {

	private int battleID;
	
	public BattleTile(int battleID) {
		super(-1, battleID);
		this.battleID = battleID;
	}
	
	public BattleTile(int battleID, Game game) {
		super(-1, battleID, game);
		this.battleID = battleID;
	}
}
