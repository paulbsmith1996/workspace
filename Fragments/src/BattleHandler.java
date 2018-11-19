import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleHandler {

	private static final int TEAM_SIZE = 4;
	
	private final Random random;
	
	private Creature[] allies;
	private Creature[] opponents;
	
	private static Scanner inputScanner = new Scanner(System.in);
	
	public BattleHandler(Creature[] allies, Creature[] opponents) {
		if(allies.length <= TEAM_SIZE && opponents.length <= TEAM_SIZE) {
			this.allies = allies;
			this.opponents = opponents;
		}
		
		random = new Random();
	}
	
	public void initiateBattle() {
		while(alive(allies) && alive(opponents)) {
			List<Attack> allyAttacks = selectMoves(allies, opponents);
			List<Attack> oppAttacks = selectMovesAI(opponents, allies);
			resolveConflicts(allyAttacks, oppAttacks);
			for(Creature ally: allies) {
				System.out.println("Ally has " + ally.getHP() + " hp remaining");
			}
			for(Creature opp: opponents) {
				System.out.println("Opponent has " + opp.getHP() + " hp remaining");
			}
		}
	}
	
	private boolean alive(Creature[] creatures) {
		for(Creature c: creatures) {
			if(c.getHP() > 0) {
				return true;
			}
		}
		return false;
	}
	
	private List<Attack> selectMoves(Creature[] attackers, Creature[] targets) {
		ArrayList<Attack> attacks = new ArrayList<>(); 
		for(int i = 0; i < attackers.length; i++) {
			Creature attacker = attackers[i];
			List<Integer> spellBook = attacker.getSpellBook();
			
			int inputSpell = -1;
			while(!spellBook.contains(inputSpell)) {
				System.out.println("Choose an attack for creature " + i);
				
				for(int spellID: spellBook) {
					System.out.println(Spells.SPELL_IDS_TO_NAMES.get(spellID));
				}
				System.out.println("");
				
				inputSpell = Spells.SPELL_NAMES_TO_IDS.get(inputScanner.nextLine().toLowerCase());
			}
			
			System.out.println("Choose a creature to attack (1 to " + targets.length + ")");
			int targetInd = -1;
			while(targetInd < 0 || targetInd >= targets.length) {
				try {
					targetInd = Integer.parseInt(inputScanner.nextLine()) - 1;
				} catch (NumberFormatException e) {
					System.out.println("NaN");
				}
			}
			attacks.add(new Attack(attacker, targets[targetInd], inputSpell));
		}
		return attacks;
	}
	
	/**
	 * TODO: Make it possible to randomly target own team with beneficial spells
	 * @param attackers
	 * @return
	 */
	private List<Attack> selectMovesAI(Creature[] attackers, Creature[] targets) {
		ArrayList<Attack> attacks = new ArrayList<>();
		for(Creature attacker: attackers) {
			if(attacker != null) {
				List<Integer> actions = attacker.getSpellBook();
				int actionID = actions.get(random.nextInt(actions.size()));
				Creature target = targets[random.nextInt(targets.length)];
				attacks.add(new Attack(attacker, target, actionID));
			}
		}
		return attacks;
	}
	
	/**
	 * TODO: This is going to end up being more intricate then just simultaneous attacks
	 * @param allyAttacks
	 * @param oppAttacks
	 */
	private void resolveConflicts(List<Attack> allyAttacks, List<Attack> oppAttacks) {
		for(Attack att: allyAttacks) {
			Creature target = att.getTarget();
			int curHP = target.getHP();
			target.setHP(Math.max(curHP - computeDamage(att), 0));
		}
		
		for(Attack att: oppAttacks) {
			Creature target = att.getTarget();
			int curHP = target.getHP();
			target.setHP(Math.max(curHP - computeDamage(att), 0));
			System.out.println("Opponent used " + Spells.SPELL_IDS_TO_NAMES.get(att.getAction()));
		}
	}
	
	/**
	 * Let
	 * A = attack "multiplier" of attacker  = Base Magic + 2*spec in the spell type
	 * F = defense "multiplier" of defender = Base Magic + 1.5*spec in the spell type
	 * D = base damage for spell
	 * 
	 * Then this computes AD/F + 1
	 * @param att - The attack being performed, whose effect we want to compute
	 * @return - The amount of damage done to the target of the attack
	 */
	public int computeDamage(Attack att) {
		Creature initiater = att.getActor();
		int[] initiaterStats = initiater.getStats();
		Creature target = att.getTarget();
		int[] targetStats = target.getStats();
		
		int actionID = att.getAction();
		// The type of a spell is stored in the 0th element of the stats
		int spellType = Spells.SPELL_STATS.get(actionID)[0];
		
		// Get the index of the stat that represents the Creatures specialization 
		// in that kind of magic
		int specStatInd = Creature.NON_SPEC_STATS + spellType;
		
		return ((initiater.getBaseMagic() + 2*initiaterStats[specStatInd]) * Spells.SPELL_STATS.get(actionID)[1] / 
				(target.getConstitution() + (3*targetStats[specStatInd]) / 2)) + 1;
	}
	
	public static void main( String[] args) {
		
		int[] playerStats = {100, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
		int[] oppStats = {100, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
		
		ArrayList<Integer> playerSpells = new ArrayList<>();
		playerSpells.add(Spells.SPELL_NAMES_TO_IDS.get("water jet"));
		playerSpells.add(Spells.SPELL_NAMES_TO_IDS.get("rock throw"));
		
		ArrayList<Integer> oppSpells = new ArrayList<>();
		oppSpells.add(Spells.SPELL_NAMES_TO_IDS.get("water ball"));
		
		Creature[] allies = {new Creature(playerStats, playerSpells)};
		Creature[] opps = {new Creature(oppStats, oppSpells)};
		
		BattleHandler bHandler = new BattleHandler(allies, opps);
		bHandler.initiateBattle();
	}
}
