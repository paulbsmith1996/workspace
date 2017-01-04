package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class FurCoat - Type of armor for chest. Worst defense.
 *
 */
public class FurCoat extends Armor {

    /**
     * Define FurCoat variables
     */
    public FurCoat() {
	this.ID = ItemReference.FURCOAT;
	this.name = "Fur Coat";
	this.armorType = "Chest";
	this.cost = 100;
	this.defense = 50;
    }
}