import java.awt.Font;

// Name        : Alexander Reid
// Username    : AReid
// Description : The dungeon class holds the game map, adds in the hero, adds the gems and keys in random fashion
//			   : Also draws the dungeon and executes player actions.

public class Dungeon
{
	private Config config;
	private Cell [][] dungeon;
	private int width;
	private int height;
	private Hero hero;
	private Stats stats;

	// Construct a new dungeon of the specified size.
	// NOTE: The dungeon starts out completely empty and won't be usable 
	// until other methods have been run the constructed object:
	//  1) set() to initialize each Cell in the dungeons 2D array
	//  2) addHero() to put a hero in the dungeon
	//  3) addItem() to add keys and gems to the dungeon   
	public Dungeon(Config config, int width, int height)
	{
		this.config = config;
		dungeon = new Cell [width][height];  
		this.width = width;
		this.height = height;
		stats = new Stats();
	}

	// Adds the specified hero to the dungeon
	public void addHero(Hero hero)
	{
		this.hero = hero; 
	}    

	// Setup a given grid location based on a string:
	//  "W" = wall
	//  "S" = secret passage
	//  "D" = closed door
	//  "O" = open door
	//  "-" = dirt floor
	public void set(int cellX, int cellY, String str)
	{    	
		switch (str)
		{
		case "W": dungeon[cellX][cellY] = new Cell(config.wallImage());
		break;
		case "S": dungeon[cellX][cellY] = new CellPassage(config.wallImage());
		break;
		case "D": dungeon[cellX][cellY] = new CellDoor(config.openDoorImage(), config.closedDoorImage(), true);
		break;
		case "O": dungeon[cellX][cellY] = new CellDoor(config.openDoorImage(), config.closedDoorImage(), false);
		break;
		case "-": dungeon[cellX][cellY] = new CellPassage(config.passageImage());   
		break;
		case "R": dungeon[cellX][cellY] = new CellRock(config, config.passageImage());
		break;
		default : System.out.println("Letter: " + str + " " + cellX + " " + cellY); throw new RuntimeException("Invalid Game Control File!");
		}
	}

	// Add the specified item to a random location in the dungeon.
	// The item can only be added to a cell that is passable (i.e.
	// not a wall or a locked door). The location must not already
	// have an item and items can't be added on top of the hero.     
	public void addItem(Item item)
	{
		int randX;
		int randY;

		do{
			randX = StdRandom.uniform(0, width);
			randY = StdRandom.uniform(0, height);
		}while((randX == hero.getX() && randY == hero.getY()) || !dungeon[randX][randY].attemptToAdd(item));
	}

	// Make the Hero execute the specified action.
	// Currently this is attempting to move north, south, east, or west.
	// If the hero succeeds in moving, play the walking sound.
	// If the action would take the hero into an impassable cell OR 
	// off the dungeon grid, play the hit wall sound.
	public void execute(Action action)
	{    
		int x = hero.getX();
		int y = hero.getY();

		if(action == Action.NORTH)
			y += 1;
		else if( action == Action.SOUTH)
			y -= 1;
		else if(action == Action.EAST)
			x += 1;
		else if(action == Action.WEST)
			x -= 1;
		else 
			return;
		
		//Checks to see if hero will hit a wall or edge of the game map and plays hitWallSound if true.
		if((x + 1 == 0 && action == Action.WEST) || (x == width && action == Action.EAST) || (y + 1 == 0 && action == Action.SOUTH) || (y == height && action == Action.NORTH))
		{
			StdAudio.play(config.hitWallSound());
			return;
		}
		
		//sets the location and plays appropriate sounds depending on if the hero can move
		if(dungeon[x][y].attemptToMove(hero))
		{
			hero.setLocation(x, y);
			StdAudio.play(config.walkSound());
		}else{
			StdAudio.play(config.hitWallSound());
		}
	}
	// Draw all the cell tiles that make up the dungeon.
	// Also draws the hero.
	public void draw()
	{       
		for(int columns = height - 1; columns >= 0; columns--)
		{
			for(int rows = width - 1; rows >= 0; rows--)
			{
				dungeon[rows][columns].draw(config, rows, columns);
				hero.draw();
				StdDraw.setPenColor(StdDraw.GREEN);
				String status = String.format("Gems : %d   Keys: %d  Pickaxes: %d  Time: %.2f", hero.getNumItems(Item.GEM), hero.getNumItems(Item.KEY), hero.getNumItems(Item.PICKAXE), stats.elapsedTime() );
				StdDraw.setFont(new Font("SansSerif", Font.BOLD, 18));
				StdDraw.text((config.cellPixels()* width) / 2, (config.cellPixels() * height) / 1.02, status);
			}
		}

	}
	
	public int getCount(Item item)
	{
		int counter = 0;
		for(int columns = height - 1; columns >= 0; columns--)
		{
			for(int rows = width - 1; rows >= 0; rows--)
			{
				if(dungeon[rows][columns].containsItem(item))
				{
					counter++;
				}
			}
		}
		return counter;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public float getElapsedTime()
	{
		return (float)(this.stats.elapsedTime());
	}

	// Test program for Dungeon, feel free to change for your own testing needs
	public static void main(String [] args)
	{
		// The config object stores defaults for sizes, sounds, and images
		Config config = new Config();

		// Setup a fake 4 x 3 dungeon
		final int WIDTH = 4;
		final int HEIGHT = 3;
		Dungeon dungeon = new Dungeon(config, WIDTH, HEIGHT);
		StdDraw.setCanvasSize(WIDTH * config.cellPixels(), HEIGHT * config.cellPixels());
		StdDraw.setXscale(0, WIDTH * config.cellPixels());
		StdDraw.setYscale(0, HEIGHT * config.cellPixels());

		// Create a dungeon like:
		//  W--W
		//  W---
		//  WDW-
		dungeon.set(0, 0, "W");
		dungeon.set(1, 0, "D");
		dungeon.set(2, 0, "W");
		dungeon.set(3, 0, "-");

		dungeon.set(0, 1, "O");
		dungeon.set(1, 1, "-");
		dungeon.set(2, 1, "-");
		dungeon.set(3, 1, "-");

		dungeon.set(0, 2, "W");
		dungeon.set(1, 2, "-");
		dungeon.set(2, 2, "-");
		dungeon.set(3, 2, "W");

		Hero hero = new Hero(config, 1, 1);
		dungeon.addHero(hero);

		// Add 3 keys and 3 gems at random locations
		for (int i = 0; i < 3; i++)
		{
			dungeon.addItem(Item.KEY);
			dungeon.addItem(Item.GEM);
		}

		// Draw the sample dungeon
		dungeon.draw();
	}   

}
