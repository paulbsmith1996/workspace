package inventories;
// Paul Baird-Smith 2015

import java.util.Vector;

import Items.Item;
import Items.ItemReference;

/**
 *
 * Class Inventory - Implemented as a Vector<Pocket>, this class
 * holds all of the user's Items, including Weapons, Armor, Potion...
 * Methods used to find specified Items and Pockets to make navigation
 * more simple for user
 *
 */
public class Inventory extends Vector<Pocket> {
    
    /**
     * Int variable holding money owned by holder of Inventory Object
     */
    protected int money;
    
    /**
     * Constructor initializes amount of money owned by holder
     */
    public Inventory () {
	this.money = 0;
	add(new Pocket("Weapon", ItemReference.WEAPON));
	add(new Pocket("Armor", ItemReference.ARMOR));
	add(new Pocket("Potion", ItemReference.POTION));
	add(new Pocket("Spell", ItemReference.SPELL));
	add(new Pocket("Utilities", ItemReference.UTILITY));
	add(new Pocket("Ore", ItemReference.ORE));
    }

    /**
     * Methods used to retreive and manipulate amount of money
     *
     * @param int - Amount to be added or removed from total money
     */ 
    public void addMoney(int amount) { this.money += amount; }
    public void removeMoney(int amount) { this.money -= amount; }
    public int getMoney() { return this.money; }
	
    /**
     * Adds an instance of the desired Item to the inventory in the appropriate pocket
     *
     * @pre: item != null
     * @post: item added to correct Pocket in Inventory
     *
     * @param Item - Item to be added to the Inventory
     */
    public void addItem (Item item) {

	// Check if item is null
	if (item != null) {

	    // Get type of item to determine which pocket it goes in
	    int type = item.getItemType();
	    
	    // Search for correct pocket to put item in
	    for (int x = 0; x < size(); x++) {
		if (elementAt(x).getType() == type) {
		    elementAt(x).addItem(item);
		}
	    }
	}

    }

    /**
     * Remove Item from correct pocket within inventory
     *
     * @pre: item != null && inventory contains item
     * @post: item removed successfully from inventory
     *
     * @param Item - Item to be removed from the Inventory
     */
    public void removeItem (Item item) {
	// Get type of item to find the correct pocket
	int type = item.getItemType();

	// Search for pocket holding Item
	for (int x = 0; x < size(); x++) {
	    if (elementAt(x).getType() == type) {
		// Remove item from appropriate Pocket
		elementAt(x).remove(item);
	    }
	}
	
    }

    /**
     * Finds the Pocket holding Items of type pocketName
     *
     * @param pocketName - The type of the Pocket being searched,
     * always the same as the Item type it holds
     */
    public Pocket findPocket (int pocketType) {
	for (int i = 0; i < size(); i++) {
	    Pocket currPocket = elementAt(i);

	    if (currPocket.getType() == pocketType) {
		// Appropriate Pocket Object found
		return currPocket;
	    }
	}

	// In case the Pocket cannot be found in the Inventory
	System.out.println("Inventory.findPocket: Pocket not found");
	return null;
	
    }

    /**
	 * Used to find the Item with the specified name within the Inventory
	 *
	 * @pre: Inventory contains an Item with name itemName
	 * @post: return item with name itemName
	 *
	 * @param String
	 *            - holds the name of the Item requested by the user
	 */
	public Item findItem(String itemName) {

		String lowerCase = itemName.toLowerCase();

		// Find Pocket of correct item type
		for (int i = 0; i < size(); i++) {
			Pocket currPocket = elementAt(i);

			// Search within Pocket to find requested Item
			for (int n = 0; n < currPocket.size(); n++) {
				Item currItem = currPocket.elementAt(n).getKey();

				if (currItem.getName().toLowerCase().equals(lowerCase)) {
					return currItem;
				}
			}
		}

		// Item not found within the Inventory
        //System.out.println("No such item found");
        return null;

    }

    public Item findItem(int itemID) {
	for(Pocket pocket: this) {
	    for(Association<Item,Integer> assoc: pocket) {
		if (assoc.getKey().getID() == itemID) {
		    return assoc.getKey();
		}
	    }
	}

	System.out.println("Inventory.findItem: No such Item found in inventory");
	return null;
    }

    /**
     * Determines if there is an instance of the specified Item in the Inventory
     *
     * @param Item - Item to be searched for in Inventory
     */
    public boolean hasItem(Item item) { return getQuantity(item) != 0; }
    public boolean hasItem(int ID) { return getQuantity(ID) != 0; }
    public boolean hasItem(String itemName) { return getQuantity(itemName) != 0; }

    /**
     * Returns amount of instances of specified item in Inventory
     *
     * @pre: item != null
     * @post: returns number of instances of item found in Inventory
     *
     * @param Item - Item to be searched for in Inventory
     */
    public int getQuantity(Item item) {

	// Trivial case to check. Item is null
	if (item == null) {
	    return 0;
	}

	// Get type of item to find the correct pocket                  
        int type = item.getItemType();

        // Search for pocket holding Item                                                                 
        for (int x = 0; x < size(); x++) {
	    
	    Pocket temp = elementAt(x);
	    
            if (temp.getType() == type) {
		// Return number of instances of item in Pocket
                return temp.getQuantity(item);
            }
        }
	
	// No instances of item found in Inventory
        return 0;

    }
    
	public int getQuantity(int ID) {
		for (Pocket pocket : this) {
			for (Association<Item, Integer> assoc : pocket) {
				if (assoc.getKey().getID() == ID) {
					return assoc.getValue();
				}
			}
		}

		return 0;

	}

    public int getQuantity(String itemName) {
	for(Pocket pocket: this) {
            for (Association<Item,Integer> assoc: pocket) {
                if (assoc.getKey().getName().equals(itemName)) {
                    return assoc.getValue();
                }
            }
        }

        return 0;
    }

    /**
     * Determines if specified Pocket Object is empty
     *
     * @param String - Name of Pocket that needs to be checked for Items
     */
    public boolean emptyPocket (String pocketType) {

        for (int x  = 0; x < size(); x++) {

	    // Look for desired pocket within vector
            Pocket pocket = elementAt(x);

            if (pocket.getName().equals(pocketType)) {
                // Check name of pocket matches pocketType String. 
		// Get String representation of pocket                                                    
                return pocket.isEmpty();
            }
        }

        // If pocket not found, inform user of error
	System.out.println("Pocket not found");
        return false;

    }

    /**
     * Returns a String representation of the Items in Pocket with name pocketType
     *
     * @param String - Name of Pocket to be checked
     */
    public String seePocket(int pocketType) {

	// Look for desired pocket within vector
	for (int x  = 0; x < size(); x++) {

	    Pocket pocket = elementAt(x);
	    
	    // Check name of pocket matches pocketType String
	    if (pocket.getType() == pocketType) {
		// Get String representation of pocket
		return pocket.toString(); 
	    }
	}
	
	// If pocket not found, inform user of error
	return "Pocket not found";
    }

    /**
     * Returns a String representation of the inventory (names of all Pockets)
     */
    public String toString () {

	StringBuffer s = new StringBuffer();

	for (int x = 0; x < size(); x++) {
	    s.append(elementAt(x).getName()+ "\n");
	}

	return s.toString();
    }
    
    public String[] pocketNames() {
    	String[] pockets = new String[size()];
    	int x = 0;
    	for(Pocket p: this) {
    		pockets[x] = (p.getName());
    		x++;
    	}
    	return pockets;
    }
}