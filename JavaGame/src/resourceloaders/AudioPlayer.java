package resourceloaders;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {

	private static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	
	private static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	private static Music playing = null;
	public static Music wasPlaying = null;
	private static boolean musicPlaying = false;
	
	private static boolean musicOn = true;
	
	public static boolean isPlaying() { return musicPlaying; }
	
	public static boolean musicOn() { return musicOn; }
	public static void setMusic(boolean b) { musicOn = b; }
	
	public static void addSound(String key, String path) {
		try {
			soundMap.put(key, new Sound(ResourceReference.SOUND_LOCATION + path));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void addMusic(String key, String path) {
		try {
			musicMap.put(key, new Music(ResourceReference.MUSIC_LOCATION + path));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	
	public static void playSound(String key) {
		soundMap.get(key).play();
	}
	
	// Plays the Music with the name key for "length" ms
	public static void playLimited(String key, int length) {
		if (musicOn) {
			Music toPlay = musicMap.get(key);
			
			if (!musicPlaying || toPlay != playing) {
				toPlay.play();
			}
			
			wasPlaying = playing;
			playing = toPlay;
			
			int time = 0;
			while(time < length) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				time++;
			}
			
			musicPlaying = true;
		}
	}
	
	public static void playPrev() {
		wasPlaying.loop();
		
		playing = wasPlaying;
	}
	
	public static void playMusic(String key) {
		if (musicOn) {
			Music toPlay = musicMap.get(key);
			
			if (!musicPlaying || toPlay != playing) {
				toPlay.loop();
			}
			
			wasPlaying = playing;
			playing = toPlay;
			musicPlaying = true;
		}
		
	}
	
	public static void fade(int duration, int endVolume, boolean stopAfterFade) {
		if(musicOn && playing != null) {
			playing.fade(duration, endVolume, stopAfterFade);
		}
	}
	
	public static void setVolume(int volume) {
		if(musicOn && playing != null) {
			playing.setVolume(volume);
		}
	}
	
	public static float getVolume() {
		if(playing != null) {
			return playing.getVolume();
		}
		return 0;
	}
	
	public static void stopPlaying() {
		if(playing != null) {
			playing.stop();
		}
		
		musicPlaying = false;
	}
	
}
