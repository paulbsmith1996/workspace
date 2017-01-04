package Tiles;
import java.util.Vector;

import misc.Controller;


public class TileManager {

	private Vector<Tile> worldTiles = new Vector<Tile>();

	private Tile[][] world;
	private Controller c;
	
	public static final int MERCHANT_X = 2;
	public static final int MERCHANT_Y = 2;
	
	public TileManager() {
		world = new Tile[101][101];
		this.c = null;
		
		worldTiles.add(new Tile0_0());
		worldTiles.add(new Tile0_1());		
		worldTiles.add(new Tile1_0());
		
		worldTiles.add(new Tile9_99());
		worldTiles.add(new Tile9_100());
		
		worldTiles.add(new Tile10_100());
		
		worldTiles.add(new Tile11_100());
		worldTiles.add(new Tile11_99());
		worldTiles.add(new Tile11_98());
		worldTiles.add(new Tile11_97());
		
		worldTiles.add(new Tile10_97());
		worldTiles.add(new Tile10_96());
		
		worldTiles.add(new Tile12_97());
		worldTiles.add(new Tile12_96());
		worldTiles.add(new Tile12_95());
		worldTiles.add(new Tile12_94());
		worldTiles.add(new Tile12_93());
		worldTiles.add(new Tile11_93());
		worldTiles.add(new Tile10_93());
		worldTiles.add(new Tile9_93());
		
		worldTiles.add(new Tile9_94());
		worldTiles.add(new Tile10_94());
		
		worldTiles.add(new Tile8_94());
		worldTiles.add(new Tile8_95());
		worldTiles.add(new Tile8_96());
		worldTiles.add(new Tile7_96());
		
		makeWorld();
	}
	
	public Tile[][] makeWorld() {
		for(Tile t: worldTiles) {
			if(t.getXCoord() < world.length && t.getYCoord() < world[0].length) {
				world[t.getXCoord()][t.getYCoord()] = t;
			}
		}
		return world;
	}
	
	public Controller getController() { return this.c; }
	
	public Tile getTile(int xCoord, int yCoord) {
		Tile t = null;
		
		if(xCoord < world.length && yCoord < world[0].length) {
			t = world[xCoord][yCoord];
		}
		
		if(t != null) {
			c = t.getController();
		} else {
			c = new Controller();
		}
		
		return t;
	}
	
}
