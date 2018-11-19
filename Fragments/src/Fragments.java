import java.util.HashMap;

/**
 * Wrapper for a HashMap, keyed by Strings, with values that
 * correspond to the FragmentType associated to the String
 * 
 * @author paulbairdsmith
 *
 */
public class Fragments {
	
	public static final HashMap<String, FragmentType> FRAG_NAME_TO_TYPE;
	static {
		FRAG_NAME_TO_TYPE = new HashMap<String, FragmentType>();
		FRAG_NAME_TO_TYPE.put("aquos", FragmentType.AQUOS);
		FRAG_NAME_TO_TYPE.put("aero", FragmentType.AERO);
		FRAG_NAME_TO_TYPE.put("chrono", FragmentType.CHRONO);
		FRAG_NAME_TO_TYPE.put("corpus", FragmentType.CORPUS);
		FRAG_NAME_TO_TYPE.put("ether", FragmentType.ETHER);
		FRAG_NAME_TO_TYPE.put("psyche", FragmentType.PSYCHE);
		FRAG_NAME_TO_TYPE.put("pyro", FragmentType.PYRO);
		FRAG_NAME_TO_TYPE.put("tele", FragmentType.TELE);
		FRAG_NAME_TO_TYPE.put("terra", FragmentType.TERRA);
		FRAG_NAME_TO_TYPE.put("volt", FragmentType.VOLT);
		FRAG_NAME_TO_TYPE.put("voluntary", FragmentType.VOLUNTARY);
	}
}