package misc;
import gameobjects.NPC;
import gameobjects.Player;
import handlers.BattleHandler;
import handlers.GameMenuHandler;
import handlers.HouseHandler;
import handlers.MainMenu;
import handlers.MerchantHandler;
import handlers.WorldHandler;
import inventories.Inventory;
import inventories.Stocks;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import resourceloaders.Audio;
import resourceloaders.AudioPlayer;
import resourceloaders.ResourceLoader;
import Enums.BattleState;
import Enums.GameState;
import Items.Fire;
import Items.HealthPotion;
import Items.Ice;
import Items.ManaPotion;
import Items.SpellBook;
import Items.WoodSword;
import Tiles.Tile;

import components.TextBox;

import cutscene.CutSceneLoader;

public class Game extends Applet implements Runnable {

	private final int WINDOW_WIDTH = 32 * 13, WINDOW_HEIGHT = 32 * 11;

	/****** Variables needed to run the Game and its Graphics *****/
	
	private Thread ticker;
	private boolean running = false;
	private KeyInput kInput;

	private GameState gState = GameState.MENU;
	private GameState prevState;

	private Rectangle appBounds;
	private int width;
	private int height;
	protected Rectangle leftBounds, rightBounds, topBounds, bottomBounds;
	
	private Vector<TextBox> texts = new Vector<TextBox>();
	

	// Start of the game is at (9, 99)
	private int xCoord = 9;
	private int yCoord = 99;
	
	private int entranceX, entranceY;
	
	protected final int NUMROWS = WINDOW_HEIGHT / 32;
	protected final int NUMCOLS = WINDOW_WIDTH / 32;
	
	private Controller c;
	private Renderer renderer;
	
	private CutSceneLoader csLoader;
	
	/********* Variables needed for Game logic *********/
	
	// int array holding stats for player
	private final int LEVEL = 1;
	private final int HP = 400 + 50 * LEVEL;
	private final int MANA = 150 + 30 * LEVEL;
	private final int AP = 40 + 20 * LEVEL;
	private final int DP = 30 + 20 * LEVEL;
	private final int MA = 30 + 20 * LEVEL;
	private final int MD = 40 + 20 * LEVEL;
	
	private int[] stats = { HP, MANA, AP, DP, LEVEL, MA, MD };

	// New instance of inventory for player to hold items
	private Inventory i = new Inventory();

	// Create player. And stock player's inventory
	private Player player;
	private SpellBook sb = new SpellBook(player);
	
	/************** Variables for Menu ****************/
	
	private MainMenu mainM;
	
	/************** Variables for Game Case Handlers ********/
	
	private GameMenuHandler gmh;
	private BattleHandler bh;
	private MerchantHandler merch;
	private HouseHandler hh;
	private WorldHandler wh;
	
	/*************** Variables for Game settings **************/
	
	private boolean playMusic;
	
	public void setState(GameState state) {
		gState = state;
	}
	
	public KeyInput getInterpreter() { return this.kInput; }
	public Player getPlayer() { return this.player; }
	public Renderer getRenderer() { return this.renderer; }
	
	public int entranceX() { return wh.getEntranceX(); }
	public int entranceY() { return wh.getEntranceY(); }
	
	public void setXCoord(int x) { this.xCoord = x; }
	public int getXCoord() { return this.xCoord; }
	
	public void setYCoord(int y) { this.yCoord = y; }
	public int getYCoord() { return this.yCoord; }
	
	public Tile getTile() { return renderer.tManager.getTile(xCoord, yCoord); }
	
	public void addTextBox(TextBox t) { this.texts.add(t); }
	public void removeTextBox(TextBox t) { this.texts.remove(t); }
	
	public Rectangle getLeftBounds() { return this.leftBounds; }
	public Rectangle getRightBounds() { return this.rightBounds; }
	public Rectangle getTopBounds() { return this.topBounds; }
	public Rectangle getBottomBounds() { return this.bottomBounds; }
	
	public boolean playMusic() { return this.playMusic; }
	
	public void init() {
		
		ResourceLoader.loadImages();
		ResourceLoader.loadSounds();
		ResourceLoader.loadMusic();
		
		csLoader = new CutSceneLoader(this);
		csLoader.loadScenes();
		
		this.resize(WINDOW_WIDTH, WINDOW_HEIGHT);
	
		this.appBounds = getBounds();
		this.width = appBounds.width;
		this.height = appBounds.height;
		
		
		this.playMusic = true;
		
		
		c = new Controller();
		
		this.entranceX = this.entranceY = 0;
		
		this.topBounds = new Rectangle(0, 0, WINDOW_WIDTH, 3);
		this.bottomBounds = new Rectangle(0, WINDOW_HEIGHT - 3, WINDOW_WIDTH, 3);
		this.leftBounds = new Rectangle(0, 0, 3, WINDOW_HEIGHT);
		this.rightBounds = new Rectangle(WINDOW_WIDTH - 3, 0, 3, WINDOW_HEIGHT);
		
		this.player = new Player(100,150,0,0,"Player", stats, i);
		
		
		i.addItem(new HealthPotion());
		i.addItem(new ManaPotion());
		i.addItem(new WoodSword());
		//i.addItem(new Pickaxe());
		
		//player.inventory.addMoney(10000);
		
		sb.add(new Fire(player));
		sb.add(new Ice(player));
		player.setSpellBook(sb);
		
		
		c.add(player);
		
		kInput = new KeyInput(player);
		this.addKeyListener(kInput);
		
		gmh = new GameMenuHandler(kInput, player, width, this);
		wh = new WorldHandler(this);
		
		renderer = new Renderer(c);
		
	}
	
	public void run() {
		while (running) {
			if(gState != GameState.GAME_MENU && gState != GameState.CUTSCENE) {
				prevState = gState;
			}
			
			if(kInput.getState() != gState) {
				kInput.setState(gState);
			}
			
			switch (gState) {
			case MENU:
				if(mainM == null) {
					mainM = new MainMenu(this);
				}
				
				mainM.update();
				AudioPlayer.setMusic(mainM.musicOn());
				kInput.setPause(false);
				
				break;
			case WANDER:
				if(playMusic) {
					AudioPlayer.playMusic(Audio.MUSIC_AMBIENT);
				}
				
				wh.wander();
				
				if(renderer.isPlayingScene()) {
					gState = GameState.CUTSCENE;
				}
				
				break;
			case BATTLE:
				
				NPC gob = wh.getOpponent();
				
				int playerX = player.getX();
				int playerY = player.getY();
				
				int oppX = gob.getX();
				int oppY = gob.getY();
				
				int oppVelX = gob.getVelX();
				int oppVelY = gob.getVelY();
				
				bh = new BattleHandler(gob, this);
				
				BattleState bState = bh.battle();
				
				switch(bState) {
				case ESCAPE:
					gob.setPos(oppX - 20, oppY - 20);
					gob.setVelX(oppVelX);
					gob.setVelY(oppVelY);
				case PLAYERWIN:
					gState = GameState.WANDER;
					player.setPos(playerX, playerY);
					break;
				case BWIN:
					break;
				case ONGOING:
					break;
				}
				break;
				
			case GAME_MENU:
				gmh.gMenuSetUp();
				
				try {
					ticker.sleep(60);
				} catch (InterruptedException e) {}
				
				if (!gmh.pause()) {
					gState = prevState;
					gmh.setPause(false);
				}
				
				break;
			case MERCHANT:
				Stocks wares = null;
				if(merch != null) {
					wares = merch.getWares();
				}
				if(merch == null || merch.getWares() != wares) {
					merch = new MerchantHandler(this, renderer, 
							new Stocks(player, player.getLevel()));
				}
				
				merch.setUp();
				
				if (kInput.pause()) {
					kInput.setBack(false);
					gState = GameState.GAME_MENU;
				}
				break;
			case HOUSE:
				if(hh == null || hh.isNewHouse()) {
					hh = new HouseHandler(this, renderer, player, 
							entranceX() / 32, entranceY() / 32);
				}
				
				hh.update();
				break;
			case CUTSCENE:
				if(!renderer.isPlayingScene()) {
					gState = prevState;
				}
				break;
			}
			
			repaint();
			
			try {
				ticker.sleep(1000 / 30);
			} catch (InterruptedException e) {}

		}
		
	}
	
	public void paint(Graphics g) {
		switch(gState) {
		case MENU:
			
			renderer.renderGame(g, 10, 94);
			
			int selected = 0;
			try {
				selected = mainM.getSelected();
			} catch (NullPointerException e) {
				selected = 0;
			}
			
			if(mainM != null) {
				mainM.draw(g, selected);
			}
			break;
		case WANDER:
			renderer.renderGame(g, xCoord, yCoord);
			
			c.draw(g);
			break;
		case BATTLE:
			if(bh != null) {
				bh.draw(g);
			}
			break;
		case GAME_MENU:
			switch(prevState) {
			case WANDER:
				renderer.renderGame(g, xCoord, yCoord);
				break;
			case MERCHANT:
				renderer.renderMerchant(g, merch);
				break;
			case HOUSE:
				renderer.renderHouse(g, hh);
				break;
			}	
			
			if(gmh != null) {
				gmh.draw(g);
			}
			break;
		case MERCHANT:			
			if(merch != null) {
				merch.draw(g);
			}
			break;
		case HOUSE:
			if(hh != null) {
				hh.draw(g);
			}
			break;
		}
		
		for(TextBox t: texts) {
			t.draw(g);
		}
	}
	
	public void start() {
		// Check for either no Thread or a dead Thread
		if (ticker == null || !ticker.isAlive()) {
			running = true;
			// Reassign ticker in case it is only dead
			ticker = new Thread(this);
			ticker.setPriority(Thread.MIN_PRIORITY + 1);
			ticker.start();
		}
	}
	
	public void stop() {
		running = false;
		
	}
	
	public void sleep(long millis) {
		try {
			ticker.sleep(millis);
		} catch (InterruptedException e) {}
	}
	
	public void waitInput() {
		while (!kInput.ready() && !kInput.goBack()) {
			try {
				ticker.sleep(30);
			} catch (InterruptedException e) {}
    	}
		kInput.setReady(false);
	}
	
	public Controller getController() { return this.c; }
	
}
