package spell;

import java.util.HashMap;

import gameobjects.Player;

public class SpellMap extends HashMap<String, Spell> {

	public SpellMap(Player player) {
		this.put("Fire", new Fire());
		this.put("Ice", new Ice());
		this.put("Spark", new Spark());
		this.put("Sharpen", new Sharpen());
		this.put("Restore", new Restore());
	}
}
