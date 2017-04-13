package resourceloaders;
//Paul Baird-Smith 2015

import java.io.IOException;

public class ResourceLoader {

	private static BufferedImageLoader imageLoader = new BufferedImageLoader();

	public static void loadImages() {

		try {
			Images.blank = imageLoader.loadImage("blank.png");

			Images.fence = imageLoader.loadImage("fence.png");
			
			Images.water 	  = imageLoader.loadImage("water.png"); 
			Images.waterDirt1 = imageLoader.loadImage("waterDirt1.png");
			Images.waterDirt2 = imageLoader.loadImage("waterDirt2.png");
			Images.waterDirt3 = imageLoader.loadImage("waterDirt3.png");
			Images.waterDirt4 = imageLoader.loadImage("waterDirt4.png");
			
			Images.stoneWall1 = imageLoader.loadImage("stoneWall1.png");
			Images.stoneWall2 = imageLoader.loadImage("stoneWall2.png");
			Images.stoneWall3 = imageLoader.loadImage("stoneWall3.png");
			Images.stoneWall4 = imageLoader.loadImage("stoneWall4.png");
			Images.stoneWall6 = imageLoader.loadImage("stoneWall6.png");
			Images.stoneWall7 = imageLoader.loadImage("stoneWall7.png");
			Images.stoneWall8 = imageLoader.loadImage("stoneWall2.png");
			Images.stoneWall9 = imageLoader.loadImage("stoneWall9.png");
			
			Images.waterDirt6 = imageLoader.loadImage("waterDirt6.png");
			
			Images.player = imageLoader.loadImage("playerstill.png");
			
			Images.goblin = imageLoader.loadImage("goblinsprite.png");
			Images.wisp = imageLoader.loadImage("wisp.png");
			Images.falkon = imageLoader.loadImage("falkon.png");
			
			Images.goblinBoss = imageLoader.loadImage("goblinboss.png");
			Images.tella = imageLoader.loadImage("tella.png");
			
			Images.healer = imageLoader.loadImage("healer.png");

			Images.grass = imageLoader.loadImage("grass.png");
			Images.dirt = imageLoader.loadImage("dirt.png");
			
			Images.stoneTablet = imageLoader.loadImage("stonetablet.png");
			
			Images.bush = imageLoader.loadImage("bush.png");
			
			Images.bushyTreeTL = imageLoader.loadImage("bushytreetl.png");
			Images.bushyTreeTR = imageLoader.loadImage("bushytreetr.png");
			Images.bushyTreeLL = imageLoader.loadImage("bushytreell.png");
			Images.bushyTreeLR = imageLoader.loadImage("bushytreelr.png");

			Images.mineEntrance = imageLoader.loadImage("mineentrance.png");
			Images.mineFloor = imageLoader.loadImage("minefloor.png");

			Images.ironRock = imageLoader.loadImage("ironrock.png");

			Images.merchantLeft = imageLoader.loadImage("merchantleft.png");
			Images.merchantCenter = imageLoader.loadImage("merchantcenter.png");
			Images.merchantRight = imageLoader.loadImage("merchantright.png");
			Images.merchantMat = imageLoader.loadImage("merchantmat.png");
			Images.floor = imageLoader.loadImage("floor.png");

			Images.merchant = imageLoader.loadImage("merchant.png");

			Images.counterLeft = imageLoader.loadImage("counterleft.png");
			Images.counterRight = imageLoader.loadImage("counterright.png");
			Images.counterDown = imageLoader.loadImage("counterdown.png");
			Images.counterBLCorner = imageLoader
					.loadImage("counterBLcorner.png");
			Images.counterBRCorner = imageLoader
					.loadImage("counterBRcorner.png");
			Images.wall = imageLoader.loadImage("wall.png");

			Images.houseDoor = imageLoader.loadImage("housedoor.png");
			Images.houseFloor = imageLoader.loadImage("housefloor.png");
			Images.houseLeft = imageLoader.loadImage("houseleft.png");
			Images.houseRight = imageLoader.loadImage("houseright.png");

			Images.maleVillager = imageLoader.loadImage("malevillager.png");

			Images.villageFloor = imageLoader.loadImage("villagefloor.png");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadSounds() {
		// Sounds will be added here
	}
	
	public static void loadMusic() {
		AudioPlayer.addMusic(Audio.MUSIC_AMBIENT, "music.ogg");
		AudioPlayer.addMusic(Audio.BATTLE_MUSIC, "battle.ogg");
		AudioPlayer.addMusic(Audio.BOSS_MUSIC, "bossbattle.ogg");
		AudioPlayer.addMusic(Audio.DREAM_MUSIC, "dream.ogg");
		AudioPlayer.addMusic(Audio.HEALING_SOUND, "healingsound.ogg");
		AudioPlayer.addMusic(Audio.INTRO, "intro.ogg");
	}

}
