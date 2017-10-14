
import java.io.Serializable;

/**
 * This class creates a Character, which holds a name,
 * quip, hp value, level value, and gold value.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */
public abstract class Character implements Serializable
{
    // String that holds the name of the character
    private String name;
    // String that holds the quip of the character
    private String quip;
    // Integer that holds the amount of hp the character has
    private int hp;
    // Integer that holds the character's level
    private int level;
    // Integer that holds the amount of gold the user house
    private int gold;
    
    /**
     * Constructor that creates a new Character
     * @param n String that holds the name of the character
     * @param q String that holds the quip of the character
     * @param h Integer that holds the amount of hp the character has
     * @param l Integer that holds the character's level
     * @param g Integer that holds the amount of gold the user house
     */
    public Character(String n, String q, int h, int l, int g)
    {
        name = n;
        quip = q;
        hp = h;
        level = l;
        gold = g;
    }
    
    /**
     * Makes the Character attack
     * @param c character that is being attacked
     */
    public abstract void attack(Character c);
    
    /**
     * Get the name of the character
     * @return String that holds the name of the character
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Get the quip of the character
     * @return String that holds the quip of the character
     */
    public String getQuip()
    {
        return quip;
    }
    
    /**
     * Get the amount of hp of the character has
     * @return Integer that holds the amount of hp the character has 
     */
    public int getHp()
    {
        return hp;
    }
    
    /**
     * Get the level of the character
     * @return Integer that holds the level of the character
     */
    public int getLevel()
    {
        return level;
    }
    
    /**
     * Get how much gold the user has
     * @return Integer that holds the amount of gold the character has 
     */
    public int getGold()
    {
        return gold;
    }
    
    /**
     * Makes the character level up
     */
    public void increaseLevel()
    {
        level++;
    }
    
    /**
     * Restores the character to full health (which is 50).
     * @param h Integer that holds the max health value
     */
    public void heal(int h)
    {
        hp = h;
    }
    
    /**
     * Makes the character take damage, deducting a certain amount from their current hp.
     * @param h amount to be subtracted to the character's current hp
     */
    public void takeDamage(int h)
    {
        hp = hp - h;
    }
    
    /**
     * Character receives a certain amount of gold
     * @param g amount of gold character will receive
     */
    public void collectGold(int g)
    {
        gold = gold + g;
    }
    
    /**
     * Display the user's final stats upon finishing the game.
     */
    public void display()
    {
        System.out.println(name + "'s final stats: ");
        System.out.println("HP: " + hp);
        System.out.println("Level: " + level);
        System.out.println("Gold: " + gold);
    }
}
