
import java.io.Serializable;


/**
 * This class creates an Item, which consists of
 * a name and a gold value so the user can sell it.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */
public class Item implements Serializable
{
    // String that holds the name of the item
    private String name;
    // Integer that holds the gold value of the item
    private int goldValue;
    
    /**
     * Constructor that creates a new Item
     * @param n String that holds the name of the item
     * @param v Integer that holds the gold value of the item
     */
    public Item(String n, int v)
    {
        name = n;
        goldValue = v;
    }
    
    /**
     * Gets the name of the item
     * @return String that holds the name of the item
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the gold value of the item
     * @return Integer that holds the gold value of the item
     */
    public int getValue()
    {
        return goldValue;
    }
    
}
