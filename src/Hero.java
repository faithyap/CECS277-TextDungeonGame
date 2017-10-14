
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class creates a Hero, which is an extension of the Character
 * class, consisting of a name, quip, and a starting point value.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */

public class Hero extends Character implements Serializable
{
    // ArrayList of items to hold the Hero's item collection
    private ArrayList<Item> items;
    // Point to store the location of the Hero
    private Point location;
    
    /**
     * Constructor method that constructs a new Hero
     * @param n String that holds the name of the hero
     * @param q String that holds the quip of the hero
     * @param start Point wherein the hero starts
     */
    public Hero(String n, String q, Point start)
    {
        super(n, q, 50, 1, 10);
        items = new ArrayList<Item>();
        location = start;
    }
    
    /**
     * Gets the ArrayList of the user's current items
     * @return ArrayList of the user's current items
     */
    public ArrayList<Item> getItems()
    {
        return items;
    }
    
    /**
     * Makes the user pick up the item
     * @param i item to be picked up
     * @return 
     */
    public boolean pickUpItem(Item i)
    {
        items.add(i);
        return true;
    }
    
    /**
     * Removes the item indicated
     * @param i item to be removed
     */
    public void removeItem(Item i)
    {
        items.remove(i);
    }
    
    /**
     * Removes the item at the index located
     * @param index integer that holds the index of the item to be removed
     */
    public void removeItem(int index)
    {
        items.remove(index);
    }
    
    /**
     * Get the location of the hero
     * @return Point holding the location of the hero
     */
    public Point getLocation()
    {
        return location;
    }
    
    /**
     * Moves the hero to a certain location
     * @param p Point holding the location the hero is to be moved to
     */
    public void setLocation(Point p)
    {
        location = p;
    }
    
    /**
     * Makes the user move north on the map
     * @param l Level holding what floor the user is in
     * @return character that determines what type of room the user moves into
     */
    public char goNorth(Level l)
    {
        // x -1
        try
        {
            char room = l.getRoom(new Point((int) location.getX()-1, (int) location.getY()));
            location.move((int) location.getX()-1, (int) location.getY());
            return room;
        } catch (IndexOutOfBoundsException o)
        {
            System.out.println("Seems like you faceplanted into a wall...");
            System.out.println("You can't go that direction!");
        }
        return '1';
    }
    
    /**
     * Makes the user move south on the map
     * @param l Level holding what floor the user is in
     * @return character that determines what type of room the user moves into
     */
    public char goSouth(Level l)
    {
        // x+1
        try
        {
            char room = l.getRoom(new Point((int) location.getX()+1, (int) location.getY()));
            location.move((int) location.getX()+1, (int) location.getY());
            return room;
        } catch (IndexOutOfBoundsException o)
        {
            System.out.println("Seems like you faceplanted into a wall...");
            System.out.println("You can't go that direction!");
        }
        return '1';
    }
    
    /**
     * Makes the user move east on the map
     * @param l Level holding what floor the user is in
     * @return character that determines what type of room the user moves into
     */
    public char goEast(Level l)
    {
        // y+1
        try
        {
            char room = l.getRoom(new Point((int) location.getX(), (int) location.getY()+1));
            location.move((int) location.getX(), (int) location.getY()+1);
            return room;
        } catch (IndexOutOfBoundsException o)
        {
            System.out.println("Seems like you faceplanted into a wall...");
            System.out.println("You can't go that direction!");
        }
        return '1';
    }
    
    /**
     * Makes the user move west on the map
     * @param l Level holding what floor the user is in
     * @return character that determines what type of room the user moves into
     */
    public char goWest(Level l)
    {
        // y-1
        try
        {
            char room = l.getRoom(new Point((int) location.getX(), (int) location.getY()-1));
            location.move((int) location.getX(), (int) location.getY()-1);
            return room;
        } catch (IndexOutOfBoundsException o)
        {
            System.out.println("Seems like you faceplanted into a wall...");
            System.out.println("You can't go that direction!");
        }
        return '1';
    }

    /**
     * Makes the hero do their individual attack
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
