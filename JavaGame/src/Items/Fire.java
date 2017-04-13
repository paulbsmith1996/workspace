package Items;

import gameobjects.Creature;

// Paul Baird-Smith 2015

/**
 *
 * Class Fire - Class describing the Fire Spell, which deals more damage than
 * other spells.
 *
 */
public class Fire extends Spell {

	/**
	 * Define Fire variables
	 *
	 * @param Creature
	 *            - owner of the Fire Spell
	 */
	public Fire(Creature player) {
		super(player);
		this.name = "Fire";
		this.type = "Flame";
		this.manaCost = 30;
		this.cost = 70;
		this.damage = 100;
		this.ID = ItemReference.FIRE;
	}

	/**
	 * Holds additional effects to cast on Creature if any
	 *
	 * @param Creature
	 *            - Creature to be casted on
	 */
	public String addedEffects(Creature other) {
		// No added effects for fire
		return "";
	}

}
