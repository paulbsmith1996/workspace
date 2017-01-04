package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class ManaPotion - Defines a kind of Potion that restore's a Creature's
 * mana by 10 points
 *
 */
public class ManaPotion extends Potion {

    /**
     * Define ManaPotion variables
     */
    public ManaPotion() {
	this.PotType = "Mana";
	this.name = "Mana Potion";
	this.strength = 100;
	this.ID = ItemReference.MANAPOTION;
	this.cost = 50;
    }
    
}