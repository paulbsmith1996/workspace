package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class FurCap - Type of Armor worn on head. Worse defense.
 *
 */
public class FurCap extends Armor {

	/**
	 * Define FurCap variables
	 */
	public FurCap() {
		this.ID = ItemReference.FURCAP;
		this.name = "Fur Cap";
		this.armType = Armament.HEAD;
		this.cost = Prices.FUR_CAP;
		this.defense = 20;
	}

}
