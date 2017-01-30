// Name        : Alexander Reid
// Username    : AReid
// Description : This class extends the CellPassage class. It is a cell that the hero can sometimes  
//             : walk through if the door is already open or if the hero has a key. 
//             : If the door is open, it can contain either a gem or a gem (but not both)

public class CellDoor extends CellPassage
{
	private String imageOpen;
	private String image;
	private boolean locked;
	private Item item = Item.NONE;


	// Create a new cell in the dungeon with the specified image.
	// The CellDoor class represents an door that can be closed (locked) or open.
	// NOTE: an open door is allowed to hold an item (e.g. a gem or key).    
	public CellDoor(String imageOpen, String imageClosed, boolean locked)
	{
		this.imageOpen = imageOpen;
		this.locked = locked;

		//Sets the image to open or closed depending on what locked boolean is passed in.
		if(locked)
			this.image = imageClosed;
		else
			this.image = imageOpen;
	}

	// Attempt to move the hero to this door location.
	// This will always work if the door is already open.
	// If it is still locked, the hero has to use a key to open door.
	// Returns true if hero made it through, false otherwise
	@Override
	public boolean attemptToMove(Hero hero)
	{       
		//Checks if the door is locked
		if(locked)
		{
			//If locked, Hero will attempt to use a KEY and open the door if Hero has such a KEY.
			if(hero.attemptToUse(Item.KEY))
			{
				this.open();
				return true;
			}else
				return false;
		}else{
			//If not locked, and IF there is an item present, Hero will collect said item.
			if(!(this.item == Item.NONE || this.item == null)){
	    		hero.collectItem(this.item);
	    		this.item = Item.NONE;
	    	}
	    	return true;
		}	

	}

	// Draw at the specified position in the dungeon.
	// Also draws any items contained at this location.
	// NOTE: x and y are indexes in the dungeon, NOT the StdDraw drawing location.
	@Override
	public void draw(Config config, int x, int y)
	{
		int X = x * config.cellPixels() + config.cellPixels()/2;
		int Y = y * config.cellPixels() + config.cellPixels()/2;

		StdDraw.picture(X, Y, this.image);

		if(!(this.item == Item.NONE || this.item == null))
			StdDraw.picture(X, Y, config.mapItemToImage(this.item));
	}

	// Attempts to add the specified item to this cell.
	// A CellDoor can store at most one item, but only if it is unlocked and open.
	// Returns true if item was added, false otherwise.
	@Override
	public boolean attemptToAdd(Item item)
	{
		//checks if locked first
		if(locked)
			return false;
		else{
			//next, if unlocked/open, checks if an item is present and adds the item if true
			if((this.item == Item.NONE || this.item == null)){
	    		this.item = item; 
	    		return true; 
	    	}else
	        return false;
		}
	}

	// Open the door, this is needed for testing of CellDoor before Hero is implemented. 
	public void open()
	{
		if(locked)
		{
			locked = false;
			this.image = imageOpen; 
		}
	}
	
	public boolean containsItem(Item item)
	{
		if(this.item == item)
    		return true;
    	else
    		return false;	
	}

	// Test program for CellDoor, feel free to change for your own testing needs
	public static void main(String [] args)
	{        
		// The config object stores defaults for sizes, sounds, and images
		Config config = new Config();

		// Fake a dungeon that is 3 x 3
		final int WIDTH = 3;
		final int HEIGHT = 3;
		StdDraw.setCanvasSize(WIDTH * config.cellPixels(), HEIGHT * config.cellPixels());
		StdDraw.setXscale(0, WIDTH * config.cellPixels());
		StdDraw.setYscale(0, HEIGHT * config.cellPixels());

		// Create a door that is initially locked
		CellDoor door = new CellDoor("door_open.png", "door_closed.png", true);      
		door.draw(config, 0, 0);        
		System.out.printf("Adding gem to closed door, result = %5b\n", door.attemptToAdd(Item.GEM));
		System.out.printf("Adding key to closed door, result = %5b\n", door.attemptToAdd(Item.KEY));
		door.open();
		door.draw(config, 0, 1);
		System.out.printf("Adding gem to open door,   result = %5b\n", door.attemptToAdd(Item.GEM));
		System.out.printf("Adding key to open door,   result = %5b\n", door.attemptToAdd(Item.KEY));
		door.draw(config, 0, 2);

		// Create a door that is initially unlocked
		CellDoor door2 = new CellDoor("door_open.png", "door_closed.png", false);      
		door2.draw(config, 1, 0);
		door2.open();
		door2.draw(config, 1, 1);
		System.out.printf("Adding key to open door2,  result = %5b\n", door2.attemptToAdd(Item.KEY));
		System.out.printf("Adding gem to open door2,  result = %5b\n", door2.attemptToAdd(Item.GEM));
		door2.draw(config, 1, 2);
	}

}

