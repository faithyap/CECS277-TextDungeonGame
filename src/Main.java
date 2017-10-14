import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This program simulates a Dungeon Master, a game where the user has to navigate
 * themselves through a map, battling monsters and picking up items on the way to
 * find the exit of the dungeon.
 * 
 * @author Faith Y.
 * @version 1.0
 * @since 2/17/2016
 */
public class Main
{
    public static void main(String[] args)
    {
        Hero user = null;
        Level l = new Level();
        File f = new File("hero.dat");
        Scanner input = new Scanner(System.in);
        if (f.exists())
        {
            try
            {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
                user = (Hero) in.readObject();
                in.close();
                l.generateLevel(user.getLevel());
            } catch (IOException i)
            {
                System.out.println("File could not be processed.");
            }
            catch (ClassNotFoundException c)
            {
                System.out.println("Class could not be found.");
            }
        }
        else
        {
            // Prompt the user to enter their name
            System.out.print("What is your name, traveler? ");
            String userName = input.nextLine();
        
            // Prompt the user to enter their quip
            System.out.print("Wonderful! Now, tell me your catch phrase: ");
            String userQuip = input.nextLine();
        
            // Create the first level
            Point userStart = new Point(0, 0);
            l.generateLevel(1);
            userStart = l.findStartLocation();
        
            // Create a Character out of the user's information
            user = new Hero(userName, userQuip, userStart); 
       
            // Begin the game
            System.out.println("Welcome to the Dungeon, " + userName + ".");
        }
        
        do
        {
            System.out.println("\nDungeon Map: ");
            l.displayMap(user.getLocation());
            System.out.println("Your current stats:");
            System.out.println("HP: " + user.getHp());
            System.out.println("Level: " + user.getLevel());
            System.out.println("Gold: " + user.getGold());
            
            // Ask the user which direction to go
            int choice = askDirection();
            char room = ' ';
            if (choice == 1)
            {
                room = user.goNorth(l);
            }
            else if (choice == 2)
            {
                room = user.goSouth(l);
            }
            else if (choice == 3)
            {
                room = user.goEast(l);
            }
            else if (choice == 4)
            {
                room = user.goWest(l);
            }
            else
            {
                System.out.println("Invalid input.");
            }
            
            // Find out what is in the room the user went into
            // 1. Room F: Go to the next level
            if (room == 'f')
            {
                //System.out.println("You have found the exit!");
                //System.out.println("Moving to next floor...");
                if (user.getLevel() == 3)
                {
                    System.out.println("Congratulations! You have exited the dungeon!");
                    user.display();
                    break;
                }
                else 
                {
                    System.out.println("You have found the exit!");
                    System.out.println("Moving to next floor...");
                    user.increaseLevel();
                    l.generateLevel(user.getLevel());
                    user.setLocation(l.findStartLocation());
                    try
                    {
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("hero.dat"));
                        out.writeObject(user);
                        out.close();
                    } catch (IOException i)
                    {
                        System.out.println("File could not be processed.");
                    }
                }
            }
            
            // 2. Room M: Encounter a monster
            else if (room == 'm')
            {
                EnemyGenerator m = new EnemyGenerator();
                Enemy enemy = m.generateEnemy(user.getLevel());
                
                // Tell the user they encountered an enemy
                System.out.println(user.getName() + " has encountered " + enemy.getName() + ".");
                String choice2 = enemyEncounter(user);
                
                // 1. Run Away
                if (choice2.equals("1"))
                {
                    System.out.println("Ahhh! *runs away into random direction*");
                    Point userCurrentLocation = user.getLocation();
                    // Randomly generate a number that will correspond to the user's direction
                    Random generator = new Random();
                    int randomNum = generator.nextInt(4)+1;
                    // Determine which direction the user goes
                    if (randomNum == 1)
                    {
                        room = user.goNorth(l);
                    }
                    else if (randomNum == 2)
                    {
                        room = user.goSouth(l);
                    }
                    else if (randomNum == 3)
                    {
                        room = user.goEast(l);
                    }
                    else if (randomNum == 4)
                    {
                        room = user.goWest(l);
                    }
                }
                
                // 2. Attack
                else if (choice2.equals("2"))
                {
                    do
                    {
                        enemy.attack(user);
                        user.attack(enemy);
                        // Make both the user and enemy say their quip if the enemy dies
                        if (enemy.getHp() <= 0)
                        {
                            System.out.println(enemy.getName() + " says " + enemy.getQuip() + "!");
                            System.out.println(user.getName() + " says " + user.getQuip() + "!");
                            System.out.println("Received " + enemy.getGold() + " gold for beating them.");
                            user.collectGold(enemy.getGold());
                            System.out.println("They have also dropped a " + enemy.getItem().getName() + "!");
                            System.out.println("Would you like to pick it up?");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            
                            int droppedItem = 0;
                            do
                            {
                                try
                                {
                                    droppedItem = input.nextInt();
                                } catch(InputMismatchException i)
                                {
                                    input.next();
                                    System.out.println("Invalid entry enter again.");
                                }
                                if (droppedItem < 1 || droppedItem > 2)
                                {
                                    System.out.println("Cannot enter number lower or higher.");
                                }
                            } while(droppedItem < 1 || droppedItem > 2);
                            if (droppedItem == 1)
                            {
                                if (user.getItems().size() >= 3)
                                {
                                    System.out.println("You may only carry three items.");
                                    System.out.println("Would you like to discard an item?");
                                    System.out.println("1. Yes");
                                    System.out.println("2. No");
                                    int discard = 0;
                                    do
                                    {
                                        try
                                        {
                                            discard = input.nextInt();
                                        } catch(InputMismatchException i)
                                        {
                                            input.next();
                                            System.out.println("Invalid entry enter again.");
                                        }
                                        if (discard < 1 || discard > 2)
                                        {
                                            System.out.println("Cannot enter number lower or higher.");
                                        }
                                    } while (discard < 1 || discard > 2);
                                    
                                    if (discard == 1)
                                    {
                                        System.out.println("Which item would you like to discard?");
                                        for (int j = 0; j < user.getItems().size(); j++)
                                        {
                                            System.out.println(j+1 + ". " + user.getItems().get(j).getName());
                                        }
                                        int whichItem = 0;
                                        do
                                        {
                                            try
                                            {
                                                whichItem = input.nextInt();
                                            } catch(InputMismatchException i)
                                            {
                                                input.next();
                                                System.out.println("Invalid entry enter again.");
                                            }
                                            if (whichItem < 1 || whichItem > user.getItems().size())
                                            {
                                                System.out.println("Cannot enter number lower or higher.");
                                            }
                                        } while(whichItem < 1 || whichItem > user.getItems().size());
                                        System.out.println("You chose to discard " + user.getItems().get(whichItem-1).getName());
                                        user.removeItem(whichItem-1);
                                        user.pickUpItem(enemy.getItem());
                                        System.out.println("You have picked up " + enemy.getItem().getName() + ".");
                                    }
                                    else
                                    {
                                        System.out.println("You have left the " + enemy.getItem().getName() + " behind.");
                                    }
                                }
                                else
                                {
                                    user.pickUpItem(enemy.getItem());
                                    System.out.println("You have picked up " + enemy.getItem().getName() + ".");
                                }
                            }
                            else if (droppedItem == 2)
                            {
                                System.out.println("You have left the " + enemy.getItem().getName() + " behind.");
                            }
                        }
                    } while (enemy.getHp() > 0);
                }
                
                // 3. Use Health Potion
                else if (choice2.equals("3"))
                {
                    user.heal(50);
                    System.out.println("You have used a health potion.");
                    System.out.println("You have healed to full hp!");
                    
                    do
                    {
                        enemy.attack(user);
                        user.attack(enemy);
                        
                        // Make both the user and enemy say their quip if the enemy dies
                        if (enemy.getHp() <= 0)
                        {
                            System.out.println(enemy.getName() + " says " + enemy.getQuip() + "!");
                            System.out.println(user.getName() + " says " + user.getQuip() + "!");
                            System.out.println("Received " + enemy.getGold() + " gold for beating them.");
                            user.collectGold(enemy.getGold());
                            System.out.println("They have also dropped a " + enemy.getItem().getName() + "!");
                            System.out.println("Would you like to pick it up?");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            int droppedItem = 0;
                            do
                            {
                                try
                                {
                                    droppedItem = input.nextInt();
                                } catch(InputMismatchException i)
                                {
                                    input.next();
                                    System.out.println("Invalid entry enter again.");
                                }
                                if (droppedItem < 1 || droppedItem > 2)
                                {
                                    System.out.println("Cannot enter number lower or higher.");
                                }
                            } while(droppedItem < 1 || droppedItem > 2);
                            
                            if (droppedItem == 1)
                            {
                                if (user.getItems().size() >= 3)
                                {
                                    System.out.println("You may only carry three items.");
                                    System.out.println("Would you like to discard an item?");
                                    System.out.println("1. Yes");
                                    System.out.println("2. No");
                                    int discard = 0;
                                    do
                                    {
                                        try
                                        {
                                            discard = input.nextInt();
                                        } catch(InputMismatchException i)
                                        {
                                            input.next();
                                            System.out.println("Invalid entry enter again.");
                                        }
                                        if (discard < 1 || discard > 2)
                                        {
                                            System.out.println("Cannot enter number lower or higher.");
                                        }
                                    } while(discard < 1 || discard > 2);
                                    if (discard == 1)
                                    {
                                        System.out.println("Which item would you like to discard?");
                                        for (int j = 0; j < user.getItems().size(); j++)
                                        {
                                            System.out.println(j+1 + ". " + user.getItems().get(j).getName());
                                        }
                                        int whichItemNum = 0;
                                        do
                                        {
                                            try
                                            {
                                                whichItemNum = input.nextInt();
                                            } catch(InputMismatchException i)
                                            {
                                                input.next();
                                                System.out.println("Invalid entry enter again.");
                                            }
                                            if (whichItemNum < 1 || whichItemNum > user.getItems().size())
                                            {
                                                System.out.println("Cannot enter number lower or higher.");
                                            }
                                        } while(whichItemNum < 1 || whichItemNum > user.getItems().size());
                                        System.out.println("You chose to discard " + user.getItems().get(whichItemNum-1).getName());
                                        user.removeItem(whichItemNum-1);
                                        user.pickUpItem(enemy.getItem());
                                        System.out.println("You have picked up " + enemy.getItem().getName() + ".");
                                    }
                                    else
                                    {
                                        System.out.println("You have left the " + enemy.getItem().getName() + " behind.");
                                    }
                                }
                                else
                                {
                                    user.pickUpItem(enemy.getItem());
                                    System.out.println("You have picked up " + enemy.getItem().getName() + ".");
                                }
                            }
                            else if (droppedItem == 2)
                            {
                                System.out.println("You have left the " + enemy.getItem().getName() + " behind.");
                            }
                            else
                            {
                                System.out.println("Invalid input.");
                            }
                        }
                    } while (enemy.getHp() > 0);
                }
            }
            
            // 3. Room I: Encounter an item
            else if (room == 'i')
            {
                ItemGenerator i = new ItemGenerator();
                Item item = i.generateItem();
                
                // Ask the user whether they want to pick up the item or sell it
                System.out.println("You found " + item.getName() + ".");
                String itemDecision = itemDecision();
                if (itemDecision.equals("1"))
                {
                    if (user.getItems().size() < 3)
                    {
                        user.pickUpItem(item);
                    }
                    else
                    {
                        System.out.println("You may only carry three items.");
                        System.out.println("Would you like to discard an item?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        int discard = 0;
                        do
                        {
                            try
                            {
                                discard = input.nextInt();
                            } catch(InputMismatchException ime)
                            {
                                System.out.println("Invalid entry enter again.");
                            }
                            if (discard < 1 || discard > 2)
                            {
                                System.out.println("Cannot enter number lower or higher.");
                            }
                        } while(discard < 1 || discard > 2);
                        
                        if (discard == 1)
                        {
                            System.out.println("Which item would you like to discard?");
                            for (int j = 0; j < user.getItems().size(); j++)
                            {
                                System.out.println(j+1 + ". " + user.getItems().get(j).getName());
                            }
                            int whichItemNum = 0;
                            do
                            {
                                try
                                {
                                    whichItemNum = input.nextInt();
                                }   
                                catch(InputMismatchException ime)
                                {
                                    System.out.println("Invalid entry enter again.");
                                }
                                if (whichItemNum < 1 || whichItemNum > user.getItems().size());
                                {
                                    System.out.println("Cannot enter number lower or higher.");
                                }
                            } while(whichItemNum < 1 || whichItemNum > user.getItems().size());
                            System.out.println("You chose to discard " + user.getItems().get(whichItemNum-1).getName());
                            user.removeItem(whichItemNum-1);
                            user.pickUpItem(item);
                            System.out.println("You have picked up " + item.getName() + ".");
                        }
                        else
                        {
                            System.out.println("You have left the " + item.getName() + " behind.");
                        }
                    }
                }
                else if (itemDecision.equals("2"))
                {
                    System.out.println("You may only sell on starting spots, which are marked with an s.");
                    System.out.println("What would you like to do?");
                    System.out.println("1. Get the item anyway");
                    System.out.println("2. Leave the item behind");
                    int decision = 0;
                    do
                    {
                        try
                        {
                            decision = input.nextInt();
                        } catch (InputMismatchException ime)
                        {
                            input.next();
                            System.out.println("Invalid entry enter again.");
                        }
                        if (decision < 1 || decision > 2)
                        {
                            System.out.println("Cannot enter number lower or higher.");
                        }
                    } while(decision < 1 || decision > 2);
                    
                    if (decision == 1)
                    {
                        if (user.getItems().size() < 3)
                        {
                            user.pickUpItem(item);
                        }
                        else
                        {
                            System.out.println("You may only carry three items.");
                            System.out.println("Would you like to discard an item?");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            int discard = 0;
                            do
                            {
                                try
                                {
                                    discard = input.nextInt();
                                } catch (InputMismatchException ime)
                                {
                                    input.next();
                                    System.out.println("Invalid entry enter again.");
                                }
                                if (discard < 1 || discard > 2)
                                {
                                    System.out.println("Cannot enter number lower or higher.");
                                }
                            } while(discard < 1 || discard > 2);
                            
                            if (discard == 1)
                            {
                                System.out.println("Which item would you like to discard?");
                                for (int j = 0; j < user.getItems().size(); j++)
                                {
                                    System.out.println(j+1 + ". " + user.getItems().get(j).getName());
                                }
                                int whichItemNum = 0;
                                do
                                {
                                    try
                                    {
                                        decision = input.nextInt();
                                    } catch(InputMismatchException ime)
                                    {
                                        input.next();
                                        System.out.println("Invalid entry enter again.");
                                    }
                                    if (decision < 1 || decision > user.getItems().size())
                                    {
                                        System.out.println("Cannot enter number lower or higher.");
                                    }
                                } while (decision < 1 || decision > user.getItems().size());
                                System.out.println("You chose to discard " + user.getItems().get(whichItemNum-1).getName());
                                user.removeItem(whichItemNum-1);
                                user.pickUpItem(item);
                                System.out.println("You have picked up " + item.getName() + ".");
                            }
                            else
                            {
                                System.out.println("You have left the " + item.getName() + " behind.");
                            }
                        }
                    }
                    else
                    {
                        System.out.println("You have left the " + item.getName() + " behind.");
                    }
                }
            }
            
            // 4. Room S: Start point/save point
            else if (room == 's')
            {
                if (user.getItems().size() != 0)
                {
                    // Ask the user if they would like to sell their items
                    System.out.println("Would you like to sell any items?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int decision = 0;
                    do
                    {
                        try
                        {
                            decision = input.nextInt();
                        } catch (InputMismatchException i)
                        {
                            input.next();
                            System.out.println("Invalid entry enter again.");
                        }
                        if (decision < 1 || decision > 2)
                        {
                            System.out.println("Cannot enter number lower or higher.");
                        }
                    } while(decision < 1 || decision > 2);
                    
                    // 1. SELL ITEMS
                    if (decision == 1)
                    {
                        do
                        {
                            System.out.println("Which items would you like to sell?");
                            for (int j = 0; j < user.getItems().size(); j++)
                            {
                                System.out.println(j+1 + ". " + user.getItems().get(j).getName());
                            }
                            
                            // Actually sell the item
                            int sellItem = 0;
                            do
                            {
                                try
                                {
                                    sellItem = input.nextInt();
                                } catch (InputMismatchException i)
                                {
                                    input.next();
                                    System.out.println("Invalid entry enter again.");
                                }
                                if (sellItem < 1 || sellItem > user.getItems().size())
                                {
                                    System.out.println("Cannot enter number lower or higher.");
                                }
                            } while(sellItem < 1 || sellItem > user.getItems().size());
                            System.out.println("You have sold " + user.getItems().get(sellItem-1).getName() + " for " + user.getItems().get(sellItem-1).getValue() + " gold.");
                            user.collectGold(user.getItems().get(sellItem-1).getValue());
                            System.out.println("The item has been sold for " + user.getItems().get(sellItem-1).getValue() + " gold!");
                            user.removeItem(sellItem-1);
                            
                            // Exit the loop if there are no more items to be sold
                            if (user.getItems().size() == 0)
                            {
                                break;
                            }
                            
                            // Ask the user if they wish to sell more items
                            System.out.println("Would you like to sell another item?");
                            System.out.println("1. Yes");
                            System.out.println("2. No");
                            do
                            {
                                try
                                {
                                    decision = input.nextInt();
                                } catch (InputMismatchException i)
                                {
                                    input.next();
                                    System.out.println("Invalid entry enter again.");
                                }
                                if (decision < 1 || decision > 2)
                                {
                                    System.out.println("Cannot enter number lower or higher.");
                                }
                            } while(decision < 1 || decision > 2);
                        } while (decision == 1);
                    }
                }
                else
                {
                    System.out.println("You have no items in your possession to sell.");
                }
                
            }
            if (user.getHp() <= 0)
            {
                System.out.println("Your character ran out of health!");
                System.out.println("GAME OVER ;_;");
            }
        } while (user.getHp() > 0);
    }
    
    public static int askDirection()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Which direction would you like to go?");
        System.out.println("1. North");
        System.out.println("2. South");
        System.out.println("3. East");
        System.out.println("4. West");
        int decision = 0;
        do
        {
            try
            {
                decision = input.nextInt();
            } catch (InputMismatchException i)
            {
                input.next();
                System.out.println("Invalid entry enter again.");
            }
            if (decision < 1 || decision > 4)
            {
                System.out.println("Cannot enter number lower or higher.");
            }
        } while (decision < 1 || decision > 4); 
        return decision;
    }
    
    public static String enemyEncounter(Hero u)
    {
        // Ask the user what they wish to do
        Item potion = new Item("Health Potion", 25);
        boolean hasPotion = false;
        
        // Check if the user has a potion
        for (int i = 0; i < u.getItems().size(); i++)
        {
            if (u.getItems().get(i).getName().equals("Health Potion") && u.getItems().get(i).getValue() == 25)
            {
                hasPotion = true;
            }
        }
        if (hasPotion == true)
        {
            int decision = 0;
            int potionIndex = 0;
            System.out.println("What do you wish to do?");
            System.out.println("1. Run Away");
            System.out.println("2. Attack");
            System.out.println("3. Use Health Potion");
            Scanner input = new Scanner(System.in);
            do
            {
                try
                {
                    decision = input.nextInt();
                } catch(InputMismatchException i)
                {
                    System.out.println("Invalid entry enter again.");
                }
                if (decision < 1 || decision > 3)
                {
                    System.out.println("Cannot enter number lower or higher.");
                }
            } while (decision < 1 || decision > 3);
            if (decision == 3)
            {
                for (int i = 0; i < u.getItems().size(); i++)
                {
                    if (u.getItems().get(i).getName().equals("Health Potion"))
                    {
                        potionIndex = i;
                    }
                }
                u.removeItem(potionIndex);
            }
            return Integer.toString(decision);
        }
        else
        {
            int decision = 0;
            System.out.println("What do you wish to do?");
            System.out.println("1. Run Away");
            System.out.println("2. Attack");
            Scanner input = new Scanner(System.in);
            do
            {
                try
                {
                    decision = input.nextInt();
                } catch (InputMismatchException i)
                {
                    input.next();
                    System.out.println("Invalid entry enter again.");
                }
                if (decision < 1 || decision > 2)
                {
                    System.out.println("Cannot enter number lower or higher.");
                }
            }while(decision < 1 || decision > 2);
            return Integer.toString(decision);
        }
    }
    
    public static String itemDecision()
    {
        int decision = 0;
        // Ask what the user wants to do with the item
        System.out.println("What would you like to do with the item?");
        System.out.println("1. Keep the item");
        System.out.println("2. Sell the item");
        Scanner input = new Scanner(System.in);
        do
        {
            try
            {
                decision = input.nextInt();
            } catch (InputMismatchException i)
            {
                input.next();
                System.out.println("Invalid entry enter again.");
            }
            if (decision < 1 || decision > 2)
            {
                System.out.println("Cannot enter number lower or higher.");
            }
        } while(decision < 1 || decision > 2);
        return Integer.toString(decision);
    }
}