package Items;

// Paul Baird-Smith

/**
 *
 * Class Armor - Defines objects that can be equipped that will boost defense
 * points of Creature equipping object
 *
 */

public class Armor extends Armament {

	/**
	 * int holding defense value of armor Object
	 */
	protected int defense;

	/**
	 * Constructor defines Item as type armor
	 */
	public Armor() {
		this.itemType = ItemReference.ARMOR;
	}
}