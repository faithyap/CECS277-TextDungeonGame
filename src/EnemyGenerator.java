import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class generates an enemy, with information read from a text file.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */
public class EnemyGenerator
{
    // ArrayList to hold the potential enemies
    private ArrayList<Enemy> enemyList;
    
    /**
     * Constructor method for the EnemyGenerator class
     */
    public EnemyGenerator()
    {
        enemyList = new ArrayList<Enemy>();
        try
        {
            Scanner read = new Scanner(new File("EnemyList.txt"));
            do
            {
                String readLine = read.nextLine();
                String[] enemy = readLine.split(",");
                Random gold = new Random();
                int goldValue = gold.nextInt(20)+1;
                Random hpVal = new Random();
                int hpValue = hpVal.nextInt(20)+1;
                enemyList.add(new Enemy(enemy[0], enemy[1], Integer.parseInt(enemy[2]), 1, goldValue, null));
            } while (read.hasNextLine());
            read.close();
        } catch (FileNotFoundException f)
        {
            System.out.println("File was not found.");
        }
    }
    
    /**
     * Creates a new object of the class Enemy
     * @param level level that the hero is in
     * @return Enemy that has been constructed
     */
    public Enemy generateEnemy(int level)
    {
        Random generator = new Random();
        int randomNum = generator.nextInt(enemyList.size());
        ItemGenerator enemyItem = new ItemGenerator();
        Item newItem = enemyItem.generateItem();
        Item enemyI = new Item(newItem.getName(), newItem.getValue());
        Enemy newEnemy = new Enemy(enemyList.get(randomNum).getName(), enemyList.get(randomNum).getQuip(), enemyList.get(randomNum).getHp(), level, enemyList.get(randomNum).getGold(), enemyI);
        return newEnemy;
    }
}
