package gameobjects;

import Enums.GameState;
import components.TextMenu;
import misc.Game;
import misc.KeyInput;
import resourceloaders.Images;
import spell.Fire;
import spell.Ice;
import spell.Spell;
import spell.SpellBook;

public class Wizard extends Interactable {

	private final int MENU_X = 0, MENU_Y = 45;

	private Game game;

	/**
	 * Vector holds Merchant's inventory
	 */
	protected SpellBook sb;

	/**
	 * Player object that is interacting with the Merchant
	 */
	protected Player player;

	/**
	 * Holds the interactor's total money. Important for transactions
	 */
	protected int playerMon;

	private KeyInput kInput;

	private final int TALKING_DISTANCE = 128;
	private int width, height;

	public Wizard(int x, int y, Game game) {
		super(x, y, Images.merchant);
		this.setTDistance(TALKING_DISTANCE);
		this.game = game;

		this.kInput = game.getInterpreter();
		kInput.setState(GameState.HOUSE);

		width = game.getWidth();
		height = game.getHeight();

		this.player = game.getPlayer();

		playerMon = player.getInventory().getMoney();

		// Initialize relevant instance variables
		sb = new SpellBook(game.getPlayer());
		populateSpells();
	}

	public Wizard(Game game) {
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
		sb = new SpellBook(game.getPlayer());
		populateSpells();
	}

	public int converse(Player player) {

		kInput.setState(GameState.GAME_MENU);

		player.setStill();
		
		game.displayText("Hello traveller. Welcome to my tower.");
		game.displayText("Would you like to purchase some spells?");

		// Retrieve player's total amount of money
		playerMon = player.getInventory().getMoney();

		game.displayMenu(sb.getSpells(), 3, true);

		game.displayMoney();

		TextMenu m = game.getTextMenu();

		String spellName = m.getSelectedOptionName();
		Spell selectedSpell = game.getSpellMap().get(spellName);

		game.displayText(selectedSpell.getDescription());
		String[] buysell = { "BUY " + spellName + " for " + selectedSpell.getCost() + " gold?", "CANCEL" };
		game.displayMenu(buysell, 1, true);

		m = game.getTextMenu();

		if (m.getSelected() == 0) {
			
			SpellBook playerBook = player.getBook();
			boolean inBook = false;
			
			for(Spell s: playerBook) {
				if(s.getName().equals(spellName)) {
					inBook = true;
				}
			}
			
			if(inBook) {
				game.displayText("You already have this spell!");
			} else if (playerMon >= selectedSpell.getCost()) {
				buy(selectedSpell);
			} else {
				game.displayText("Cannot afford " + spellName);
			}
		} else {
			return 0;
		}

		game.getTextMenu().setVisible(false);
		game.getMoneyText().setVisible(false);
		game.getInfoText().setVisible(false);

		game.newNumSelect();

		kInput.setInteracting(false);
		kInput.setState(GameState.HOUSE);

		return 0;
	}

	/**
	 * Returns a String representation of Merchant's inventory
	 */
	public String toString() {
		return sb.toString();
	}

	/**
	 * Used for the interactor to buy items from the Merchant
	 *
	 * @param Item
	 *            - Item that user would like to buy
	 * @param int
	 *            - Amount of Item user would like to buy
	 */
	public void buy(Spell spell) {

		// Calculate total cost of transaction first to see if possible
		int totalCost = spell.getCost();

		// Remove correct sum of money from user's Inventory
		player.getInventory().removeMoney(totalCost);
		playerMon -= totalCost;

		// Remove correct number of items from Merchant's Inventory
		sb.remove(spell);

		player.getBook().add(spell);
	}

	public void populateSpells() {
		Player player = game.getPlayer();
		
		if(player.level < 5) {
			sb.add(new Fire());
			sb.add(new Ice());
		}
	}

}
