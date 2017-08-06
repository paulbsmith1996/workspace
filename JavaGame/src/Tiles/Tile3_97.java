package Tiles;

import gameobjects.Blank;
import gameobjects.Dirt;
import gameobjects.GoblinBoss;
import gameobjects.IronRock;
import gameobjects.MineFloor;

public class Tile3_97 extends Tile {

	public Tile3_97() {
		super(3, 97);
	}
	
	@Override
	public void create() {
		setArea(new MineFloor(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Blank(), numRows() - 1);
		setRow(new Blank(), 0);
		
		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);
		//setArea(new MineFloor(), numCols() - 1, 3, numCols() - 1, numRows() - 4);
		
		
		setArea(new MineFloor(), numCols() - 1, 3, numCols() - 1, numRows() - 4);
		//setArea(new MineFloor(), 3, 0, 7, 0);
		//setArea(new MineFloor(), 3, numRows() - 1, 7, numRows() - 1);		
		//setArea(new MineFloor(), 0, 3, 0, numRows() - 4);
		
		GoblinBoss firstBoss = new GoblinBoss(17);
		String[] dialogue = {"So you are the one who attacked my henchmen.", 
			"I do not take kindly to those who ruin my plans."};
		firstBoss.setDialogue(dialogue);
		
		String[] deathDialogue = {"Agh! Who are you?",
				"You will regret your actions against the Black Star!"};
		firstBoss.setDeathDialogue(deathDialogue);
		
		setObject(firstBoss, 3, 5);
	}
}
