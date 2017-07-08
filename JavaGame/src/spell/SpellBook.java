package spell;
// Paul Baird-Smith 2015

import java.util.Vector;

import gameobjects.Creature;

/**
 *
 * Class SpellBook - Implemented as a Vector<Spell>. Each Player object holds
 * instance of SpellBook. Contains all Spells that can be cast by owner of
 * SpellBook
 *
 */
public class SpellBook extends Vector<Spell> {

	/**
	 * Variable represents owner of the SpellBook
	 */
	protected Creature owner;

	/**
	 * Constructor initializes owner of SpellBook object
	 */
	public SpellBook(Creature player) {
		this.owner = player;
	}

	/**
	 * Casts specified Spell on speicified Creature, applying listed effects and
	 * damage
	 *
	 * @param Spell
	 *            - Spell to cast on the requested Creature
	 * @param Creature
	 *            - Target of the Spell
	 */
	public int spellCast(Spell spell, Creature other) {
		return spell.cast(owner, other);
	}

	/**
	 * Allows user to check if SpellBook contains the Spell with the specified
	 * name
	 * 
	 *
	 * @param String
	 *            - Name of Spell to check for in SpellBook
	 */
	public boolean contains(String spellName) {

		String lowercase = spellName.toLowerCase();

		// Read through Spells contained in SpellBook
		for (int i = 0; i < size(); i++) {
			Spell temp = elementAt(i);

			// Appropriate Spell found
			if (temp.getName().toLowerCase().equals(lowercase)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns a String representing the names and mana costs of all the Spells
	 * contained in the SpellBook object
	 */
	public String toString() {
		StringBuffer spells = new StringBuffer();

		for (int i = 0; i < size(); i++) {
			Spell temp = elementAt(i);
			spells.append(temp.getName() + " Costs " + temp.getManaCost()
					+ " mana \n");
		}

		// If user has no Spells, inform them
		if (size() == 0) {
			return "You have no spells yet.";
		}

		return spells.toString();
	}
	
	public String[] getSpells() {
		String[] allSpells = new String[size()];
		int i = 0;
		for(Spell s: this) {
			allSpells[i] = s.name;
			i++;
		}
		
		return allSpells;
	}

}