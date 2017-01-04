package gameobjects;
import java.awt.image.BufferedImage;


public class Interactable extends Obstacle {
	
	private int talkingDistance = 64;
	private String[] dialogue;
	
	protected int interactType;
	
	private boolean checked = true;
	
	public static final int FRIENDLY = 0;
	public static final int TOREAD = 1;
	public static final int CHEST = 2;
	public static final int ORE = 3;
	
	public Interactable (int x, int y, BufferedImage image) {
		super(x, y, image);
	}
	
	public int getInteractType() { return this.interactType; }
	
	public void setTDistance(int distance) { this.talkingDistance = distance; }
	public int getTDistance() { return this.talkingDistance; }
	
	public Interactable(BufferedImage image) {
		super(0,0, image);
	}
	
	// To override for specific interactable object
	public String[] interact() {
		return dialogue;
	}
	
	public void setDialogue(String[] strArr) { this.dialogue = strArr; }
	
	public void setChecked(boolean b) { this.checked = b; }
	public boolean isChecked() { return this.checked; }
	
}
