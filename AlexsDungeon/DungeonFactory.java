// Name        :
// Username    :
// Description :

import java.io.*;
import java.util.Scanner;

public class DungeonFactory
{
	// Create a Dungeon object reading from standard input.
	// You do NOT need to modify this method.
	public static Dungeon createFromStdIn(Config config)
	{
		// Read in the size and number of keys/gems
		int width  = StdIn.readInt();
		int height = StdIn.readInt();

		// We read in a count of the items to initially add to the dungeon.
		// NOTE: we skip the first location since this is Item.NONE
		int [] numItems = new int[Item.values().length];
		for (int i = 1; i < numItems.length; i++)
			numItems[i] = StdIn.readInt();

		// Read in the initial location of the hero
		int heroX = StdIn.readInt();
		int heroY = StdIn.readInt();

		// Construct the dungeon layout
		Dungeon dungeon = new Dungeon(config, width, height);

		Hero hero = new Hero(config, heroX, heroY);
		dungeon.addHero(hero);

		// Setup all the cells in the dungeon
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				dungeon.set(x, height - y - 1, StdIn.readString()); 
			}
		}

		// Add the right number of items of each type at random locations
		for (int i = 1; i < numItems.length; i++)
		{
			for (int j = 0; j < numItems[i]; j++)
			{
				dungeon.addItem(Item.values()[i]);
			}
		}

		return dungeon;
	}

	// Create a Dungeon object reading from the specified file.
	// Note: This method is largely similar to the other method but uses file I/O instead of standard input.
	public static Dungeon createFromFile(Config config, String filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(filename));
		Dungeon dungeon = null;

		while(scanner.hasNext())
		{
			// Read in the size and number of keys/gems
			int width  = scanner.nextInt();
			int height = scanner.nextInt();

			// We read in a count of the items to initially add to the dungeon.
			// NOTE: we skip the first location since this is Item.NONE
			int [] numItems = new int[Item.values().length];
			for (int i = 1; i < numItems.length; i++)
				numItems[i] = scanner.nextInt();

			// Read in the initial location of the hero
			int heroX = scanner.nextInt();
			int heroY = scanner.nextInt();

			// Construct the dungeon layout
			dungeon = new Dungeon(config, width, height);

			Hero hero = new Hero(config, heroX, heroY);
			dungeon.addHero(hero);

			// Setup all the cells in the dungeon
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					dungeon.set(x, height - y - 1, scanner.next()); 
				}
			}

			// Add the right number of items of each type at random locations
			for (int i = 1; i < numItems.length; i++)
			{
				for (int j = 0; j < numItems[i]; j++)
				{
					dungeon.addItem(Item.values()[i]);
				}
			}
		}
		scanner.close(); 
        return dungeon;
	}

}
