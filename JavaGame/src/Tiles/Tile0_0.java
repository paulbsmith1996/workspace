package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Goblin;
import gameobjects.Grass;




public class Tile0_0 extends Tile{

	public Tile0_0() {
		super(0,0);
	}
	
	public void create() {
		for(int x = 0; x < numCols(); x++) {
			setObject(new Grass(), x, 0);
			setObject(new Fence(), x, 1);
			for(int y = 2; y < numRows(); y++) {
				setObject(new Dirt(), x, y);
			}
		}
		
		for(int y = 1; y < numRows(); y++) {
			setObject(new Grass(), 0, y);
			setObject(new Fence(), 1, y);
		}
		
		addMob(new Goblin(100,100,2,2,1));
		addMob(new Goblin(100,200,-2,1,1));
		
	}
}
