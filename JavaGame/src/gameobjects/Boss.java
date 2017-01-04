package gameobjects;
import java.awt.image.BufferedImage;


public class Boss extends NPC {

	private String[] dialogue;
	private String[] deathDialogue;
	
	public static final int GOBLIN = 0;
	
	public Boss(String name, int x, int y, BufferedImage image, int type, int level) {
		super(x, y, 0, 0, name, image);
		String[] text = {"Hello. I am a boss"};
		this.dialogue = text;
		
		this.health = 50 + 70 * level;
		this.maxHP = 50 + 70 * level;
		this.maxMana = 100 + 40 * level;
		this.mana = 100 + 40 * level;
		this.ap = 30 + 10 * level;
		this.dp = 30 + 10 * level;
		this.ma = 30 + 10 * level;
		this.md = 30 + 10 * level;
	}
	
	public void setDialogue(String[] arr) { this.dialogue = arr; }
	public String[] dialogue() { return this.dialogue; }
	
	public void setDeathDialogue(String[] arr) { this.deathDialogue = arr; }
	public String[] deathDialogue() { return this.deathDialogue; }
	
}
