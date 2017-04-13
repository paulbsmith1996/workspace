package cutscene;

import misc.Game;

public class CutSceneLoader {
	
	private static Game game;
	
	public static void initCutSceneLoader(Game g) { game = g; } 
	
	public static void loadScenes() {
		// Add in all the necessary cut scenes
		CutSceneReference.firstScene = new CutScene1(game);
		CutSceneReference.introScene = new IntroCutScene(game);
	}

}
