package cutscene;

import misc.Game;

public class CutSceneLoader {
	
	private Game game;
	
	public CutSceneLoader(Game game) {
		this.game = game;
	}
	
	public void loadScenes() {
		// Add in all the necessary cut scenes
		CutSceneReference.firstScene = new CutScene1(game);
	}

}
