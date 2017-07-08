package Tiles;

import java.awt.Graphics;
import java.util.Vector;

import cutscene.CutScene;
import gameobjects.BushyTreeLL;
import gameobjects.BushyTreeLR;
import gameobjects.BushyTreeTL;
import gameobjects.BushyTreeTR;
import gameobjects.Creature;
import gameobjects.GameObject;
import gameobjects.HouseDoor;
import gameobjects.HouseLeft;
import gameobjects.HouseRight;
import gameobjects.MerchantCenter;
import gameobjects.MerchantLeft;
import gameobjects.MerchantRight;
import gameobjects.NPC;
import gameobjects.Obstacle;
import misc.Controller;
import misc.Game;

public class Tile {

	private int xCoord, yCoord;
	
	private GameObject[][] tileObjects;
	private GameObject[][] backGround;
	private final int NUMROWS = 11;
	private final int NUMCOLS = 13;
	private Controller c;
	private Vector<Creature> mobs;
	private CutScene cs;
	
	private Game game;
	
	public Tile(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.c = new Controller();
		
		this.cs = null;
		
		tileObjects = new GameObject[NUMCOLS][NUMROWS];
		backGround = new GameObject[NUMCOLS][NUMROWS];
		
		mobs = new Vector<Creature>();
		create();
	}
	
	public Tile(int xCoord, int yCoord, Game game) {
		this.game = game;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.c = new Controller();
		
		this.cs = null;
		
		tileObjects = new GameObject[NUMCOLS][NUMROWS];
		backGround = new GameObject[NUMCOLS][NUMROWS];
		
		mobs = new Vector<Creature>();
		create();
	}
	
	public Tile(Controller c) {
		this.c = c;
		this.xCoord = 0;
		this.yCoord = 0;
		tileObjects = new GameObject[NUMCOLS][NUMROWS];
		mobs = new Vector<Creature>();
		create();
	}
	
	public Game getGame() { return this.game; }
	
	public int getXCoord() { return this.xCoord; }
	public int getYCoord() { return this.yCoord; }
	
	public int numCols() { return NUMCOLS; }
	public int numRows() { return NUMROWS; }
	
	public Controller getController() { return this.c; }
	
	public CutScene getCutScene() { return this.cs; }
	public void setCutScene(CutScene cs) { this.cs = cs; }
	
	public void addMob(Creature creature) {
		mobs.add(creature);
		if(creature.getHp() > 0) {
			c.add(creature);
		}
	}
	
	public void addMerchant(int x, int y) {
		setObject(new MerchantLeft(), x, y);
		setObject(new MerchantCenter(), ++x, y);
		setObject(new MerchantRight(), ++x, y);
	}
	
	public void addHouse(int x, int y) {
		setObject(new HouseLeft(), x, y);
		setObject(new HouseDoor(), ++x, y);
		setObject(new HouseRight(), ++x, y);
	}
	
	public void addBushyTree(int x, int y) {
		setObject(new BushyTreeTL(), x, y);
		setObject(new BushyTreeTR(), x + 1, y);
		setObject(new BushyTreeLL(), x, y + 1);
		setObject(new BushyTreeLR(), x + 1, y + 1);
	}
	
	public void removeMob(Creature creature) {
		mobs.remove(creature);
		c.remove(creature);
	}
	
	public Vector<Creature> getMobs() { return this.mobs; }
	
	public GameObject[][] getObjects() { return this.tileObjects; }
	
	public void setObject(GameObject obj, int x, int y) {
		obj.setX(x * 32);
		obj.setY(y * 32);
		c.remove(tileObjects[x][y]);
		tileObjects[x][y] = obj;
		if(obj instanceof NPC || obj instanceof Obstacle) {
			c.add(obj);
		} else {
			backGround[x][y] = obj;
		}
	}
	
	public GameObject removeObject(GameObject obj) {
		
		for(int x = 0; x < tileObjects.length; x++) {
			for(int y = 0; y < tileObjects[0].length; y++) {
				if(obj.equals(tileObjects[x][y])) {
					GameObject temp = tileObjects[x][y];
					tileObjects[x][y] = backGround[x][y];
					return temp;
				}
			}
		}
		
		return null;
	}
	
	public void setRow(GameObject obj, int y) {
		Class C = obj.getClass();
		for(int x = 0; x < numCols(); x++) {
			try {
				setObject((GameObject)C.newInstance(), x, y);
			} catch(Exception e) {}
		}
	}
	
	public void setColumn(GameObject obj, int x) {
		Class C = obj.getClass();
		for(int y = 0; y < numCols(); y++) {
			try {
				setObject((GameObject)C.newInstance(), x, y);
			} catch(Exception e) {}
		}
	}
	
	public void setArea(GameObject obj, int x1, int y1, int x2, int y2) {
		Class C = obj.getClass();
		for(int x = x1; x <= x2; x++) {
			for(int y = y1; y <= y2; y++) {
				try{
					setObject((GameObject)C.newInstance(), x, y);
				} catch(Exception e) {}
			}
		}
	}
	
	public void draw(Graphics g) {
		for(int x = 0; x < numCols(); x++) {
			for(int y = 0; y < numRows(); y++) {
				GameObject behind = backGround[x][y];
				GameObject obj = tileObjects[x][y];
				if(behind != null) {
					behind.draw(g);
				}
				if(obj != null) {
					obj.draw(g);
				}
			}
		}
	}
	
	// To override
	public void create() {
		
	}
}
