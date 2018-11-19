import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

/**
 * At the start of run, loads all the information on spells from the
 * specified spreadsheet.
 * 
 * @author paulbairdsmith
 *
 */
public class SpellLoader {
	
	// Delimiter between columns of information. We use a CSV format, so this
	// is typically a comma
	private static final String DELIMITER = ",";
	
	// Number of possible fragment types
	private static final int NUM_FRAG_TYPES = FragmentType.values().length;
	
	// Holds the cost of every spell
	public static HashMap<Integer, HashMap<FragmentType,Integer>> spellCosts =
			new HashMap<>();
	
	// Holds the stats of every spell, with:
	// Stats = [Spell Type, Damage]
	public static HashMap<Integer, int[]> spellStats = new HashMap<>();
	
	// Maps spell names to their respective IDs
	public static HashMap<String, Integer> spellNamesToIDs = new HashMap<>();
	
	// Maps spell IDs back to their names
	public static HashMap<Integer, String> spellIDsToNames = new HashMap<>();
	
	// This line count will be used to assign unique IDs to every spell
	private static int lineCount = 0; 
	
	/**
	 * Loads all the information on the spells from the spreadsheet
	 * @throws FileNotFoundException
	 */
	public static void loadSpells() throws FileNotFoundException {
		
		// Create a new File and Scanner for the spell information spreadsheet
		File spellFile = new File(ResourceLocator.SPELL_DATA_FILE_NAME);
		Scanner spellScan = null;
		try {
			spellScan = new Scanner(spellFile);
		} catch(FileNotFoundException fe) {
			throw new FileNotFoundException("SpellLoader: Could not locate spell file: " + ResourceLocator.SPELL_DATA_FILE_NAME);
		}
		
		// Skip header line
		String[] headers = spellScan.nextLine().split(DELIMITER);
		for(int i = 0; i < headers.length; i++) {
			headers[i] = headers[i].toLowerCase();
		}
		
		// Process each line of file
		while(spellScan.hasNextLine()) {
			String nextLine = spellScan.nextLine();
			if(nextLine.replaceAll(",", "").equals("")) {
				break;
			}
			processLine(nextLine, headers);
		}
		
		// Close scanner when done reading file
		spellScan.close();
	}
	
	/**
	 * Process the information in the given line of the spell file
	 * @param line - The line currently being parsed in the spell file
	 * @param headers - The ordered set of headers for each column
	 * @return - A name of a spell and its associated cost in fragments
	 * @throws NumberFormatException
	 * @throws RuntimeException
	 */
	public static void processLine(String line, String[] headers) 
			throws NumberFormatException, RuntimeException {
		
		// Split the line across the delimiters
		String[] lineEntries = line.split(DELIMITER);
		
		// The first entry is the name of the spell
		String spellName = lineEntries[0];
		
		// The ID of the spell is the line it is found on. This creates a
		// unique ID for each spell
		int spellID = lineCount++;
		spellNamesToIDs.put(spellName.toLowerCase(), spellID);
		spellIDsToNames.put(spellID, spellName);
		
		// Put the spell stats in the appropriate map
		// Compute damage
		int damage = -1;
		try {
			damage = Integer.parseInt(lineEntries[lineEntries.length - 1]);
		} catch(NumberFormatException e) {
			throw new NumberFormatException("SpellLoader: could not read damage value: " 
												+ lineEntries[lineEntries.length - 1]);
		}
		if(damage < 0) {
			throw new NumberFormatException("SpellLoader: could not read damage value: " 
					+ lineEntries[lineEntries.length - 1]);
		}
		// Find the type of the spell using helper method, and put in map
		int[] stats = {typeNameToTypeID(lineEntries[lineEntries.length - 2]), damage};
		spellStats.put(spellID, stats);
		
		// Create and populate the data structure that holds the cost of the spell
		// in amounts of fragment types
		HashMap<FragmentType,Integer> castCost = new HashMap<>();
		
		for(int fragIndex = 1; fragIndex <= NUM_FRAG_TYPES; fragIndex++) {
			
			FragmentType type = Fragments.FRAG_NAME_TO_TYPE.get(headers[fragIndex]);
			
			if(type != null) {
				int numOfType = -1;
				// Get the number of fragments of the current type, required to cast the spell
				try {
					numOfType = Integer.parseInt(lineEntries[fragIndex]);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("SpellLoader: Invalid number format for: " + lineEntries[fragIndex]);
				}
				if(numOfType < 0) {
					throw new NumberFormatException("SpellLoader: Invalid number format for: " + lineEntries[fragIndex]);
				}
				castCost.put(type, numOfType);
			} else {
				throw new RuntimeException("SpellLoader: Invalid column header: " + headers[fragIndex]);
			}
		}
		spellCosts.put(spellID, castCost);
	}
	
	// Helper method to turn the name of a spell type into the corresponding spell type ID 
	private static int typeNameToTypeID(String typeName) {
		String formattedName = typeName.toLowerCase();
		if(formattedName.equals("aquos")) {
			return Spells.AQUOS_TYPE;
		} else if(formattedName.equals("aero")) {
			return Spells.AERO_TYPE;
		} else if(formattedName.equals("chrono")) {
			return Spells.CHRONO_TYPE;
		} else if(formattedName.equals("corpus")) {
			return Spells.CORPUS_TYPE;
		} else if(formattedName.equals("ether")) {
			return Spells.ETHER_TYPE;
		} else if(formattedName.equals("psyche")) {
			return Spells.PSYCHE_TYPE;
		} else if(formattedName.equals("pyro")) {
			return Spells.PYRO_TYPE;
		} else if(formattedName.equals("tele")) {
			return Spells.TELE_TYPE;
		} else if(formattedName.equals("terra")) {
			return Spells.TERRA_TYPE;
		} else if(formattedName.equals("volt")) {
			return Spells.VOLT_TYPE;
		} else {
			return Spells.VOLUNTARY_TYPE;
		}
	}
	
	
	// Quick test for cursory correctness
	public static void main(String[] args) {
		try {
			SpellLoader.loadSpells();
		} catch(Exception e) {
			// Do nothing
		}
		HashMap<Integer, HashMap<FragmentType,Integer>> spellCosts = SpellLoader.spellCosts;
		System.out.println(spellCosts.get(spellNamesToIDs.get("water jet")).get(FragmentType.AQUOS) == 2);
		System.out.println(spellCosts.get(spellNamesToIDs.get("earthquake")).get(FragmentType.TERRA) == 5);
	}
}
