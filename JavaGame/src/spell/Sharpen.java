package spell;

import gameobjects.Creature;
// Paul Baird-Smith 2015

public class Sharpen extends Spell {

	public Sharpen() {
		//super(player);
		//owner = player;
		this.name = "Sharpen";
		this.type = "Heal";
		this.manaCost = 40;
		this.cost = 110;
		this.damage = 0;
		this.spellID = SpellReference.SHARPEN;
	}

	@Override
	public String addedEffects(Creature owner) {
		int curAP = owner.getAP();
		owner.setAP(curAP + 50);
		return "Attack boosted by 5!";
	}

}