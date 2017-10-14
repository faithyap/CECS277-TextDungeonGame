
import java.io.Serializable;
import java.util.Random;

/**
 * This class creates an Enemy, which is an extension of the Character
 * class consisting of a name, quip, hp value, level, gold value, and an item.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */
public class Enemy extends Character implements Serializable
{
    // Item that holds the item of the character
    private Item item;
    
    /**
     * Constructor method that creates a new enemy
     * @param n String that holds the name of the enemy
     * @param q String that holds the quip of the enemy
     * @param h Integer that holds the amount of hp the enemy has
     * @param l Integer that holds the level of the enemy
     * @param g Integer that holds the amount of gold the enemy has
     * @param i Item that the enemy is holding
     */
    public Enemy(String n, String q, int h, int l, int g, Item i)
    {
        super(n, q, h, l, g);
        item = i;
    }
    
    /**
     * Get the item of the enemy
     * @return Item that the enemy is holding
     */
    public Item getItem()
    {
        return item;
    }
    
    /**
     * Makes the enemy attack
     * @param c character to be attacked
     */
    @Override
    public void attack(Character c)
    {
        // Generate a random amount of damage
        Random generator = new Random();
        int damage = generator.nextInt(10)+1;
        
        // Determine whether the attack hits or misses
        Random hitOrMiss = new Random();
        int rng = hitOrMiss.nextInt(2);
        if (rng == 1)
        {
            c.takeDamage(damage);
            System.out.println(c.getName() + " took " + damage + " damage! ");
        }
        else
        {
            System.out.println("The attack on " + c.getName() + " missed!");
        }
    }
    
}
