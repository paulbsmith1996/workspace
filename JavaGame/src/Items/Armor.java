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
     * String holding type of armor. e.g. "Boot"
     */
    protected String armorType;

    /**
     * Constructor defines Item as type armor
     */
    public Armor() {
	this.itemType = ItemReference.ARMOR;
    }

    /**
     * Returns defense points of armor Object
     */
    public int getDefense() { return this.defense; }

    /**
     * Returns the type of armor of the instantiated Object
     */
    public String getArmorType() { return this.armorType; }

}