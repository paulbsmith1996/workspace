package Items;
// Paul Baird-Smith 2015

import gameobjects.Creature;

import java.util.Random;

/**
 *
 * Class Spark - Defines Spells of type Spark. Used to lower enemy's defense
 * point(DP) in combat
 *
 */
public class Spark extends Spell {

	Random r;

	/**
	 * Creature variable representing owner of Spark
	 */
	Creature owner;

	/**
	 * Constructor defines Spell and Item variables for Spark
	 */
	public Spark(Creature player) {
		super(player);
		this.name = "Spark";
		this.type = "Lightning";
		this.manaCost = 30;
		this.cost = 100;
		this.damage = 10;
		this.ID = ItemReference.SPARK;
	}

	/**
	 * Holds added effects applied to enemey's when Spark cast
	 */
	public String addedEffects(Creature other) {
		// Retrieve enemy's DP
		int otherDP = other.getDP();

		r = new Random();
		// Determine true power of Spark
		int power = r.nextInt((owner.getMA()) * 10 / 3);

		// Decrement enemy's DP
		if (otherDP - power >= 1) {
			otherDP -= power;
			other.setDP(otherDP);
		}

		// Inform user of change in stats
		return "Enemy's DP fell by " + power + "!";
	}

}