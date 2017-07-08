package Interfaces;

import Items.Armament;
import Items.Item;
import Items.Potion;
import gameobjects.Creature;
import spell.Spell;
import spell.SpellBook;

public interface PlayerInt {

    // Return total EXP earned by Player
    public int getEXP();

    // Return random outcome for random events
    public int getOutcome();

    // Set SpellBook to book
    public void setSpellBook(SpellBook book);

    // Store quant amount of Item item in inventory
    public void store(Item item, int quant);

    // Use an item by specifying an Item or its name
    // Removes item from inventory, determines type and how to use it
    public String use(Item item);
    public String use(String itemName);

    // Equip an arament to Player's holster
    public String equip (Armament arm);

    // Return String representation of all equipped Aramaments
    public String allEquipped();

    // Return a String representation of Player's Inventory
    public String invDisplay();

    // Cast a spell that can be specified by its name
    public int cast(Spell spell, Creature other);
    public int cast(String spell, Creature other);

    // Return true if player has an instance of this item in inventory
    public boolean hasItem(String itemName);

    // Drink a potion and apply effects to player
    public String drinkPotion(Potion potion);

    // Add EXP to Player's total
    public int gainEXP(int exp);

    // Update stats and skills as Player levels up
    public String levelUp();

    // Reset stats when a buff is removed on an Armament
    public void resetStats();

}