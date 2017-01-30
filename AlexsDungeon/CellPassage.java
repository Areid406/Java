// Name        : Alexander Reid
// Username    : AReid
// Description : An expanded version of the Cell class,  It is a cell that the hero can walk through. 
//             : Depending on the image it is created with, it could be a normal passage or a secret passage.
//			   : It can contain either a gem or a key (but not both).

public class CellPassage extends Cell
{
    private Item item;
    
    // Create a new cell in the dungeon with the specified image.
    // The CellPassage class represents a passable part of the dungeon.
    public CellPassage(String image)
    {
        super(image);
    }
    
    // Default constructor, added so it compiles before you fix the previous constructor.
    public CellPassage()
    {        
    }
    
    // Attempts to add the specified item to this cell.
    // A CellPassage can store at most one item.
    // Returns true if item was added, false otherwise.
    @Override
    public boolean attemptToAdd(Item item)
    
    //If no item is present, assign the passed in item to the current designated space
    {
    	if((this.item == Item.NONE || this.item == null)){
    		this.item = item; 
    		return true; 
    	}else
        return false;
       
    }

    // Attempt to move the specified hero to this location.
    // Also collect any item present at this location.
    // Since the CellPassage class is always passable, this always returns true.
    @Override
    public boolean attemptToMove(Hero hero)
    {        
    	//If an item is present, collect it and change the item to being nothing.
    	if(!(this.item == Item.NONE || this.item == null)){
    		hero.collectItem(this.item);
    		this.item = Item.NONE;
    	}
    	return true;
    }
    
    // Draw at the specified position in the dungeon.
    // Also draws any item contained at this location.
    // NOTE: x and y are indexes in the dungeon, NOT the StdDraw drawing location.
    @Override
    public void draw(Config config, int x, int y)
    {
    	//Draws the cell similarly to Cell class
    	 super.draw(config, x, y);
    	 
    	 //Checks if an item is present and draws it if true.
         if(!(this.item == Item.NONE || this.item == null))
         { 
        	 StdDraw.picture( (x * config.cellPixels()) + (config.cellPixels() * .5), (y * config.cellPixels()) + (config.cellPixels() * .5), config.mapItemToImage(item));
         }
    }

    public boolean containsItem(Item item)
    {
    	if(this.item == item)
    		return true;
    	else
    		return false;
    				
    }
    
    // Test program for CellPassage, feel free to change for your own testing needs
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
        
        // A normal passage 
        CellPassage passage = new CellPassage(config.passageImage());       
        passage.draw(config, 0, 0);
        System.out.printf("Adding gem to passage,  result = %5b\n", passage.attemptToAdd(Item.GEM));
        passage.draw(config, 0, 1);
        System.out.printf("Adding gem to passage,  result = %5b\n", passage.attemptToAdd(Item.GEM));
        System.out.printf("Adding key to passage,  result = %5b\n", passage.attemptToAdd(Item.KEY));

        // Create another passage and add a key
        CellPassage passage2 = new CellPassage(config.passageImage());
        System.out.printf("Adding key to passage2, result = %5b\n", passage2.attemptToAdd(Item.KEY));
        passage2.draw(config, 0, 2);
        
        // A secret passage
        CellPassage secret = new CellPassage(config.wallImage());
        secret.draw(config, 1, 0);
        System.out.printf("Adding key to secret,   result = %5b\n", secret.attemptToAdd(Item.KEY));
        secret.draw(config, 1, 1);
        System.out.printf("Adding gem to passage,  result = %5b\n", secret.attemptToAdd(Item.GEM));
        System.out.printf("Adding key to passage,  result = %5b\n", secret.attemptToAdd(Item.KEY));

        // Create another secret passage and add a key
        CellPassage secret2 = new CellPassage(config.wallImage());
        System.out.printf("Adding key to secret2,  result = %5b\n\n", secret2.attemptToAdd(Item.KEY));
        secret2.draw(config, 1, 2);        
    }
    
}
