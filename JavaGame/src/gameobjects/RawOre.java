package gameobjects;
import java.awt.image.BufferedImage;

import Items.Ore;

public class RawOre extends Interactable {

	private Ore containedOre;
	private boolean used = false;
	
	public RawOre(int x, int y, Ore containedOre, BufferedImage image) {
		super(x, y, image);
		this.containedOre = containedOre;
		this.interactType = Interactable.ORE;
	}
	
	public RawOre(Ore containedOre, BufferedImage image) {
		super(0,0, image);
		this.containedOre = containedOre;
		this.interactType = Interactable.ORE;
	}
	
	public Ore getOre() { return this.containedOre; }
	
	public boolean isUsed() { return this.used; }
	public void mine() { this.used = true; }
}
