package Items;

import gameobjects.Creature;
// Paul Baird-Smith 2015

public class Sharpen extends Spell {

    Creature owner;

    public Sharpen(Creature player) {
	super(player);
	owner = player;
	this.name = "Sharpen";
	this.type = "Heal";
	this.manaCost = 40;
	this.cost = 110;
	this.damage = 0;
	this.ID = ItemReference.SHARPEN;
    }

    @Override
	public String addedEffects(Creature c) {
	owner.setAP(owner.getAP() + 50);
	return "Attack boosted by 5!";
	//owner.displayMoreStats();
    }

}