package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.GoblinBoss;
import gameobjects.Grass;
import gameobjects.VillageFloor;

public class Tile12_95 extends Tile {

	public Tile12_95() {
		super(12, 95);
	}
	
	@Override
	public void create() {
		setColumn(new Grass(), 0);
		setColumn(new Fence(), 0);
		
		setColumn(new Grass(), numCols() - 1);
		setColumn(new Fence(), numCols() - 1);
		
		setArea(new Dirt(), 1, 0, numCols() - 2, numRows() - 1);
		
		setArea(new VillageFloor(), numCols() / 2 - 1, 2, numCols() / 2 + 1, numRows() - 1);
		
		GoblinBoss firstBoss = new GoblinBoss(5);
		String[] dialogue = {"So you are the one who attacked my henchmen.", 
			"I do not take kindly to those who ruin my plans."};
		firstBoss.setDialogue(dialogue);
		
		String[] deathDialogue = {"Agh! Who are you?",
				"You will regret your actions against the Black Star!"};
		firstBoss.setDeathDialogue(deathDialogue);
		
		setObject(firstBoss, 6, 1);
	}
}
