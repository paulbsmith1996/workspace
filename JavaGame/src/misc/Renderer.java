package misc;
import gameobjects.GameObject;
import gameobjects.NPC;
import gameobjects.RawOre;
import handlers.HouseHandler;
import handlers.MerchantHandler;

import java.awt.Graphics;

import Tiles.Tile;
import Tiles.TileManager;
import cutscene.CutScene;


public class Renderer {

	protected TileManager tManager;
	private Controller c;
	private Tile t;
	private int xCoord, yCoord;
	private boolean playingCutScene;
	
	public Renderer(Controller c) {
		this.c = c;
		tManager = new TileManager();
		this.playingCutScene = false;
	}
	
	public Renderer() {
		this.c = new Controller();
		xCoord = 9;
		yCoord = 99;
	}
	
	public Controller getController() { return this.c; }
	public boolean isPlayingScene() { return this.playingCutScene; }
	
	public void renderMenu(Graphics g) {
		
	}
	
	public void renderGame(Graphics g, int xCoord, int yCoord) {
	
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		
		if (t == null || t.getXCoord() != xCoord || t.getYCoord() != yCoord) {
			t = tManager.getTile(xCoord, yCoord);
		}
		
		c = tManager.getController();
		
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
	
	public void renderBattle(Graphics g, int width, int height) {
		if (t == null || t.getXCoord() != xCoord || t.getYCoord() != yCoord) {
			t = tManager.getTile(xCoord, yCoord);
		}
		
		if(t != null) {
			t.draw(g);
		}
	}
	
	public void renderMerchant(Graphics g, MerchantHandler mh) {
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
	
	public void renderHouse(Graphics g, HouseHandler hh) {
		t = hh.houseBakground();
		
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
	
	public void renderGMenu() {
		
	}
}
