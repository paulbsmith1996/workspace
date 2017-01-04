package Items;

import gameobjects.Creature;

// Paul Baird-Smith 2015

/**
 *
 * Class Restore - Defines Spells of type Restore. Used to heal Player in combat
 *
 */
public class Restore extends Spell {

	Creature owner;

	/**
	 * Constructor defines Restore variables
	 */
	public Restore(Creature player) {
		super(player);
		owner = player;
		this.name = "Restore";
		this.type = "Heal";
		this.manaCost = 50;
		this.cost = 150;
		this.damage = 40;
		this.ID = ItemReference.RESTORE;
	}

	/**
	 * Holds added effects of Spell if any
	 */
	public String addedEffects(Creature other) {
		int addedHeal = owner.getMA() * 10 / 3;
		owner.setHP(owner.getHp() + addedHeal);
		return "Healed for " + (addedHeal + damage) + " HP";
	}

}