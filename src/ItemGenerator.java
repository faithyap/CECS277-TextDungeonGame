
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class generates an item, with information read from a text file.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */
public class ItemGenerator
{
    // ArrayList that holds the possible items
    private ArrayList<Item> itemList;
    
    /**
     * Constructor method for the ItemGenerator class
     */
    public ItemGenerator()
    {
        itemList = new ArrayList<Item>();
        try
        {
            Scanner read = new Scanner(new File("ItemList.txt"));
            do
            {
                String readLine = read.nextLine();
                String[] item = readLine.split(",");
                itemList.add(new Item(item[0], Integer.parseInt(item[1])));
            } while (read.hasNextLine());
            read.close();
            
        } catch(FileNotFoundException f)
        {
            System.out.println("File was not found.");
        }
    }
    
    /**
     * Generates a new object of the class Item
     * @return new Item that has been constructed
     */
    public Item generateItem()
    {
        Random generator = new Random();
        int num = generator.nextInt(itemList.size());
        Item i = new Item(itemList.get(num).getName(), itemList.get(num).getValue());
        return i;
    }
    
}
