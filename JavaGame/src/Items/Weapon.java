package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class Weapon - Defines a specific kind of Armament that boosts attack points
 * of wearer
 *
 */
public class Weapon extends Armament {

    /**
     * Int holding the total boost to user's attack points
     */
    protected int attack;

    /**
     * Define Item type for Weapon objects
     */
    public Weapon() {
	this.itemType = ItemReference.WEAPON;
    }

    /**
     * Returns amount to boost user's attack points
     */
    public int getAttack() { return this.attack; }

}