package Tiles;

import gameobjects.Dirt;
import gameobjects.Fence;
import gameobjects.Grass;
import gameobjects.MVillager;
import gameobjects.VillageFloor;

public class Tile11_97 extends Tile{

	public Tile11_97() {
		super(11,97);
	}
	
	@Override
	public void create() {
		setArea(new Grass(), 0, 0, numCols(), numRows());
		setRow(new VillageFloor(), numRows() - 1);
		
		setObject(new Grass(), 0, numRows() - 1);
		setObject(new Fence(), 0, numRows() - 1);
		
		setObject(new Grass(), numCols() - 1, numRows() - 1);
		setObject(new Fence(), numCols() - 1, numRows() - 1);
		
		setRow(new Grass() ,0);
		setRow(new Fence(), 0);
		
		setArea(new VillageFloor(), 6, 2, 6, numRows() - 1);
		setArea(new VillageFloor(), 2, 7, 8, 7);
		setArea(new VillageFloor(), 9, 5, 9, 7);
		
		addMerchant(5, 2);
		
		addHouse(2, 6);
		addHouse(8, 5);
		
		MVillager gateKeeper = new MVillager();
		setObject(gateKeeper, numCols() - 2, 1);
	}
	
}
