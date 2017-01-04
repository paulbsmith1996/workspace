package Interfaces;
// Paul Baird-Smith 2015

public interface CreatureInt {

    public int getLevel();
    public int getHp();
    public int getMaxHP();
    public int getMana();
    public int getMaxMana();
    public int getAP();
    public void setAP(int newValue);
    public int getDP();
    public void setDP(int newValue);
    public void damage(int damage);
    public boolean dead();
    public String returnStats();
    public String returnAllStats();

}
