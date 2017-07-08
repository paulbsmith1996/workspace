package gameobjects;

import java.util.Random;

import Enums.GameState;
import Items.Item;
import Items.ItemReference;
import components.NumberSelector;
import components.TextMenu;
import handlers.MerchantHandler;
import inventories.Pocket;
import inventories.Stocks;
import misc.Game;
import misc.KeyInput;
import resourceloaders.Images;

public class Merchant extends Interactable {
	
	private final int MENU_X = 0, MENU_Y = 45;
	
	private Game game;
	
	/**
	 * Vector holds Merchant's inventory
	 */
	protected Stocks wares;

	/**
	 * Player object that is interacting with the Merchant
	 */
	protected Player player;

	/**
	 * Holds the interactor's total money. Important for transactions
	 */
	protected int playerMon;
	
	private KeyInput kInput;
	
	private NumberSelector numSelect;
	
	private final int TALKING_DISTANCE = 128; 
	private int width, height;
	
	private MerchantHandler mh;

	public Merchant(int x, int y, Game game) {
		super(x, y, Images.merchant);
		this.setTDistance(TALKING_DISTANCE);
		this.game = game;
		
		this.kInput = game.getInterpreter();
		kInput.setState(GameState.HOUSE);
		
		numSelect = game.getNumSelect();
		
		width = game.getWidth();
		height = game.getHeight();
		
		this.player = game.getPlayer();
		
		playerMon = player.getInventory().getMoney();

		// Initialize relevant instance variables
		wares = new Stocks(player, player.getLevel());
	}

	public Merchant(Game game) {
		super(0, 0, Images.merchant);
		this.setTDistance(TALKING_DISTANCE);
		this.game = game;
		
		this.kInput = game.getInterpreter();
		kInput.setState(GameState.HOUSE);	
		
		width = game.getWidth();
		height = game.getHeight();
		
		this.player = game.getPlayer();
		
		playerMon = player.getInventory().getMoney();

		// Initialize relevant instance variables
		wares = new Stocks(player, player.getLevel());
	}
	
	public int converse(Player player) {

		kInput.setState(GameState.GAME_MENU);
		
		player.setStill();

		boolean buying = true;

		String[] buysell = { "BUY", "SELL" };

		String[] sellChoices = { "Weapons", "Armor", "Potions", "Utility", "Ore" };

		String[] buyChoices = { "Weapons", "Armor", "Potions", "Utility", "Ore" };

		// Retrieve player's total amount of money
		playerMon = player.getInventory().getMoney();

		game.displayMenu(buysell, 1, true);

		game.displayMoney();
		
		TextMenu m = game.getTextMenu();

		if (m.getSelected() == 0) {
			buying = true;
			game.displayMenu(buyChoices, 1, true);
		} else if (m.getSelected() == 1) {
			buying = false;
			game.displayMenu(sellChoices, 1, true);
		} else if (m.getSelected() == 2) {
			kInput.setTalking(false);
			return 0;
		}

		if (m.getSelected() == 2) {
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
		
		game.getTextMenu().setVisible(false);
		game.getMoneyText().setVisible(false);
		
		if(game.getInfoText() != null) {
			game.getInfoText().setVisible(false);
		}

		game.newNumSelect();
		
		kInput.setInteracting(false);
		kInput.setState(GameState.HOUSE);
		
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
	 * @param int
	 *            - Amount of Item user would like to buy
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

		player.store(a, quant);
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
	 * @param int
	 *            - Amount of this Item to be sold to the Merchant
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
	 * @param boolean
	 *            - Determines whether user is buying or selling Items
	 */
	public int selection(Pocket pocket, boolean buying) {

		if (!buying) {
			// User is selling Items
			Pocket playerPock = player.getInventory().findPocket(pocket.getType());
			pocket = playerPock;
		}

		if (pocket.isEmpty()) {
			game.displayText("No " + pocket.getName() + "s left");
			game.sleep(100);
			return 0;
		}

		// Display Pocket's contents with prices
		String[] choices = pocket.toPrices(buying);
		game.displayMenu(choices, 2, true);
		
		TextMenu m = game.getTextMenu();

		if (m.getSelected() >= m.numChoices()) {
			game.sleep(100);
			return 0;
		}

		String itemName = pocket.itemNames()[m.getSelected()];
		String amountStr = "0";
		
		numSelect = game.getNumSelect();
		
		numSelect.setVisible(true);
		
		game.addKeyListener(numSelect);
		while (!numSelect.isReady()) {
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
				game.displayText("Cannot afford this");
				return 0;
			}

		} else {
			// User is selling Items
			// Determine if user has enough Items for a feasible transaction
			if (amount > player.getInventory().getQuantity(desired)) {
				game.displayText("You do not have this many items to sell");
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
		
		return 0;

	}

}
