package Items;
// Paul Baird-Smith 2015

public class Utility extends Item {

    protected int usesLeft;
    protected int totalUses;

    public Utility() {
	this.itemType = ItemReference.UTILITY;
    }

    public void setUses(int uses) { 
	this.totalUses = uses; 
	this.usesLeft = uses;
    }
    public int getTotalUses() { return this.totalUses; }

    public int getUsesLeft() { return this.usesLeft; } 

    public int use() { 
	if(usesLeft > 0) {
	    usesLeft--;
	    System.out.println("This " + getName() + " has " + getUsesLeft() + "/" + totalUses + " uses left");
	    return 1;
	} else {
	    System.out.println("No more uses left for this item");
	    return 0;
	}
    }
    
    public void repair() {
	usesLeft = totalUses;
    }

}
