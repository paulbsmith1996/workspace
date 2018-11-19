import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Holds the logic for a basic Creature that can be involved in battles
 * 
 * Creatures have stats that are used to determine the strengths and effects
 * of spells used in battle. A Creature has 16 stats (not including the level stat),
 * all of which increase linearly with level :
 * 
 * Base HP stat      - determines starting HP
 * Base Magic stat   - determines base magic strength
 * Constitution stat - determines base resistance to spells
 * Luck stat 		 - used to compute chances of critical hits / favorable RNG events
 * Attention stat    - determines speed at which competencies are acquired
 * 11 x Spec stats   - a specialization stat for every kind of magic, used in attack and defense
 * 
 * Each Creature has a pouch of fragments that can be used to cast spells
 * in battle, and a "book" of spells containing the IDs of all the spells the
 * Creature can cast. 
 * 
 * @author paulbairdsmith
 *
 */
public class Creature {

	// A "pouch" that holds the current number and type of fragments
	// the Creature currently owns
	private HashMap<FragmentType, Integer> fragmentPouch;
	
	// Holds a list of IDs that map to the spells the Creature can cast
	private ArrayList<Integer> spellBook;
	
	// Constants used for indexing
	private static final int NUM_STATS = 16;
	public static final int NON_SPEC_STATS = 5;
	
	private final int DEFAULT_LEVEL = 1;
	
	// The array of stats for the Creature
	private int[] stats;
	
	// The current hp stat of the Creature, different from the base HP
	// stat. This can change, and when it hits 0, the Creature is "dead"
	private int hp;
	
	// Holds level of Creature
	private int level;
	
	// Indices for the base stats that are not spec stats
	private final int BASE_HP_IND 	= 0;
	private final int BASE_MAG_IND 	= 1;
	private final int CONST_IND 	= 2;
	private final int LUCK_IND 		= 3;
	private final int ATTN_IND		= 4;
	
	/**
	 * General constructor for Creature with no parameters. Defaults
	 * level to 1, stats to an array of 0s, fragments to an empty pouch,
	 * and the spell book to an empty book
	 */
	public Creature() {
		this.fragmentPouch = new HashMap<FragmentType, Integer>();
		this.spellBook = new ArrayList<>();
		this.stats = new int[NUM_STATS];
		this.level = DEFAULT_LEVEL;
		this.hp = stats[BASE_HP_IND];
	}
	
	/**
	 * Constructor for a Creature given some starting stats
	 * @param stats - The stats of the Creature
	 */
	public Creature(int[] stats) {
		this();
		if(stats.length == NUM_STATS) {
			this.stats = stats;
		} else {
			System.err.println("Invalid stats, provided " + stats.length 
					+ " stats, but expected " + NUM_STATS);
		}
		this.hp = stats[0];
	}
	
	/**
	 * Constructor for a Creature given some starting stats and spell book
	 * @param stats - the stats of the Creature
	 * @param spellBook - The list of spells the Creature can cast
	 */
	public Creature(int[] stats, ArrayList<Integer> spellBook) {
		this(stats);
		this.spellBook = spellBook;
	}
	
	// Getter and setter for current HP
	public int getHP() { return this.hp; }
	public void setHP(int hp) { this.hp = hp; }
	
	// Returns the stats of the Creature
	public int[] getStats() { return this.stats; }
	
	// Getters for specific stats of the Creature
	public int getBaseHP() { 		return this.stats[BASE_HP_IND]; }
	public int getBaseMagic() { 	return this.stats[BASE_MAG_IND]; }
	public int getConstitution() {	return this.stats[CONST_IND]; }
	public int getLuck() { 			return this.stats[LUCK_IND]; }
	public int getAttention() { 	return this.stats[ATTN_IND]; }
	public int getAquosSp() { 		return this.stats[NON_SPEC_STATS + Spells.AQUOS_TYPE]; }
	public int getAeroSp() { 		return this.stats[NON_SPEC_STATS + Spells.AERO_TYPE]; }
	public int getChronoSp() { 		return this.stats[NON_SPEC_STATS + Spells.CHRONO_TYPE]; }
	public int getCorpusSp() { 		return this.stats[NON_SPEC_STATS + Spells.CORPUS_TYPE]; }
	public int getEtherSp() { 		return this.stats[NON_SPEC_STATS + Spells.ETHER_TYPE]; }
	public int getPsycheSp() { 		return this.stats[NON_SPEC_STATS + Spells.PSYCHE_TYPE]; }
	public int getPyroSp() { 		return this.stats[NON_SPEC_STATS + Spells.PYRO_TYPE]; }
	public int getTeleSp() { 		return this.stats[NON_SPEC_STATS + Spells.TELE_TYPE]; }
	public int getTerraSp() { 		return this.stats[NON_SPEC_STATS + Spells.TERRA_TYPE]; }
	public int getVoltSp() { 		return this.stats[NON_SPEC_STATS + Spells.VOLT_TYPE]; }
	public int getVoluntarySp() { 	return this.stats[NON_SPEC_STATS + Spells.VOLUNTARY_TYPE]; }
	
	// Returns a List<Integer> representing the IDs of the spells that can be
	// cast by this Creature
	public List<Integer> getSpellBook() { return this.spellBook; }
	
	/**
	 * Removes the fragments required to cast the spell
	 * @param spell - The spell being cast
	 * @return 0 if the spell can be cast, 1 otherwise
	 */
	public int checkAndRemoveFragsForSpell(Spells.Spell spell) {
		HashMap<FragmentType, Integer> spellCost = spell.getCastCost();
		for(FragmentType fragType: spellCost.keySet()) {
			if(spellCost.get(fragType) > checkForFrag(fragType)) {
				return 0;
			}
		}
		for(FragmentType fragType: spellCost.keySet()) {
			removeFrag(fragType,spellCost.get(fragType));
		}
		return 1;
	}
	
	// Returns the number of fragments of the passed type in the pouch
	public int checkForFrag(FragmentType type) {
		return (fragmentPouch.containsKey(type)) ? fragmentPouch.get(type) : 0; 
	}
	
	/**
	 * Removes fragments from the pouch
	 * @param type - Type of fragment to remove
	 * @param amount - Amount of fragments to remove
	 * @return How many fragments of the passed type are left
	 */
	public int removeFrag(FragmentType type, int amount) {
		int numOfType = checkForFrag(type);
		int remaining = 0;
		
		if(numOfType > amount) {
			remaining = numOfType - amount;
		}
		
		fragmentPouch.put(type, remaining);
		return remaining;
	}
	
	// Default method to remove just a single fragment
	public int removeFrag(FragmentType type) {
		return removeFrag(type, 1);
	}
	
	/**
	 * Add a specified numbder of fragments of a given type to the pouch
	 * @param type - The type of fragment to add to the pouch
	 * @param amount - The amount of fragments to add to the pouch
	 */
	public void addFrag(FragmentType type, int amount) {
		int numOfType = checkForFrag(type);
		fragmentPouch.put(type, numOfType + amount);
	}
	
	// Default method to add a single fragment to the pouch
	public void addFrag(FragmentType type) {
		addFrag(type,1);
	}
}
