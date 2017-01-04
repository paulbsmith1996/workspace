package inventories;
// Paul Baird-Smith 2015

import java.util.Vector;

import Interfaces.PocketInt;
import Items.Item;

/**
 *
 * Class Pocket - Comprises Inventory. Implemented as a Vector<Item>. Contains
 * Items of similar type.
 *
 */
public class Pocket extends Vector<Association<Item, Integer>> implements
		PocketInt {

	/**
	 * Name holds type of pocket
	 */
	String name;

	int type;

	/**
	 * Constructor makes Pocket aware of its type. Initialize contents vector
	 */
	public Pocket(String name, int type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Returns name of the Pocket. Also equal to Item type held by Pocket
	 */
	public String getName() {
		return this.name;
	}

	public int getType() {
		return this.type;
	}

	/**
	 * Returns the Item in the Pocket with name itemName
	 *
	 * @param String
	 *            - Holds name of Item to be searched for
	 */
	public Item findItem(String itemName) {

		// Lowercase String representation of itemName
		String lowerCase = itemName.toLowerCase();

		// Read through Pocket elements to find matching name
		for (int i = 0; i < size(); i++) {
			Item temp = elementAt(i).getKey();

			if (temp.getName().toLowerCase().equals(lowerCase)) {
				return temp;
			}
		}

		// No Item name matches given String
		System.out.println("No such item found");
		return null;

	}

	public boolean contains(Item item) {
		return contains(item.getID());
	}

	public boolean contains(int itemID) {
		for (Association<Item, Integer> assoc : this) {
			if (assoc.getKey().getID() == itemID) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Add specified item to Pocket
	 */
	public void addItem(Item item) {

		if (contains(item)) {
			// Pocket already contains item
			for (Association<Item, Integer> assoc : this) {

				// Compare ID with desired ID until correct ID found
				if (assoc.getKey().getID() == item.getID()) {
					int quant = assoc.getValue();
					quant++;
					assoc.setValue(quant);
				}

			}

		} else {
			// Vector does not contain item so add
			Association<Item, Integer> assoc = new Association<Item, Integer>(
					item, 1);
			add(assoc);
		}
	}

	/**
	 * Returns true if specified item is contained in the Pocket
	 */
	public boolean hasItem(Item item) {
		return getQuantity(item) != 0;
	}

	public boolean hasItem(int ID) {
		return getQuantity(ID) != 0;
	}

	public boolean hasItem(String itemName) {
		return getQuantity(itemName) != 0;
	}

	/**
	 * Return the number of instances of item contained within the Pocket
	 */
	public int getQuantity(int itemID) {

		// Read through Pocket to find Association holding correct Item
		for (Association<Item, Integer> assoc : this) {

			// Find matching ID of items to make a match
			if (assoc.getKey().getID() == itemID) {
				int quant = assoc.getValue();
				return quant;
			}
		}

		// Indicate no instance of Item found in Pocket
		return 0;
	}

	public int getQuantity(Item item) {
		int ID = item.getID();
		return getQuantity(ID);
	}

	public int getQuantity(String itemName) {

		String lowercase = itemName.toLowerCase();

		// Read through Pocket to find Association holding correct Item
		for (Association<Item, Integer> assoc : this) {

			// Find matching ID of items to make a match
			if (assoc.getKey().getName().toLowerCase().equals(lowercase)) {
				int quant = assoc.getValue();
				return quant;
			}
		}

		// Indicate no instance of Item found in Pocket
		return 0;
	}

	/**
	 * Remove indicated item from pocket. Essentially reverse of add so look at
	 * those comments
	 */
	public void remove(Item item) {
		if (hasItem(item)) {
			// At least one instance of item in Pocket
			for (int x = 0; x < size(); x++) {

				Association<Item, Integer> temp = elementAt(x);

				// Find matching Item in Pocket
				if (temp.getKey().getID() == item.getID()) {
					// Decrement amount left in Pocket
					int quant = temp.getValue();
					quant--;
					temp.setValue(quant);
				}
			}
		} else {
			// Indicate no instance of item found in Pocket
			System.out.println("No item to remove");
		}

	}

	/**
	 * Returns true if Pocket has no elements or if all Associations indicate
	 * there are no instances of any Item
	 */
	public boolean isEmpty() {

		// Trivial base case: If size ==0, Pocket is empty
		if (size() == 0) {
			return true;
		}

		// Read through all elements of Pocket
		for (int x = 0; x < size(); x++) {
			// If any Association is nonzero, Pocket not empty
			if (elementAt(x).getValue() != 0) {
				return false;
			}
		}

		// No non-zero Association found within Pocket
		return true;
	}

	/**
	 * Returns the name and quantities of all items contained in pocket
	 */
	public String toString() {

		StringBuffer s = new StringBuffer();

		for (Association<Item, Integer> assoc : this) {

			if (assoc.getValue() != 0) {
				String info = assoc.getValue() + " " + assoc.getKey().getName()
						+ "s available";
				s.append(info + "\n");
			}
		}

		return s.toString();
	}

	public String[] contents() {
		String[] contents = new String[size()];
		for (int x = 0; x < this.size(); x++) {
			contents[x] = elementAt(x).getValue() + " "
					+ elementAt(x).getKey().getName();
		}
		return contents;
	}

	public String[] itemNames() {
		String[] names = new String[size()];
		for (int x = 0; x < this.size(); x++) {
			names[x] = elementAt(x).getKey().getName();
		}
		return names;
	}

	/**
	 * A modified version of toString to display prices of Items as well. Used
	 * in the Merchant class for transactions
	 */
	public String[] toPrices(boolean buying) {

		if (isEmpty()) {
			String[] empty = {"No items available"};
			return empty;
		}

		String[] prices = new String[size()];
		int x = 0;
		
		for (Association<Item, Integer> assoc : this) {

			String info = assoc.getValue() + " " + assoc.getKey().getName();

			int cost = assoc.getKey().getCost();

			if (buying) {
				info += "s: " + cost;
			} else {
				info += "s: " + (3 * cost) / 4;
			}
			info += " coins";
			prices[x] = info;
			x++;
		}

		return prices;
	}
}


