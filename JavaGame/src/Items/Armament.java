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

	// True if Armament is equipped by a Player object
    public boolean EQUIPPED = false;
    
	// To override. This applies any extra effect from equipping
    // the Armament
    public void addedEffects() {}

}