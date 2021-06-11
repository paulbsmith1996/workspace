package Items;

import gameobjects.Player;

// Paul Baird-Smith 2015

public class ClothRobe extends Armament {

	// Represents owner of ClothRobe object
	protected Player owner;

	/**
	 * Constructor assigns appropriate values to each variable belonging to an
	 * Item
	 * 
	 * @param p
	 *            - owning player
	 */
	public ClothRobe(Player p) {
		owner = p;
		this.ID = ItemReference.CLOTHROBE;
		this.name = "Cloth Robe";
		this.armType = Armament.CHEST;
		this.cost = Prices.CLOTH_ROBE;
		// Relatively low defense, but very good addedEffects
		this.defense = 30;
	}

	@Override
	public void addedEffects() {

		// Here, goal is to increase owner's maxMana stat by 1/3 of its
		// current total

		// Determine amount of mana already used
		int manaUsed = owner.getMaxMana() - owner.getMana();

		// Increase maxMana stat by appropriate amount
		int curMaxMana = owner.getMaxMana();
		owner.setMaxMana(curMaxMana + curMaxMana / 3);
		
		// Update owner's current mana
		owner.setMana(owner.getMaxMana() - manaUsed);

		// If object has not already been equipped by owner,
		// inform owner of status change
		if (!this.equipped) {
			System.out.println("Mana increased by " + (owner.getMaxMana() / 3)
					+ " points");
		}

		// Also increase owner's magic attack and magic defense
		owner.setMA(owner.getMA() + 10);
		owner.setMD(owner.getMD() + 10);
	}

}