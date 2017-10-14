import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class reads and creates the floor that the user will use as the dungeon map.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */
public class Level
{
    // Variable that will hold the coordinates as the user goes through the map
    private char[][] level;
    
    // Constructor for the class Level
    public Level()
    {
        level = new char[4][4];
    }
    
    /**
     * Reads a text file and creates a floor map based on it
     * @param levelNum integer that holds the floor number to be created
     */
    public void generateLevel(int levelNum)
    {
        try
        {
            Scanner read = new Scanner(new File("Level" + levelNum + ".txt"));
            do
            {
                for (int i = 0; i < 4; i++)
                {
                    String readLine = read.nextLine();
                    String[] readLineArray = readLine.split(" ");
                    for (int j = 0; j < 4; j++)
                    {
                        level[i][j] = readLineArray[j].charAt(0);
                    }
                }
            } while (read.hasNextLine());
            read.close();
        } catch(FileNotFoundException f)
        {
            System.out.println("File not found.");
        }
    }
    
    /**
     * Returns a character value of what type of room the given point is
     * @param p Point that the user is trying to determine
     * @return character of what type of room it is
     */
    public char getRoom(Point p)
    {
        return level[(int) p.getX()][(int) p.getY()];
    }
    
    /**
     * Displays the map, locating the user at a given point
     * @param p Point wherein the user is located
     */
    public void displayMap(Point p)
    {
        System.out.println("-----------");
        for (int i = 0; i < 4; i++)
        {
            System.out.print("| ");
            for (int j = 0; j < 4; j++)
            {
                if (p.getX() == i && p.getY() == j)
                {
                    System.out.print("* ");
                }
                else 
                {
                    System.out.print(level[i][j] + " ");
                }
            }
            System.out.print("|\n");
        }
        System.out.print("-----------\n");
    }
    
    /**
     * Finds the starting location in the generated level.
     * @return Point of the starting location.
     */
    public Point findStartLocation()
    {
        Point p = new Point(0,0);
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (level[i][j] == 's')
                {
                    p = new Point(i,j);
                }
            }
        }
        return p;
    }
}
