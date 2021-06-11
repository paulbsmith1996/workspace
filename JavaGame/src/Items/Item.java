package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class Item - Defines all kinds of Objects that can be held in an Inventory
 *
 */
public class Item {

	/**
	 * Holds the type of Item that the Object is
	 */
	protected int itemType;

	/**
	 * Holds name of Item object
	 */
	protected String name;

	/**
	 * Holds unique ID code for Item
	 */
	protected int ID;

	/**
	 * Holds cost of item for a Creature/Player to buy it
	 */
	protected int cost;

	/**
	 * Returns the type if Item that this is
	 */
	public int getItemType() {
		return this.itemType;
	}

	/**
	 * Returns the name of this Item
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the unique ID for the Item object
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Returns the cost of the item for the Player/Creature
	 */
	public int getCost() {
		return this.cost;
	}

}