package inventories;

import gameobjects.Player;
import Items.Armament;
import Items.Armor;
import Items.Weapon;

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
		equipped = new Armament[10];
	}

	/**
	 * Used to equip new Armament to Holster. Removes currently equipped
	 * Armament of same armor type and replaces it by Armament Object
	 *
	 * @param Armament
	 *            - Armament Obect to be equipped in Holster
	 */
	public Armament equip(Armament arm) {

		arm.EQUIPPED = false;
		// Armament var holding currently equipped Armament
		Armament curr = null;
		// Holds current buff provided by equipped Armament
		int currBuff = 0;

		if (arm instanceof Weapon) {
			// Armament to be equipped is a Weapon Object
			// Slot 0 allocated to Weapon Armaments
			curr = equipped[0];

			if (curr != null) {
				// Currently equipped Weapon not null
				// Retrieve current buff of Weapon
				currBuff = ((Weapon) curr).getAttack();
			}

			// Replace with new Armament Object
			equipped[0] = arm;
			owner.setAP(owner.getAP() - currBuff + ((Weapon) arm).getAttack());

		} else if (arm instanceof Armor) {
			// Armament to be equipped is an Armor Object

			// Int holding type of Armor to be equipped. Not set to 0 to
			// avoid imprecisions leading to removal of Weapon
			int armorNum = 100;
			String typeArmor = ((Armor) arm).getArmorType();

			// Define armorNum to correspond with armor type
			if (typeArmor.equals("Boot")) {
				armorNum = 1;
			} else if (typeArmor.equals("Legging")) {
				armorNum = 2;
			} else if (typeArmor.equals("Chest")) {
				armorNum = 3;
			} else if (typeArmor.equals("Helmet")) {
				armorNum = 4;
			} else if (typeArmor.equals("Glove")) {
				armorNum = 5;
			}

			// Retrueve currently equipped Armament in appropriate slot
			curr = equipped[armorNum];

			if (curr != null) {
				currBuff = ((Armor) curr).getDefense();
			}

			// Replace with new Armor Object and remove old buff from wearer
			equipped[armorNum] = arm;
			owner.setDP(owner.getDP() - currBuff + ((Armor) arm).getDefense());
		}

		owner.resetStats();
		applyBuffs();
		arm.EQUIPPED = true;

		// Return old Armament Object to store it in Inventory
		return curr;

	}

	public void applyBuffs() {
		for (int i = 0; i < equipped.length; i++) {
			Armament temp = equipped[i];
			if (temp != null) {
				temp.addedEffects();
				if (temp instanceof Weapon) {
					owner.setAP(owner.getAP() + ((Weapon) temp).getAttack());
				} else if (temp instanceof Armor) {
					owner.setDP(owner.getDP() + ((Armor) temp).getDefense());
				}

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