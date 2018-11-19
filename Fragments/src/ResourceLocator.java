import java.io.File;

/**
 * Holds paths for different resource files
 * 
 * @author paulbairdsmith
 *
 */
public class ResourceLocator {

	// Root of the package containing the game
	private static final String PACKAGE_ROOT = System.getProperty("user.dir");
	
	// Additional class path classifier that points to the resources package
	private static final String RESOURCES_FOLDER = PACKAGE_ROOT + "/resources";
	
	// Name of file containing information on spells
	public static final String SPELL_DATA_FILE_NAME = RESOURCES_FOLDER + "/spells.csv";
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		File[] listOfFiles = new File(System.getProperty("user.dir")).listFiles();
		for(File f: listOfFiles) {
			System.out.println(f.getPath());
		}
	}
}
