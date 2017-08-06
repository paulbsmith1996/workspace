package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class HealthPotion - Defines Potion that heals user for 5 HP
 *
 */
public class HealthPotion extends Potion {

	/**
	 * Define HealthPotion variables
	 */
	public HealthPotion() {
		this.PotType = "Health";
		this.name = "Health Potion";
		this.strength = 500;
		this.ID = ItemReference.HEALTHPOTION;
		this.cost = Prices.HEALTH_POTION;
	}

}