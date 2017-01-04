package gameobjects;
import inventories.Holster;
import inventories.Inventory;

import java.awt.Rectangle;
import java.util.Random;

import misc.Controller;
import resourceloaders.Images;
import Interfaces.PlayerInt;
import Items.Armament;
import Items.Armor;
import Items.Consumable;
import Items.Item;
import Items.Potion;
import Items.Spell;
import Items.SpellBook;
import Items.Utility;
import Items.Weapon;

public class Player extends Creature implements PlayerInt {

	private int side = 0;
	
	public GameObject move(Controller c) {
		setX(getX() + velX);
		setY(getY() + velY);

		// Default is that Creature is not intersecting anything
		GameObject intersecting = null;
		side = 0;

		// Iterate through all GameObjects contained in Controller
		for (GameObject obstacle : c) {
			// Check the GameObject is an Obstacle
			if (obstacle instanceof Obstacle) {

				if (leftBounds().intersects(obstacle.rightBounds())) {

					// Move Creature out of object it is intersecting
					setX(obstacle.getX() + obstacle.getWidth() + 4);

					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 1;
				}

				if (rightBounds().intersects(obstacle.leftBounds())) {

					// Move Creature out of object if intersecting
					setX(obstacle.getX() - getWidth() - 4);

					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 2;
				}

				if (topBounds().intersects(obstacle.bottomBounds())) {

					// Move Creature out of object it intersecting
					setY(obstacle.getY() + obstacle.getHeight() + 4);

					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 3;
				}

				if (bottomBounds().intersects(obstacle.topBounds())) {

					// Move Creature out of object if intersecting
					setY(obstacle.getY() - getHeight() - 4);

					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 4;
				}
			}
		}

		// Return GameObject that creature is currently intersecting
		return intersecting;
	}
	
	public int getSide() { return side; }

	public boolean intersects(NPC g) {
		return getBounds().intersects(g.getBounds());
	}

	public boolean intersects(Rectangle r) {
		return getBounds().intersects(r.getBounds());
	}
	
	public Interactable checkInteraction(Controller c) {
		for(GameObject obj: c) {
			if(obj instanceof Interactable) {
				if(getDistance(obj) <= ((Interactable)obj).getTDistance()) {
					return (Interactable)obj;
				}
			}
		}
		return null;
	}

	/*************** Player Game Logic *************************/

	/**
	 * Holds all exp collected by Player
	 */
	protected int exp;

	/**
	 * Holds the outcome of randomly generated numbers, used for generating
	 * random events.
	 */
	protected int outcome;

	/**
	 * Inventory object that holds all of Player's Items
	 */
	protected Inventory inventory;

	/**
	 * Random object to incorporate randomness
	 */
	protected Random r;

	/**
	 * SpellBook object holding Spells that the Player can cast
	 */
	protected SpellBook book;

	/**
	 * Holster object that holds all Armaments equipped by Player
	 */
	protected Holster holster;

	protected int[] stats;

	/**
	 * Precise constructor for Player using a name, stats, and an Inventory
	 *
	 * pre: stats.length = 7
	 *
	 * @param String
	 *            - Defines name for Player object
	 * @param int[] - Defines stats for the Player object
	 * @param Inventory
	 *            - Gives Player object an Inventory to hold Items
	 */
	public Player(int x, int y, int velX, int velY, 
			String name, int[] stats, Inventory inventory) {
		
		super(x, y, velX, velY, Images.player);
		this.stats = stats;
		

		// Initialize inventory var and book and holster
		this.inventory = inventory;
		this.book = new SpellBook(this);
		this.holster = new Holster(this);

		// Basic defintion of Player attributes
		this.maxHP = this.health = this.stats[0];
		this.maxMana = this.mana = this.stats[1];
		this.ap = this.stats[2];
		this.dp = this.stats[3];
		this.level = this.stats[4];
		this.ma = this.stats[5];
		this.md = this.stats[6];
		this.name = name;

		// Initialize Random object
		r = new Random();
	}
	
	public Inventory getInventory() { return this.inventory; }
	public Holster getHolster() { return this.holster; }
	public SpellBook getBook() { return this.book; }
	public int getLevl() { return this.level; }

	/**
	 * Returns Player's total exp
	 */
	public int getEXP() {
		return exp;
	}

	/**
	 * Allows user to select SpellBook for Player object
	 */
	public void setSpellBook(SpellBook book) {
		this.book = book;
	}

	/**
	 * Returns result of Random generating a new number. Keeps single seed of
	 * Random.
	 */
	public int getOutcome() {
		outcome = r.nextInt();
		return outcome;
	}

	/**
	 * Add an amount of the indicated item to inventory *
	 * 
	 * @param Item
	 *            - Item to be added to the Inventory
	 * @param int - Amount of Item to be added to Inventory
	 */
	public void store(Item item, int quant) {
		for (int x = 0; x < quant; x++) {
			inventory.addItem(item);
		}
	}

	/**
	 * Used to equip an Armament to Player's Holster
	 */
	public String equip(Armament arm) {
		
		Armament curr = holster.equip(arm);
		String notification = "";
		
		// Notify user of change
		if (curr != null) {
			notification += "Removed: " + curr.getName() + "\n";
			store(curr, 1);
		}
		
		if(arm instanceof Weapon) {
			notification += "Your attack has been boosted by " 
					+ ((Weapon) arm).getAttack() + " points\n";
		} else if(arm instanceof Armor) {
			notification += "Your defens has been boosted by " 
					+ ((Armor) arm).getDefense() + " points\n";
		
		}

		return notification + "Equipped: " + arm.getName();
	}

	/**
	 * Prints a list of all the Armaments equipped by the Player
	 */
	public String allEquipped() {
		return this.holster.toString();
	}

	/**
	 * Use indicated item from inventory. Different cases for Consumable and
	 * Armament objects
	 *
	 * @pre: item != null
	 * @post: user uses item, conferring effect
	 */
	public String use(Item item) {

		String notify = "";
		
		// Check inventory contains instance of item
		if (inventory.getQuantity(item) > 0) {

			if (item instanceof Consumable) {
				// Item is a Consumable
				notify = drinkPotion((Potion) item); 
				inventory.removeItem(item);
			} else if (item instanceof Armament) {
				// Item is an Armament
				notify = equip((Armament) item); 
				inventory.removeItem(item);
			} else if (item instanceof Utility) {
				((Utility) item).use();
			}

		} else {
			notify = "No more of this item!";
		}
		
		// Case where inventory contains no instance of item
		return notify;
	}

	/**
	 * See previous method header for more information
	 *
	 * @pre: inventory contains item with name itemName
	 * @post: item used by Player
	 *
	 * @param String
	 *            - name of Item to be used
	 */
	public String use(String itemName) {
		Item desired = inventory.findItem(itemName);
		return use(desired);
	}

	/**
	 * Casts a Spell from Player's SpellBook on specified Creature
	 *
	 * @param Spell
	 *            - Spell from Player's SpellBook to be cast
	 * @param Creature
	 *            - target Creature for cast Spell
	 */
	public int cast(Spell spell, Creature other) {

		// Retrieve mana cost of Spell
		int manaCost = spell.getManaCost();

		// Check Player has sufficient mana
		if (getMana() >= manaCost) {
			// Player does have sufficient mana
			int castResult = book.spellCast(spell, other);
			this.mana -= spell.getManaCost();
			// Nonzero value means successful cast
			return castResult;
		} else {
			// Player does not have sufficient mana
			// Indicate invalid spell cast
			return 0;
		}

	}

	/**
	 * Allows user to search for a Spell to cast using a String equal to the
	 * name of the requested Spell
	 *
	 * @param String
	 *            - Name of Spell to be cast
	 * @param Creature
	 *            - Target of Spell to be cast
	 */
	public int cast(String spell, Creature other) {

		String lowercase = spell.toLowerCase();

		// Search through SpellBook to find correct Spell
		for (int i = 0; i < book.size(); i++) {
			Spell temp = book.elementAt(i);

			if (temp.getName().toLowerCase().equals(lowercase)) {
				cast(temp, other);
				// Nonzero value indicates successful spell cast
				return temp.getManaCost();
			}
		}

		// Debugging code to be removed
		System.out.println("Class Player.cast: No spell cast");
		// Indicates an invalid cast
		return 0;
	}

	/**
	 * Returns a String representation of inventory
	 */
	public String invDisplay() {
		return "\nYou have " + inventory.getMoney() + " coins.\n\n"
				+ inventory.toString();
	}

	/**
	 * Allows user to find an Item in Player's Inventory by name or by instance
	 * of Item
	 */
	public boolean hasItem(String itemName) {
		Item desired = inventory.findItem(itemName);
		return inventory.hasItem(desired);
	}

	/**
	 * Allows user to drink a potion from Player's inventory
	 * 
	 * @pre: potion != null
	 * @post: potion removed from inventory, effects applied to Player
	 *
	 * @param Potion
	 *            - Potion to be consumed by Player
	 */
	public String drinkPotion(Potion potion) {
		
		// String holding type of Potion
		String potType = potion.getPotType();
		// Int holding strength of Potion effect
		int strength = potion.getStrength();

		// Check if Player has instance of Potion
		if (inventory.getQuantity(potion) > 0) {
			// Determine type of Potion
			if (potType.equals("Health")) {
				// Potion is a Health Potion
				if (health + strength < maxHP) {
					health += strength;
					return "Healed " + strength + "HP!";
				} else {
					int healed = maxHP - health;
					health = maxHP;
					return "Healed " + healed + "HP!";
				}
				
			} else if (potType.equals("Mana")) {
				// Potion is a Mana Potion
				if (mana + strength < maxMana) {
					mana += strength;
					return "Restored " + strength + " Mana!";
				} else {
					int restored = maxMana - mana;
					mana = maxMana;
					return "Restored " + restored + " Mana!";
				}

			}

		}
		
		return "You do not have any of this potion!";
	}

	/**
	 * Player gains experiences
	 *
	 * @param int - Amount of exp to be gained by Player
	 */
	public int gainEXP(int exp) {
		// Add exp to running total
		this.exp += exp;

		// Determine if Player has levelled up
		if (this.exp >= (200 * level)) {
			this.exp -= (200 * level);
			//levelUp();
			// Notify if player has levelled up
			return 1;
		}
		
		return 0;
	}

	/**
	 * Track changes in Player's stats as user levels up
	 */
	public String levelUp() {
		// Increment level and stats appropriately
		level++;
		health += 50;
		maxHP += 50;
		maxMana += 30;
		mana += 30;
		ap += 20;
		dp += 20;
		ma += 20;
		md += 20;

		resetStats();
		holster.applyBuffs();
		// Inform user of changes in stats
		//displayAllStats();
		return "Level UP!";
	}

	public void resetStats() {
		int mult = level - 1;
		int manaUsed = maxMana - mana;
		int healthLost = maxHP - health;

		this.maxHP = stats[0];
		maxHP += (50 * mult);

		this.health = maxHP - healthLost;

		this.maxMana = stats[1];
		maxMana += (30 * mult);

		this.mana = maxMana - manaUsed;

		int twice = 20 * mult;

		this.ap = stats[2];
		ap += (twice);

		this.dp = stats[3];
		dp += (twice);

		this.ma = stats[5];
		ma += (twice);

		this.md = stats[6];
		md += (twice);
	}

	public boolean isDead() {
		return health <= 0;
	}

}
