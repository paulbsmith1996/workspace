package Tiles;

import cutscene.CutSceneReference;
import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.StoneTablet;
import gameobjects.Water;
import gameobjects.WaterDirt1;
import gameobjects.WaterDirt2;
import gameobjects.WaterDirt3;
import gameobjects.WaterDirt4;
import gameobjects.WaterDirt6;
import resourceloaders.Audio;

public class Tile9_99 extends Tile {

	public Tile9_99() {
		super(9, 99);
	}
	
	@Override
	public void create() {
		
		this.setCutScene(CutSceneReference.introScene);
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
		
		setArea(new WaterDirt4(), 0, 0, 0, 3);
		setArea(new WaterDirt6(), numCols() - 1, 0, numCols() - 1, 3);
		
		setArea(new Water(), 0, 0, numCols() - 1, 1);
		setRow(new WaterDirt2(), 2);
		
		setObject(new WaterDirt1(), 0, 2);
		setObject(new WaterDirt3(), numCols() - 1, 2);
		
		setArea(new Fence(), 0, 4, 0, numRows() - 1);
		setArea(new Fence(), numCols() - 1, 4, numCols() - 1, numRows() - 1);
		
		addBushyTree(numCols() - 4, numRows() - 5);
		addBushyTree(2, numRows() - 5);
		
		StoneTablet introTab = new StoneTablet();
		String[] text = {"You have been left here to serve out your exile.",
				"Should you choose to wander the realm, we shall not stop you",
				"but know that return to our kingdom will be punished by death.",
				"Sorry it has come to this"};
		introTab.setDialogue(text);
		
		setObject(introTab, numCols() / 2, 3);
		
		setMusic(Audio.MUSIC_AMBIENT);
	}
}
