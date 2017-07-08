package spell;

import gameobjects.Creature;

// Paul Baird-Smith 2015

/**
 *
 * Class Restore - Defines Spells of type Restore. Used to heal Player in combat
 *
 */
public class Restore extends Spell {

	/**
	 * Constructor defines Restore variables
	 */
	public Restore() {
		//super(player);
		//owner = player;
		this.name = "Restore";
		this.type = "Heal";
		this.manaCost = 50;
		this.cost = 150;
		this.damage = 40;
		this.spellID = SpellReference.RESTORE;
	}

	/**
	 * Holds added effects of Spell if any
	 */
	public String addedEffects(Creature owner) {
		int addedHeal = owner.getMA() * 10 / 3;
		owner.setHP(owner.getHp() + addedHeal);
		return "Healed for " + (addedHeal + damage) + " HP";
	}

}