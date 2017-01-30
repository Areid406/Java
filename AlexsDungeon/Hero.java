// Name        : Alexander Reid
// Username    : AReid
// Description : Represents the state of the hero in the game. It keeps track of where the hero is, 
//             : the image used to draw the hero, and how many keys and gems the hero has collected. 
//             : This class plays sound effects related to collecting or using items. 

public class Hero
{
	private Config config;
    private int x;
    private int y;
    private int [] items = new int [Item.values().length];
    private String heroImage;
    //private int keys, gems;
    
    // Create a new Hero object at the specified location using the default image.
    // NOTE: x and y are indexes in the dungeon, NOT the StdDraw drawing location.  
    public Hero(Config config2, int x, int y)
    {
   		this.x = x * config2.cellPixels() + config2.cellPixels()/2;
   		this.y = y * config2.cellPixels() + config2.cellPixels()/2;
        this.config = config2;
        heroImage = config2.heroImage();
    }

    // Change to the image used to display this hero. 
    public void setImage(String image)
    {
        heroImage = image;
    }
    
    // Draw the Hero at its current location.
    // Be sure to convert the hero's x and y indexes to a StdDraw drawing location. 
    public void draw()
    {
    	StdDraw.picture(x, y, heroImage);
    }
    
    // Attempts to use the specified item.
    // This consumes one of the specified items (if the hero has one).
    // If successful, also plays sound effect for using the item.
    // If a given item has no sound effect, don't play anything (but don't crash).
    // Returns true if item was available, false otherwise.
    public boolean attemptToUse(Item item)
    {
    	if(getNumItems(item) > 0)
        {
    		//decrements that type of item enumerated in the Item class
    		if(config.itemWasConsumed(item))
    		{	
    			items[item.ordinal()]--;
    			StdAudio.play(config.mapItemToConsumedSound(item));
    			
    		}else{
    			StdAudio.play(config.mapItemToUseSound(item));
    		}	
        	return true;
        }else
        return false;
    }
           
    // Returns how many of the specified item the hero currently has. 
    public int getNumItems(Item item)
    {
    	return items[item.ordinal()];
    }
        
    // Called when the hero takes an item from the dungeon.
    // Also plays sound effect for collecting this item.
    // If a given item has no sound effect, don't play anything (but don't crash).
    public void collectItem(Item item) 
    {
    	items[item.ordinal()]++;
    	
    	if(!config.mapItemToCollectSound(item).equalsIgnoreCase(""))
    	{
    		StdAudio.play(config.mapItemToCollectSound(item));
    	}
    	
    	
    }
    
    // Returns the current x-index of the hero in the dungeon. 
    public int getX()
    {
    	int X = x / config.cellPixels();
        return X;
    }
    
    // Returns the current y-index of the hero in the dungeon.
    public int getY()
    {
    	int Y = y / config.cellPixels();
        return Y;
    }
    
    // Move the hero to the specified x-index and y-index
    public void setLocation(int x, int y)
    {
    	this.x = x * config.cellPixels() + config.cellPixels()/2;
    	this.y = y * config.cellPixels() + config.cellPixels()/2;
    }

    
    // Test program for Hero, feel free to change for your own testing needs
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

        // Create a default girl hero at (0, 0)
        Hero hero1 = new Hero(config, 0, 0);        
        hero1.draw();
        System.out.printf("hero1, location (%d, %d)\n", hero1.getX(), hero1.getY());
        
        // Test out adding gems and keys
        System.out.printf("hero1, gems = %d, keys = %d\n", hero1.getNumItems(Item.GEM), hero1.getNumItems(Item.KEY));
        hero1.collectItem(Item.GEM);
        System.out.printf("hero1, gems = %d, keys = %d\n", hero1.getNumItems(Item.GEM), hero1.getNumItems(Item.KEY));
        hero1.collectItem(Item.KEY);
        System.out.printf("hero1, gems = %d, keys = %d\n", hero1.getNumItems(Item.GEM), hero1.getNumItems(Item.KEY));
        hero1.collectItem(Item.KEY);
        System.out.printf("hero1, gems = %d, keys = %d\n", hero1.getNumItems(Item.GEM), hero1.getNumItems(Item.KEY));
        hero1.collectItem(Item.GEM);
        System.out.printf("hero1, gems = %d, keys = %d\n", hero1.getNumItems(Item.GEM), hero1.getNumItems(Item.KEY));
        
        // Now use some items, pausing between so we can hear any sound effects
        for (int i = 0; i < 3; i++)
        {
            System.out.printf("hero1, using key = %5b, gems = %d, keys = %d\n", hero1.attemptToUse(Item.KEY), hero1.getNumItems(Item.GEM), hero1.getNumItems(Item.KEY));
            StdDraw.show(1000);
        }        
        for (int i = 0; i < 3; i++)
        {
            System.out.printf("hero1, using gem = %5b, gems = %d, keys = %d\n", hero1.attemptToUse(Item.GEM), hero1.getNumItems(Item.GEM), hero1.getNumItems(Item.KEY));
            StdDraw.show(1000);
        }
                
        // Change to the girl into a boy and move up one
        hero1.setImage("boy.png");
        hero1.setLocation(0, 1);
        hero1.draw();
        StdDraw.show(0);
        
        // Create a boy hero at (1, 0)
        Hero hero2 = new Hero(config, 1, 0);
        hero2.setImage("boy.png");
        hero2.draw();
        StdDraw.show(0);
        System.out.printf("hero2, location (%d, %d)\n", hero2.getX(), hero2.getY());
    }
            
}
