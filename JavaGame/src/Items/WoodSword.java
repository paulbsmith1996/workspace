package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class WoodSword - Defines objects of class Weapon that boost attack points by
 * smallest amount possible
 *
 */
public class WoodSword extends Weapon {

	/**
	 * Define Item and Weapon variables for WoodSword objects
	 */
	public WoodSword() {
		this.ID = ItemReference.WOODSWORD;
		this.name = "Wood Sword";
		this.cost = Prices.WOOD_SWORD;
		this.attack = 30;
	}

}