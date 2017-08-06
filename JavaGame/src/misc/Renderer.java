package misc;
import java.awt.Graphics;

import Tiles.BattleTileDirt;
import Tiles.BattleTileMine;
import Tiles.Tile;
import Tiles.TileManager;
import cutscene.CutScene;
import gameobjects.GameObject;
import gameobjects.NPC;
import gameobjects.RawOre;
import handlers.HouseHandler;
import handlers.MerchantHandler;


public class Renderer {

	private static Controller c;
	private static Tile t;
	private static int xCoord, yCoord;
	private static boolean playingCutScene;
	
	public static void initRenderer(Controller cont) {
		c = cont;
		playingCutScene = false;
	}
	
	public static Controller getController() { return c; }
	public static boolean isPlayingScene() { return playingCutScene; }
	
	public static void renderMenu(Graphics g) {
		
	}
	
	public static void renderGame(Graphics g, int xCoord, int yCoord) {
		
		if (t == null || t.getXCoord() != xCoord || t.getYCoord() != yCoord) {
			t = TileManager.getTile(xCoord, yCoord);
		}
		
		c = TileManager.getController(xCoord, yCoord);
		
		if (t != null) {
			
			t.draw(g);
			CutScene cs = t.getCutScene();
			
			if(cs != null && !cs.played()) {
				playingCutScene = true;
				cs.play(g);
			}
			
			if(cs == null || cs.played()) {
				playingCutScene = false;
			}
		}
		
		
		if(c != null) {
			for(int x = 0; x < c.size(); x++) {
				GameObject obj = c.elementAt(x);
				if(obj instanceof NPC && ((NPC) obj).getHp() <= 0) {
					c.remove(obj);
				} else if(obj instanceof RawOre && ((RawOre)obj).isUsed()) {
					c.remove(obj);
					t.removeObject(obj);
				}
			}
			
			c.draw(g);
		}
	}
	
	public static void renderBattle(Graphics g, int width, int height, int xCoord, int yCoord) {
		if (t == null || t.getXCoord() != xCoord || t.getYCoord() != yCoord) {
			t = TileManager.getTile(xCoord, yCoord);
		}
		
		if(t != null) {
			int mostCommon = t.getMostCommonObject();
			
			if(mostCommon == 0 || mostCommon == 1) {
				t = new BattleTileDirt();
			} else if(mostCommon == 2) {
				t = new BattleTileMine();
			}
			t.draw(g);
		}
	}
	
	public static void renderMerchant(Graphics g, MerchantHandler mh) {
		t = mh.getEnvironment();
		
		c = t.getController();
		
		if (t != null) {
			t.draw(g);
		}
		
		if(c != null) {
			for(GameObject obj: c) {
				if(obj instanceof NPC && ((NPC) obj).getHp() <= 0) {
					c.remove(obj);
				}
			}
			c.draw(g);
		}
	}
	
	public static void renderHouse(Graphics g, HouseHandler hh) {
		t = hh.houseBackground();
		
		c = t.getController();
		
		if(t != null) {
			t.draw(g);
		}
		
		if(c != null) {
			for(GameObject obj: c) {
				if(obj instanceof NPC && ((NPC) obj).getHp() <= 0) {
					c.remove(obj);
				}
			}
			c.draw(g);
		}
	}
	
	public static void renderGMenu() {
		
	}
}
