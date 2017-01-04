package Interfaces;

import Items.Item;

// Paul Baird-Smith 2015

public interface PocketInt {

    /**
     * Returns name of Pocket
     */
    public String getName();

    /**
     * Returns type of pocket
     */
    public int getType();

    // Returns true if the Pocket has ever contained specified Item
    // Needed to add Items in stacks to the pocket
    public boolean contains(Item item);
    public boolean contains(int itemID);

    // Returns item with indicated name if contained in pocket
    public Item findItem(String itemName);

    // Adds specified item to pocket
    public void addItem(Item item);
    
    // Returns true if pocket contains at least one instance of item
    public boolean hasItem(Item item);

    // Returns quantity of item in pocket
    public int getQuantity(Item item);

    // Removes indicated item from pocket
    public void remove(Item item);

    // Returns true if pocket does not contain any instances of an item
    public boolean isEmpty();

    // Returns a String representation of the items contained in pocket
    public String toString();

    // Returns String representation of items in pocket and their prices
    // Boolean determines whether to display buy or sell prices
    public String[] toPrices(boolean buying);


}