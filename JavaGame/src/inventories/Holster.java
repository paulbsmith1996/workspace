package inventories;

import gameobjects.Player;
import Items.Armament;

// Paul Baird-Smith 2015

/**
 *
 * Class Holster - Holds all Armament objects currently equipped by the player
 * owning the Holster Object
 *
 */
public class Holster {

	/**
	 * Array holding all currently equipped armaments
	 */
	protected Armament[] equipped;
	
	protected final int HOLSTER_SIZE = 10;

	/**
	 * Variable defining owner of Holster Object
	 */
	protected Player owner;

	/**
	 * Initializes owner and equipped array
	 *
	 * @param Player
	 *            - Owner of the Holster Object
	 */
	public Holster(Player player) {
		this.owner = player;
		equipped = new Armament[HOLSTER_SIZE];
	}

	/**
	 * Used to equip new Armament to Holster. Removes currently equipped
	 * Armament of same armor type and replaces it by Armament Object
	 *
	 * @param Armament
	 *            - Armament Obect to be equipped in Holster
	 */
	public Armament equip(Armament arm) {

		arm.setEquipped(false);
		
		int type = arm.getArmType();
		
		// Armament var holding currently equipped Armament
		Armament curr = this.equipped[type];
		this.equipped[type] = arm;
		
		
		int deltaA = 0;
		int deltaD = 0;
		
		if(curr == null) {
			deltaA = arm.getAttack();
			deltaD = arm.getDefense();
		} else {
			deltaA = arm.getAttack() - curr.getAttack();
			deltaD = arm.getDefense() - curr.getDefense();
		}
		
		owner.setAP(owner.getAP() + deltaA);
		owner.setDP(owner.getDP() + deltaD);

		owner.resetStats();
		applyBuffs();
		arm.setEquipped(true);

		// Return old Armament Object to store it in Inventory
		return curr;

	}

	public void applyBuffs() {
		for (int i = 0; i < equipped.length; i++) {
			Armament temp = equipped[i];
			if (temp != null) {
				temp.addedEffects();
			}
		}

	}

	/**
	 * Returns a String representation of the Holster Object
	 */
	public String toString() {

		StringBuffer s = new StringBuffer();

		for (int i = 0; i < equipped.length; i++) {
			if (equipped[i] != null) {
				s.append(equipped[i].getName() + "\n");
			}
		}

		if (!s.toString().equals("")) {
			return s.toString();
		} else {
			return "No items equipped!";
		}

	}
}