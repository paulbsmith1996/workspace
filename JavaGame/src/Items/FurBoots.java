package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class FurBoots - Type of Armor worn on feet. Worst defense.
 *
 */
public class FurBoots extends Armor {

    /**
     * Define FurBoots variables
     */
    public FurBoots() {
	this.ID = ItemReference.FURBOOTS;
	this.name = "Fur Boots";
	this.armorType = "Boot";
	this.cost = 30;
	this.defense = 20;
    }

}
