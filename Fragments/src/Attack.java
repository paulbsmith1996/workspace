/**
 * Wrapper for an intent to attack another creature. This holds 
 * -an actor or initiater of the attack,
 * -a target of the attack, and
 * -the ID of the actual attack being performed or cast
 * 
 * These can all be queried by the user of this class
 * 
 * @author paulbairdsmith
 *
 */
public class Attack {

	// The initiater of the attack
	private Creature actor;
	
	// The target of the attack
	private Creature target;
	
	// Holds the ID of the spell being cast
	private int actionID;
	
	/**
	 * General constructor for an Attack
	 * @param i - The Creature that initiates the attack
	 * @param t - The target Creature of the attack
	 * @param actionID - The ID of the spell being cast on the target Creature
	 */
	public Attack(Creature i, Creature t, int actionID) {
		this.actor = i;
		this.target = t;
		this.actionID = actionID;
	}
	
	// Getters for the fields of the Attack. No need for setters
	public Creature getActor() { return this.actor; }
	public Creature getTarget() { return this.target; }
	public int getAction() { return this.actionID; }
	
}
