package spell;

import gameobjects.Creature;

// Paul Baird-Smith 2015

/**
 *
 * Class Fire - Class describing the Fire Spell, which deals more damage than
 * other spells.
 *
 */
public class Fire extends Spell {

	private String description = "An introductory fire spell that causes more "
			+ "damage to the target than other spells.";
	
	/**
	 * Define Fire variables
	 *
	 * @param Creature
	 *            - owner of the Fire Spell
	 */
	public Fire() {
		this.name = "Fire";
		this.type = "Flame";
		this.manaCost = 30;
		this.cost = 70;
		this.damage = 100;
		this.spellID = SpellReference.FIRE;
		
		setDescription(description);
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
