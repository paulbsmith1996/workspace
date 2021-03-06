package misc;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import Enums.BattleState;
import Enums.GameState;
import Items.HealthPotion;
import Items.ManaPotion;
import Items.WoodSword;
import Tiles.Tile;
import Tiles.TileManager;
import components.NumberSelector;
import components.TextBox;
import components.TextMenu;
import cutscene.CutSceneLoader;
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
import resourceloaders.Audio;
import resourceloaders.AudioPlayer;
import resourceloaders.ResourceLoader;
import spell.Fire;
import spell.Ice;
import spell.SpellBook;
import spell.SpellMap;

public class Game extends Applet implements Runnable {

	/****** Variables needed to run the Game and its Graphics *****/
	
	public static final int WINDOW_WIDTH = 32 * 13, WINDOW_HEIGHT = 32 * 11;
	public static final int FPS = 30; 
	
	private final int T_MENU_X = 45, T_MENU_Y = 75;
	
	private final int GAME_OVER_COUNT = 5;
	private int count = 0;
	
	private Thread ticker;
	private boolean running = false;
	private KeyInput kInput;

	private Rectangle appBounds;
	private int width;
	private int height;
	protected Rectangle leftBounds, rightBounds, topBounds, bottomBounds;
	
	private Vector<TextBox> texts = new Vector<TextBox>();
	

	/*************** Variables needed for game logic ***************/

	private GameState gState = GameState.MENU;
	private GameState prevState;
	
	// Start of the game is at (9, 99)
	private int xCoord = 9;
	private int yCoord = 100;
	
	private final int START_X_COORD = 100, START_Y_COORD = 150;
	
	protected final int NUMROWS = WINDOW_HEIGHT / 32;
	protected final int NUMCOLS = WINDOW_WIDTH / 32;
	
	private Controller c;
	
	private final int ESCAPE_OFFSET_X = 20, ESCAPE_OFFSET_Y = 20;
	
	/********* Variables needed for Game start *********/
	
	// int array holding stats for player
	private final int LEVEL = 3;
	private final int HP = 		1500 + 50 * (LEVEL - 1);
	private final int MANA = 	180 + 30 * (LEVEL - 1);
	private final int AP = 		230 	+ 30 * (LEVEL - 1);
	private final int DP = 		200 	+ 30 * (LEVEL - 1);
	private final int MA = 		210 	+ 30 * (LEVEL - 1);
	private final int MD = 		220 	+ 30 * (LEVEL - 1);
	
	private int[] stats = { HP, MANA, AP, DP, LEVEL, MA, MD };

	// New instance of inventory for player to hold items
	private Inventory i = new Inventory();

	// Create player. And stock player's inventory
	private Player player;
	private SpellBook sb;
	
	private SpellMap sMap;
	
	/************** Variables for Menu ****************/
	
	private MainMenu mainM;
	private TextBox moneyText, infoText;
	private TextMenu tMenu;
	private NumberSelector numSelect;
	
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
	
	public HouseHandler getHouseHandler() { return this.hh; }
	
	public KeyInput getInterpreter() { return this.kInput; }
	public Player getPlayer() { return this.player; }
	public SpellMap getSpellMap() { return this.sMap; }
	
	public TextMenu getTextMenu() { return this.tMenu; }
	public NumberSelector getNumSelect() { return this.numSelect; }
	public void newNumSelect() { 
		this.numSelect = new NumberSelector(275, 60);
		this.numSelect.setVisible(false);
	}
	public TextBox getMoneyText() { return this.moneyText; }
	public TextBox getInfoText() { return this.infoText; }
	
	public Controller getController() { return this.c; }
	public void setController(Controller cont) { this.c = cont; }
	
	public int entranceX() { return wh.getEntranceX(); }
	public int entranceY() { return wh.getEntranceY(); }
	
	public void setXCoord(int x) { this.xCoord = x; }
	public int getXCoord() { return this.xCoord; }
	
	public void setYCoord(int y) { this.yCoord = y; }
	public int getYCoord() { return this.yCoord; }
	
	public Tile getTile() { 
		return TileManager.getTile(xCoord, yCoord); 
	}
	
	public void addTextBox(TextBox t) { this.texts.add(t); }
	public void removeTextBox(TextBox t) { this.texts.remove(t); }
	
	public Rectangle getLeftBounds() { return this.leftBounds; }
	public Rectangle getRightBounds() { return this.rightBounds; }
	public Rectangle getTopBounds() { return this.topBounds; }
	public Rectangle getBottomBounds() { return this.bottomBounds; }
	
	public boolean playMusic() { return this.playMusic; }
	
	// Initializes all app settings, player starting conditions, resource loaders
	// and game state handlers
	public void init() {
		
		initAppSettings();
		initResources();
			
		c = new Controller();
		
		initPlayer();
		initKeyInput();
		
		initHandlers();
		
	}
	
	// Initializes all static loaders and their respective resources 
	public void initResources() {
		ResourceLoader.loadImages();
		ResourceLoader.loadSounds();
		ResourceLoader.loadMusic();
		
		CutSceneLoader.initCutSceneLoader(this);
		CutSceneLoader.loadScenes();
		
		TileManager.initTileManager();
		Renderer.initRenderer(c);
	
	}
	
	// Initializes all settings of the app, including whether music is playing or not
	// Also initializes all variables depending on the app settings, like bounds
	public void initAppSettings() {
		this.resize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		this.appBounds = getBounds();
		this.width = appBounds.width;
		this.height = appBounds.height;
		
		this.playMusic = true;
		
		this.topBounds = new Rectangle(0, 0, WINDOW_WIDTH, 3);
		this.bottomBounds = new Rectangle(0, WINDOW_HEIGHT - 3, WINDOW_WIDTH, 3);
		this.leftBounds = new Rectangle(0, 0, 3, WINDOW_HEIGHT);
		this.rightBounds = new Rectangle(WINDOW_WIDTH - 3, 0, 3, WINDOW_HEIGHT);
		
		numSelect = new NumberSelector(275, 60);
		numSelect.setVisible(false);
	}
	
	// Initializes all the default settings for the player and adds it to the game's controller
	public void initPlayer() {
		this.player = new Player(START_X_COORD,START_Y_COORD,0,0,"Player", stats, i);
		
		i.addItem(new HealthPotion());
		i.addItem(new ManaPotion());
		i.addItem(new WoodSword());
		//i.addItem(new Pickaxe());
		
		//player.getInventory().addMoney(10000);
		
		sb = new SpellBook(player);
		//sb.add(new Fire());
		//sb.add(new Ice());
		player.setSpellBook(sb);
		
		sMap = new SpellMap(player);
		
		
		c.add(player);
	}
	
	// Initializes the keyboard input listener
	public void initKeyInput() {
		kInput = new KeyInput(player);
		this.addKeyListener(kInput);
	}
	
	// Initializes objects that handle different game states
	public void initHandlers() {
		gmh = new GameMenuHandler(kInput, player, width, this);
		wh = new WorldHandler(this);
	}
	
	public void run() {
		while (running) {
			
			this.requestFocus();
			
			if(gState != GameState.GAME_MENU && gState != GameState.CUTSCENE) {
				prevState = gState;
			}
			
			if(kInput.getState() != gState) {
				kInput.setState(gState);
			}
			
			switch (gState) {
			case MENU:
				
				handleMenu();
				
				break;
			case WANDER:
				
				handleWander();
				
				break;
			case BATTLE:
				
				handleBattle();
				
				break;
			case GAME_MENU:
				
				handleMain();
				
				break;
			case HOUSE:
				
				handleHouse();
				
				break;
			case CUTSCENE:
				
				handleCutScene();
				
				break;
			case GAME_OVER:
				AudioPlayer.stopPlaying();
				if(count >= GAME_OVER_COUNT * (1000 / FPS)) {
					gState = GameState.MENU;
				}
				count++;
				break;
			}
			
			repaint();
			
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {}

		}
		
	}
	
	// Handles case where game state is MENU
	public void handleMenu() {
		if(mainM == null) {
			mainM = new MainMenu(this);
		}
		
		mainM.update();
		AudioPlayer.setMusic(mainM.musicOn());
		kInput.setPause(false);
	}
	
	// Handles case where game state is WANDER
	public void handleWander() {
		//if(playMusic) {
		//	AudioPlayer.playMusic(Audio.MUSIC_AMBIENT);
		//}
		
		wh.wander();
		
		if(Renderer.isPlayingScene()) {
			gState = GameState.CUTSCENE;
		}
	}
	
	// Handles case where game state is BATTLE
	public void handleBattle() {
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
			gob.setPos(oppX - ESCAPE_OFFSET_X, oppY - ESCAPE_OFFSET_Y);
			gob.setVelX(-oppVelX);
			gob.setVelY(-oppVelY);
		case PLAYERWIN:
			gState = GameState.WANDER;
			player.setPos(playerX, playerY);
			break;
		case BWIN:
			gState = GameState.GAME_OVER;
			break;
		case ONGOING:
			break;
		}
	}
	
	// Handles case where game state is GAME_MENU
	public void handleMain() {
		gmh.gMenuSetUp();
		
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {}
		
		if (!gmh.pause()) {
			gState = prevState;
			gmh.setPause(false);
		}
	}
	
	// Handles case where game state is MERCHANT
	public void handleMerchant() {
		Stocks wares = null;
		if(merch != null) {
			wares = merch.getWares();
		}
		if(merch == null || merch.getWares() != wares) {
			merch = new MerchantHandler(this, new Stocks(player, player.getLevel()));
		}
		
		merch.setUp();
		
		if (kInput.pause()) {
			kInput.setBack(false);
			gState = GameState.GAME_MENU;
		}
	}
	
	// Handles case where game state is HOUSE
	public void handleHouse() {
		if(hh == null || hh.isNewHouse()) {
			hh = new HouseHandler(this, player, entranceX() / 32, entranceY() / 32);
		}
		
		hh.update();
	}
	
	// Handles case where game state is CUTSCENE
	public void handleCutScene() {
		if(!Renderer.isPlayingScene()) {
			gState = prevState;
		}
	}
	
	public void paint(Graphics g) {
		switch(gState) {
		case MENU:		
			paintMenu(g);
			break;
		case WANDER:
			Renderer.renderGame(g, xCoord, yCoord);
			break;
		case BATTLE:
			if(bh != null) {
				bh.draw(g);
			}
			break;
		case GAME_MENU:
			paintMain(g);
			break;
		case HOUSE:
			if(hh != null) {
				hh.draw(g);
			}
			break;
		case CUTSCENE:
			break;
		case GAME_OVER:
			paintGameOver(g);
			break;
		}
		
		for(TextBox t: texts) {
			t.draw(g);
		}
		
		if (tMenu != null) {
			tMenu.draw(g);
		}

		if (infoText != null) {
			infoText.draw(g);
		}

		if (moneyText != null) {
			String money = Integer.toString(player.getInventory().getMoney());
			//moneyText = new TextBox(10, 10, 200, 30, this, true);
			moneyText.setText("Money: " + money);
			moneyText.draw(g);
		}
		
		if(numSelect != null) {
			numSelect.draw(g);
		}
	}
	
	// Paints the in game menu
	public void paintMenu(Graphics g) {
		Renderer.renderGame(g, 10, 94);
		
		int selected = 0;
		try {
			selected = mainM.getSelected();
		} catch (NullPointerException e) {
			selected = 0;
		}
		
		if(mainM != null) {
			mainM.draw(g, selected);
		}
	}
	
	// Paints the main menu
	public void paintMain(Graphics g) {
		switch(prevState) {
		case WANDER:
			Renderer.renderGame(g, xCoord, yCoord);
			break;
		case HOUSE:
			Renderer.renderHouse(g, hh);
			break;
		}	
		
		if(gmh != null) {
			gmh.draw(g);
		}
	}
	
	public void paintGameOver(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Times New Roman", Font.BOLD, 26));
		String go = "GAME OVER";
		g.drawString(go, 
					 WINDOW_WIDTH / 2 - g.getFontMetrics().stringWidth(go) / 2, 
					 WINDOW_HEIGHT / 2 + g.getFontMetrics().getHeight() / 2);
	}
	
/*************** Handle Graphics here **************************/
	
	
	public void displayMoney() {
		String money = Integer.toString(player.getInventory().getMoney());
		if(moneyText == null) {
			moneyText = new TextBox(10, 10, 200, 30, this, true);
		}
		moneyText.setText("Money: " + money);
		moneyText.setVisible(true);

		repaint();
	}

	public void displayText(String text) {
		infoText = new TextBox(0, height - 30, width, 30, this, true);
		infoText.setText(text);
		
		if (tMenu != null) {
			tMenu.setVisible(false);
		}
		
		infoText.setVisible(true);
		repaint();
		waitInput();
	}

	public void displayMenu(String[] choices, int width, boolean canExit) {

		tMenu = new TextMenu(choices, width, T_MENU_X, T_MENU_Y, this, canExit);
		tMenu.setVisible(true);

		if(infoText != null) {
			infoText.setVisible(false);
		}
		
		// Update KeyInput's properties to reflect dimensions/choices of
		tMenu.select();
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
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
	
	public void waitInput() {
		while (!kInput.ready() && !kInput.goBack()) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {}
    	}
		kInput.setReady(false);
	}
}
