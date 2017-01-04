package gameobjects;

import resourceloaders.Images;

public class Tella1 extends Interactable{

	public Tella1(int x, int y) {
		super(x, y, Images.tella);
	}
	
	public Tella1() {
		super(Images.tella);
	}
	
	@Override
	public String[] interact() {
		String[] dialogue = {"Hello traveller.",
				"My name is Tella, godess of light and I come to you for help.",
				"I have seen your potential and I know of the greatness you can achieve",
				"Eons ago, we gods were created to maintain the peace in this world",
				"For ages we lived happily aiding the creatures of "
				+ "this world to create, invent, and prosper",
				"But it has been 10 years now since the Black Star has ravaged our home",
				"They robbed us of our powers and have set about using them for "
				+ "selfish and evil reasons",
				"and now we live among you, the mortals, unable to perform our duties",
				"I implore you. You must help our cause if the world is "
				+ "to return to its natural state",
				"Make your way to the next town.", "There, a man named Glare will guide you",
				"Please hurry. I do not know how much longer we will last.",
				"Our powers grow weaker with each passing day."};
		return dialogue;
	}
}
