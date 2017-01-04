package inventories;
// Paul Baird-Smith 2015

import gameobjects.Player;

import java.util.Random;

import Items.ClothRobe;
import Items.Fire;
import Items.FurBoots;
import Items.FurCap;
import Items.FurCoat;
import Items.FurGloves;
import Items.FurLeggings;
import Items.HealthPotion;
import Items.Ice;
import Items.IronOre;
import Items.ManaPotion;
import Items.Pickaxe;
import Items.Restore;
import Items.Sharpen;
import Items.Spark;
import Items.StoneSword;
import Items.WoodSword;

public class Stocks extends Inventory {

    Player player;
    
    /**                                                                                 
     * Ints holding quantities of each Potion type                                      
     */
    protected int totHP;
    protected int totMana;

    /**                                                                                                          
     * Create a Random object to generate Merchant's inventory                                                   
     */
    protected Random r;

    public Stocks(Player p, int level) {

	player = p;

	r = new Random();

	// Initialize amounts of Potion types                                           
        totHP = 0;
        totMana = 0;

        // Generate up to 10 random potions                                             
        for (int y = 0; y < 10; y++) {

            int roll = r.nextInt();

            if (roll%3 == 0) {
                // E(X) = 3                                                             
                addItem(new HealthPotion());
                totHP++;
            } else if (roll%3 == 1) {
                // E(Y) = 3                                                             
                addItem(new ManaPotion());
                totMana++;
            }

            // E(X + Y) = 6                                                             
        }

	populateArmaments(level);
	populateSpells(level);
	populateUtilities(level);
	populateOres(level);
    }

    public void populateArmaments(int level) {
	// Random Generation of non-Spells and non-Consumables                                                       
	if (level < 10) {
	    if (r.nextInt(2) == 1) {
		addItem(new WoodSword());
	    }
	    
	    if (r.nextInt(2) == 1) {
		addItem(new FurCap());
	    }
	    
	    if (r.nextInt(3) == 1) {
		addItem(new FurLeggings());
	    }
	    
	    if (r.nextInt(4) == 1) {
		addItem(new FurCoat());
	    }
	} 

	if (level >= 6 && level <= 15) {
	
	    if (r.nextInt(3) == 1) {
		addItem(new ClothRobe(player));
	    }
	    
	    if (r.nextInt(3) == 1) {
		addItem(new StoneSword());
	    }
	}

        // Add in basic Armors for all Merchants                                                                     
        addItem(new FurBoots());
        addItem(new FurGloves());

    }

    public void populateSpells(int level) {
        // Add in basic Spells for all Merchants 
	if (level <= 7) {
	    addItem(new Fire(player));
	    addItem(new Ice(player));
	    addItem(new Spark(player));
	}

	if (level >= 5) {
	    addItem(new Restore(player));
	    addItem(new Sharpen(player));
	}
    }

	public void populateUtilities(int level) {
		addItem(new Pickaxe());
	}

    public void populateOres(int level) {
	for(int i = 0; i < 10; i++) {
	    if (r.nextInt(3) == 0) {
		addItem(new IronOre());
	    }
	}
    }
}