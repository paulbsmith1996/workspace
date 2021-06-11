package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class FurLeggings - Type of armor for the legs. Worst Defense.
 *
 */
public class FurLeggings extends Armor {

	/**
	 * Define FurLeggings variables
	 */
	public FurLeggings() {
		this.ID = ItemReference.FURLEGGINGS;
		this.name = "Fur Leggings";
		this.armType = Armament.LEGS;
		this.cost = Prices.FUR_LEGGINGS;
		this.defense = 20;
	}
}