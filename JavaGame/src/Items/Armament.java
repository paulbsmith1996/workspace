package Items;
// Paul Baird-Smith 2015

/**
 *
 * Class Armament - Empty class used to organize the different kinds
 * of items that can be equipped by a Creature
 *
 * @author - Paul Baird-Smith
 */

public class Armament extends Item {

	public static final int WEAPON = 0;
	public static final int CHEST = 1;
	public static final int HEAD = 2;
	public static final int HANDS = 3;
	public static final int LEGS = 4;
	public static final int FEET = 5;
	
	// True if Armament is equipped by a Player object
    public boolean equipped = false;
    public int armType;
    
    public int defense, attack;
    
    public Armament() {
    	this.defense = 0;
    	this.attack = 0;
    }
    
    public void setEquipped(boolean b) { this.equipped = b; }
    
    public void setArmType(int type) { this.armType = type; }
    public int getArmType() { return this.armType; }
    
    public int getDefense() { return this.defense; }
    public void setDefense(int defense) { this.defense = defense; }
    
	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	// To override. This applies any extra effect from equipping
    // the Armament
    public void addedEffects() {}

}