package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class StoneSword - Defines Weapons of class StoneSword. Deals extra damage
 * when equipped by a Creature
 *
 */
public class StoneSword extends Weapon {

    /**
     * Define Item and Weapon variables for StoneSword
     */
    public StoneSword() {
	this.ID = ItemReference.STONESWORD;
	this.name = "Stone Sword";
	this.attack = 50;
	this.cost = 150;
    }

}