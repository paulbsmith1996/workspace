package Items;
// Paul Baird-Smith 2015

public class Pickaxe extends Utility {

    public Pickaxe() {
	this.ID = ItemReference.PICKAXE;
	this.name = "Pickaxe";
	this.cost = 70;
	setUses(3);
    }

}