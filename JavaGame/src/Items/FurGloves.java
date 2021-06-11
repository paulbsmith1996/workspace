package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class FurGloves - Type of armor for the hands. Worst defense
 *
 */
public class FurGloves extends Armor {

	/**
	 * Define Fur Gloves variables
	 */
	public FurGloves() {
		this.ID = ItemReference.FURGLOVES;
		this.name = "Fur Gloves";
		this.armType = Armament.HANDS;
		this.cost = Prices.FUR_GLOVES;
		this.defense = 10;
	}
}