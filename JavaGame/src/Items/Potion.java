package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class Potion - Defines specific objects of type Consumable.
 * Each potion has a strength and a type
 *
 */
public class Potion extends Consumable {

	/**
	 * Int holding effect of potionå
	 */
	protected int strength;

	/**
	 * String holding potion type
	 */
	protected String PotType;

	/**
	 * Constructor defines type of Item that is Potion
	 */
	public Potion() {
		this.itemType = ItemReference.POTION;
		this.name = "Potion";
		this.consumableType = 0;
	}

	/**
	 * Returns the type of Potion that this is
	 */
	public String getPotType() {
		return this.PotType;
	}

	/**
	 * Returns the strength of this Potion
	 */
	public int getStrength() {
		return strength;
	}

}