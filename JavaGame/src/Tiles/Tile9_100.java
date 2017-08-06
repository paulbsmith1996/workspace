package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.StoneTablet;
import resourceloaders.Audio;
import resourceloaders.AudioPlayer;

public class Tile9_100 extends Tile {

	public Tile9_100() {
		super(9, 100);
	}
	
	@Override
	public void create() {
		setArea(new Dirt(), 0, 0, numCols() - 1, numRows() - 1);
		
		setRow(new Fence(), numRows() - 1);
		setColumn(new Fence(), 0);
		
		setObject(new Fence(), numCols() - 1, 0);
		
		StoneTablet warning = new StoneTablet();
		String[] text = {"There's something up ahead.",
				"It seems to be a group of armored green men", "Are they friendly?"};
		warning.setDialogue(text);
		
		setObject(warning, numCols() / 2 - 1, numRows() - 3);
		
		addBushyTree(2, 5);
		
		setMusic(Audio.MUSIC_AMBIENT);
	}
}
