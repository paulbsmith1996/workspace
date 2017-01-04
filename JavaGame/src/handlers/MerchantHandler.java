package handlers;
// Paul Baird-Smith 2015

import gameobjects.GameObject;
import gameobjects.Merchant;
import gameobjects.Player;
import inventories.Pocket;
import inventories.Stocks;

import java.awt.Graphics;
import java.util.Random;
import java.util.Scanner;

import misc.Controller;
import misc.Game;
import misc.KeyInput;
import misc.Renderer;
import Enums.GameState;
import Enums.MerchantState;
import Items.Item;
import Items.ItemReference;
import Items.Spell;
import Tiles.MerchantTile;

import components.NumberSelector;
import components.TextBox;
import components.TextMenu;

/**
 *
 * Class Merchant - Describes buy and sell interactions between a Creature
 * object and the Merchant. The Creature can buy all sorts of randomly generated
 * Items here and can sell back Spells and Items for a discounted price
 *
 */
public class MerchantHandler {

	/**
	 * Vector holds Merchant's inventory
	 */
	protected Stocks wares;

	/**
	 * Create a Random object to generate Merchant's inventory
	 */
	protected Random r;

	protected Scanner scan;

	/**
	 * Player object that is interacting with the Merchant
	 */
	protected Player player;

	/**
	 * Holds the interactor's total money. Important for transactions
	 */
	protected int playerMon;

	private Controller c;	
	private Renderer renderer;
	private MerchantTile environment;
	
	private KeyInput kInput;	
	private Merchant vendor;	
	private MerchantState merchState;	
	private Game game;
	
	private TextBox t;
	private TextMenu m;
	private TextBox moneyText;
	private NumberSelector numSelect;
	
	private final int TALKINGDISTANCE = 128;
	
	private int width;
	private int height;
	
	/**
	 * Constructor means Merchant object is aware of player
	 */
	public MerchantHandler(Game game, Renderer renderer, Stocks wares) {
		
		this.game = game;
		this.environment = new MerchantTile();
		this.c = environment.getController();
		
		this.kInput = game.getInterpreter();
		kInput.setState(GameState.MERCHANT);
		
		this.renderer = renderer;		
		
		width = game.getWidth();
		height = game.getHeight();
		
		t = new TextBox(0, height - 30, width, 30, game);
		
		this.player = game.getPlayer();
		
		c.add(player);
		
		if (vendor == null) {
			for (GameObject obj : c) {
				if (obj instanceof Merchant) {
					this.vendor = (Merchant)obj;
				}
			}
		}
		
		this.wares = wares;
		
		playerMon = player.getInventory().getMoney();

		// Initialize relevant instance variables
		wares = new Stocks(player, player.getLevel());

		// Initialize Scanner and Random objects
		scan = new Scanner(System.in);
		r = new Random();
		
		merchState = MerchantState.PERUSE;
	}
	
	public Stocks getWares() { return this.wares; }
	public MerchantTile getEnvironment() { return this.environment; }
	
	public void setUp() {
		switch(merchState) {
		case PERUSE:
			peruse();
			break;
		case CONVERSE:
			converse();
			break;
		}
		game.repaint();
	}
	
	public void peruse() {
		if(m != null) {
			m.setVisible(false);
		}
		
		if(moneyText != null) {
			moneyText.setVisible(false);
		}
		
		if(t != null) {
			t.setVisible(false);
		}
		
		kInput.setMerchState(MerchantState.PERUSE);
		player.move(c);
		
		if(player.getY() + player.getHeight() > game.getHeight()) {
			player.setPos(game.entranceX(), game.entranceY());
			game.setState(GameState.WANDER);
		}
		
		if(vendor != null && player.getDistance(vendor) < TALKINGDISTANCE) {
			if(kInput.isTalking()) {
				merchState = MerchantState.CONVERSE;
			}
		}
	}

	public int converse() {
		
		player.setStill();

		boolean buying = true;

		kInput.setMerchState(MerchantState.CONVERSE);
		
		String[] buysell = { "BUY", "SELL" };
		
		String[] sellChoices = { "Weapons", "Armor", "Potions", "Utility",
				"Ore" };
		
		String[] buyChoices = { "Weapons", "Armor", "Potions", "Utility", 
				"Ore", "Spells"};
		
		// Retrieve player's total amount of money
		playerMon = player.getInventory().getMoney();

		displayMenu(buysell, 1, true);
		
		displayMoney();
		moneyText.setVisible(true);
		
		if (m.getSelected() == 0) {
			buying = true;
			displayMenu(buyChoices, 1, true);
		} else if (m.getSelected() == 1) {
			buying = false;
			displayMenu(sellChoices, 1, true);
		} else if (m.getSelected() == 2) {
			merchState = MerchantState.PERUSE;
			kInput.setTalking(false);
			return 0;
		}

		if (m.getSelected() == 5) {
			// User requests to buy/sell Spells
			if(buying) {
				selection(wares.findPocket(ItemReference.SPELL), buying);
			}
		} else if (m.getSelected() == 2) {
			// User requests to buy/sell Potions
			selection(wares.findPocket(ItemReference.POTION), buying);
		} else if (m.getSelected() == 0) {
			// User requests to buy/sell Weapons
			selection(wares.findPocket(ItemReference.WEAPON), buying);
		} else if (m.getSelected() == 1) {
			// User requests to buy/sell Armors
			selection(wares.findPocket(ItemReference.ARMOR), buying);
		} else if (m.getSelected() == 3) {
			selection(wares.findPocket(ItemReference.UTILITY), buying);
		} else if (m.getSelected() == 4) {
			selection(wares.findPocket(ItemReference.ORE), buying);
		}
		return 0;
	}

	/**
	 * Returns a String representation of Merchant's inventory
	 */
	public String toString() {
		return wares.toString();
	}

	/**
	 * Used for the interactor to buy items from the Merchant
	 *
	 * @param Item
	 *            - Item that user would like to buy
	 * @param int - Amount of Item user would like to buy
	 */
	public void buy(Item a, int quant) {

		// Calculate total cost of transaction first to see if possible
		int totalCost = quant * a.getCost();

		// Remove correct sum of money from user's Inventory
		player.getInventory().removeMoney(totalCost);
		playerMon -= totalCost;

		// Remove correct number of items from Merchant's Inventory
		for (int i = 0; i < quant; i++) {
			wares.removeItem(a);
		}

		// Different cases: is or is not a Spell
		if (a.getItemType() != ItemReference.SPELL) {
			// Item is not a Spell
			player.store(a, quant);
		} else {
			// Item is a Spell
			player.getBook().add((Spell) a);
		}
	}

	/**
	 * Used to sell user's items to Merchant for smaller sums than their
	 * original prices
	 *
	 * @pre: the amount entered is <= amount of item in inventory && item to be
	 *       sold is not a Spell
	 * @post: user and Merchant have correct amount of items and money
	 *
	 * @param Item
	 *            - Item to be sold by user
	 * @param int - Amount of this Item to be sold to the Merchant
	 */
	public void sell(Item desired, int amount) {

		// Calculate total amount of money to be made by transaction
		int totalRev = ((3 * desired.getCost()) / 4) * amount;

		// Remove the correct amount of items from user's Inventory
		for (int i = 0; i < amount; i++) {
			player.getInventory().removeItem(desired);
		}

		player.getInventory().addMoney(totalRev);
	}

	/**
	 * Allows user to select the Item they would like to buy/sell
	 *
	 * @pre: pocket != null
	 * @post: user has selected a valid item to buy/sell
	 *
	 * @param Pocket
	 *            - Pocket from which requested Item can be selected
	 * @param boolean - Determines whether user is buying or selling Items
	 */
	public int selection(Pocket pocket, boolean buying) {

		if (!buying) {
			// User is selling Items
			Pocket playerPock = player.getInventory().findPocket(pocket.getType());
			pocket = playerPock;
		}
		
		if(pocket.isEmpty()) {
			displayText("No " + pocket.getName() + "s left");
			game.sleep(100);
			return 0;
		}

		// Display Pocket's contents with prices
		String[] choices = pocket.toPrices(buying);
		displayMenu(choices,2,true);

		if(m.getSelected() >= m.numChoices()) {
			game.sleep(100);
			return 0;
		}
		
		String itemName = pocket.itemNames()[m.getSelected()];
		String amountStr = "0";
		
		numSelect = new NumberSelector(275, 60);
		game.addKeyListener(numSelect);
		while(!numSelect.isReady()) {
			amountStr = numSelect.getValue();
			game.repaint();
		}
		
		game.removeKeyListener(numSelect);
		numSelect.setVisible(false);
		
		 Item desired = pocket.findItem(itemName);
		 int amount = Integer.parseInt(amountStr);
		
		 if (buying) {
			// User is buying Items
			// Determine if transaction is feasible with user's money
			if (amount * desired.getCost() > playerMon) {
				game.sleep(125);
				displayText("Cannot afford this");
				return 0;
			}

		} else {
			// User is selling Items
			// Determine if user has enough Items for a feasible transaction
			if (amount > player.getInventory().getQuantity(desired)) {
				displayText("You do not have this many items to sell");
				return 0;
			}
		}

		if (buying) {
			// If user buying Items, make call to buy method
			buy(desired, amount);
		} else {
			// If user selling Items, make call to sell method
			sell(desired, amount);
		}
		
		displayMoney();
		return 0;

	}

	
	/*************** Handle Graphics here **************************/
	
	
	public void displayMoney() {
		String money = Integer.toString(player.getInventory().getMoney());
		moneyText = new TextBox(10, 10, 100, 20, game);
		moneyText.setText("Money: " + money);
		moneyText.setVisible(true);

		game.repaint();
	}

	public void displayText(String text) {
		t.setText(text);
		
		if (m != null) {
			m.setVisible(false);
		}
		
		t.setVisible(true);
		game.repaint();
		game.waitInput();
	}

	public void displayMenu(String[] choices, int width, boolean canExit) {

		m = new TextMenu(choices, width, this.width / 2, 10, game, canExit);

		t.setVisible(false);
		// Update KeyInput's properties to reflect dimensions/choices of
		m.select();
	}

	public void draw(Graphics g) {
		renderer.renderMerchant(g, this);

		if (m != null) {
			m.draw(g);
		}

		if (t != null) {
			t.draw(g);
		}

		if (moneyText != null) {
			moneyText.draw(g);
		}
		
		if(numSelect != null) {
			numSelect.draw(g);
		}
	}

}