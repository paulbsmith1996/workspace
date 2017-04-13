package Items;
// Paul Baird-Smith 2015

import gameobjects.Creature;

import java.util.Random;

/**
 *
 * Class Spell - Defines a special kind of Item. Spells can be cast and strength
 * depends on caster. Special variables for Spell class
 *
 */
public class Spell extends Item {

	/**
	 * Holds type of this Spell
	 */
	protected String type;

	/**
	 * Holds cost in Mana for casting Spell
	 */
	protected int manaCost;

	/**
	 * Holds raw damage value for spell when cast
	 */
	protected int damage;
	protected int realDamage = 0;

	/**
	 * Variable defining owner or caster of Spell
	 */
	protected Creature caster;

	/**
	 * New Random object for probability
	 */
	protected Random r;
	
	private final int MARGIN_MULTIPLIER = 4;
	private final int MARGIN_OF_ERROR = 40;
	private final int MIN_DAMAGE = 10;

	/**
	 * Initialize type and owner
	 */
	public Spell(Creature player) {
		this.caster = player;
		this.itemType = ItemReference.SPELL;
	}

	/**
	 * Returns name of Spell
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns spell type of Spell
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Returns the cost, in Mana, of Spell
	 */
	public int getManaCost() {
		return this.manaCost;
	}

	/**
	 * Returns raw damage value of Spell when cast
	 */
	public int getDamage() {
		return this.damage;
	}
	
	public int getRealDamage() {
		return realDamage;
	}

	/**
	 * Allows user to cats this Spell on another Creature
	 *
	 * @param Creature
	 *            - Creature that Spell is cast on
	 */
	public int cast(Creature other) {
		// Determine type of Spell
		if (!this.type.equals("Heal")) {
			// Spell is not healing Spell
			// Retrieve attack and defense against magic of respective Creatures
			int attack = caster.getMA();
			int defense = other.getMD();

			r = new Random();
			// Calculate random number less than stated attack/defense
			int true_attack = attack - MARGIN_OF_ERROR / 2 + r.nextInt(MARGIN_OF_ERROR);
			int true_damage = 3 * getDamage() / 4 + r.nextInt(getDamage() / 2);
			int true_defense = defense - MARGIN_OF_ERROR / 2 + r.nextInt(MARGIN_OF_ERROR);
			
			// Deal calculated damage to target
			if (true_attack > true_defense) {
				// Enemy is hit by Spell
				realDamage = true_attack - true_defense + true_damage;
				return 1;
			} else {
				// Enemy was not affected by Spell
				realDamage = MIN_DAMAGE;
				return 1;
			}

		} else {
			// Spell is a healing Spell

			caster.setHP(caster.getHp() + getDamage());
			//addedEffects(caster);

			// Determine if added health exceeds maximum health
			// and add appropriate amount of HP to user
			if (caster.getHp() > caster.getMaxHP()) {
				caster.setHP(caster.getMaxHP());
			}
			return 3;
		}
	}

	/**
	 * Method to be overridden by specific Spell addedEffects method
	 */
	public String addedEffects(Creature other) {
		// Template method to be overridden
		return "";
	}

}