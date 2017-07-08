package Tiles;
import java.util.Vector;

import misc.Controller;


public class TileManager {

	private static Vector<Tile> worldTiles = new Vector<Tile>();

	private static Tile[][] world;
	private static Controller c;
	
	public static final int MERCHANT_X = 2;
	public static final int MERCHANT_Y = 2;
	
	public static void initTileManager() {
		world = new Tile[101][101];
		c = null;
		
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
		worldTiles.add(new Tile9_96());
		
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
		worldTiles.add(new Tile7_95());
		worldTiles.add(new Tile7_94());
		worldTiles.add(new Tile7_93());
		worldTiles.add(new Tile7_92());
		worldTiles.add(new Tile7_91());
		
		worldTiles.add(new Tile7_90());
		worldTiles.add(new Tile6_90());
		
		makeWorld();
	}
	
	public static Tile[][] makeWorld() {
		for(Tile t: worldTiles) {
			if(t.getXCoord() < world.length && t.getYCoord() < world[0].length) {
				world[t.getXCoord()][t.getYCoord()] = t;
			}
		}
		return world;
	}
	
	//public static Controller getController() { return c; }
	public static Controller getController(int xCoord, int yCoord) {
		Tile t = null;
		
		if(xCoord < world.length && yCoord < world[0].length) {
			t = world[xCoord][yCoord];
		}
		
		if(t != null) {
			c = t.getController();
		} else {
			c = null;
		}
		
		return c;
	}
	
	public static Tile getTile(int xCoord, int yCoord) {
		Tile t = null;
		
		if(xCoord < world.length && yCoord < world[0].length) {
			t = world[xCoord][yCoord];
		}
		
		return t;
	}
	
}
