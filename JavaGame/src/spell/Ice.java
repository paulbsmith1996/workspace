package spell;

// Paul Baird-Smith 2015

import gameobjects.Creature;

import java.util.Random;

/**
 *
 * Class Ice - Defines Ice Spell, which does less damage but also reduces the
 * enemy's Attack Points (AP)
 *
 */
public class Ice extends Spell {

	Random r;
	
	private String description = "An introductory frost spell that "
			+ "slightly lowers the target's physical attack points.";

	/**
	 * Define Ice variables
	 */
	public Ice() {
		this.name = "Ice";
		this.type = "Frost";
		this.manaCost = 30;
		this.cost = 100;
		this.damage = 10;
		this.spellID = SpellReference.ICE;
		
		this.setDescription(description);
	}

	/**
	 * Added effects to be cast to the Creature if any
	 *
	 * @param Creature
	 *            - Creature that Spell will be cast on
	 */
	@Override
	public String addedEffects(Creature other) {
		// Retrieve castee's current AP
		int otherAP = other.getAP();

		r = new Random();

		// Determine strength of Spell
		// int power = 0;
		// if (owner.getMA() >= 3) {
		// power = r.nextInt(owner.getMA() * 10 / 3);
		// }

		int power = otherAP / 8 + r.nextInt(otherAP / 4);

		otherAP -= power;
		other.setAP(otherAP);

		return ("Enemy's AP fell by " + power + "!");
	}

}