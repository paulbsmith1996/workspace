package Tiles;

import gameobjects.Blank;
import gameobjects.CounterBLCorner;
import gameobjects.CounterBRCorner;
import gameobjects.CounterDown;
import gameobjects.CounterLeft;
import gameobjects.CounterRight;
import gameobjects.Floor;
import gameobjects.MerchantMat;
import gameobjects.Wizard;
import misc.Game;

public class WizardTile extends HouseTile {

	private Game game;
	
	public WizardTile(Game game) {
		super(HouseReference.WIZARD_1, game);
	}

	@Override
	public void create() {
		setArea(new Floor(), 1, 0, numCols() - 2, numRows() - 1);

		for (int y = 0; y < 2; y++) {
			setObject(new CounterLeft(), 2, y);
			setObject(new CounterRight(), numCols() - 3, y);
		}

		setColumn(new Blank(), 0);
		setColumn(new Blank(), numCols() - 1);

		setRow(new Blank(), numRows() - 1);

		setObject(new Blank(), numCols() - 2, 0);
		setObject(new Blank(), 1, 0);

		for (int x = 3; x < numCols() - 3; x++) {
			setObject(new CounterDown(), x, 2);
		}

		setObject(new CounterBLCorner(), 2, 2);
		setObject(new CounterBRCorner(), numCols() - 3, 2);
		setObject(new Wizard(getGame()), numCols() / 2, 1);
		setObject(new MerchantMat(), numCols() / 2, numRows() - 1);
	}

}
