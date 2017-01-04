package Tiles;

import cutscene.CutSceneReference;
import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;

public class Tile12_93 extends Tile {

	public Tile12_93() {
		super(12, 93);
	}
	
	@Override
	public void create() {
		setColumn(new Grass(), numCols() - 1);
		setColumn(new Fence(), numCols() - 1);
		
		setRow(new Grass(), 0);
		setRow(new Fence(), 0);
		
		setArea(new Dirt(), 0, 1, numCols() - 2, numRows() - 1);
		
		setObject(new Grass(), 0, numRows() - 1);
		setObject(new Fence(), 0, numRows() - 1);
		
		setCutScene(CutSceneReference.firstScene);
	}
}
