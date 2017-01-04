package Items;
// Paul Baird-Smith 2015

public class Ore extends Item {

    public int oreType;

    public Ore() {
	this.itemType = ItemReference.ORE;
    }

    public int getOreType() { return this.oreType; }

}