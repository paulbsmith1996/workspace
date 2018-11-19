import java.io.FileNotFoundException;
import java.util.HashMap;

public class Spells {

	protected static final HashMap<Integer, HashMap<FragmentType,Integer>> SPELL_COSTS;
	protected static final HashMap<Integer, int[]> SPELL_STATS;
	protected static final HashMap<String, Integer> SPELL_NAMES_TO_IDS;
	protected static final HashMap<Integer, String> SPELL_IDS_TO_NAMES;
	
	protected static final int AQUOS_TYPE = 0;
	protected static final int AERO_TYPE = 1;
	protected static final int CHRONO_TYPE = 2;
	protected static final int CORPUS_TYPE = 3;
	protected static final int ETHER_TYPE = 4;
	protected static final int PSYCHE_TYPE = 5;
	protected static final int PYRO_TYPE = 6;
	protected static final int TELE_TYPE = 7;
	protected static final int TERRA_TYPE = 8;
	protected static final int VOLT_TYPE = 9;
	protected static final int VOLUNTARY_TYPE = 10;
	
	static {
			try {
				SpellLoader.loadSpells();
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Spells: Could not load spell file");
			}
			SPELL_COSTS = SpellLoader.spellCosts;
			SPELL_STATS = SpellLoader.spellStats;
			SPELL_NAMES_TO_IDS = SpellLoader.spellNamesToIDs;
			SPELL_IDS_TO_NAMES = SpellLoader.spellIDsToNames;
	}
	
	public static HashMap<FragmentType, Integer> getCost(String spellName) {
		return SPELL_COSTS.get(spellName);
	}
	
	public class Spell {
		
		private HashMap<FragmentType, Integer> castCost;
		private int damage;
		
		private Spell() {
			castCost = new HashMap<FragmentType, Integer>();
		}
		
		public HashMap<FragmentType, Integer> getCastCost() {
			return this.castCost;
		}
		
		public int getDamage() { return this.damage; }
			
	}
}
