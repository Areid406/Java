// Name        : Alexander Reid
// Username    : AReid
// Description : Represent an individual cell location in the dungeon. This is the base class in the inheritance. 
//             : As such it defines the API that all the other Cell-related data types implement. 
//             : The Cell type itself is quite basic, it represents an impassable wall with a given background image. 

public class Cell
{
    protected String image;
    
    // Create a new cell in the dungeon with the specified image.
    // The Cell base class represents an impassable part of the dungeon.
    public Cell(String image)
    {
        this.image = image;
    }

    // Default constructor, added so it compiles before you fix the previous constructor.
    public Cell()
    {
    }
    
    // Attempts to add the specified item to this cell.
    // Since the Cell class is impassable, it can never store items.
    // Returns true if item was added, false otherwise.
    public boolean attemptToAdd(Item item)
    {
        return false;          
    }

    // Attempt to move the specified hero to this location.
    // Since the Cell class is impassable, this always fails.
    public boolean attemptToMove(Hero hero)
    {        
        return false;
    }
    
    public boolean containsItem(Item item)
    {
    		return false;
    }
    
    // Draw at the specified position in the dungeon.
    // NOTE: x and y are indexes in the dungeon, NOT the StdDraw drawing location.  
    public void draw(Config config, int x, int y)
    {
        StdDraw.picture( (x * config.cellPixels()) + (config.cellPixels() * .5), (y * config.cellPixels()) + (config.cellPixels() * .5), image);
    }

    // Test program for Cell, feel free to change for your own testing needs
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
        
        // Draw a wall at various locations 
        Cell wall = new Cell(config.wallImage());       
        wall.draw(config, 0, 0);
        wall.draw(config, 1, 1);
        
        // Wall with a different image
        Cell wall2 = new Cell(config.passageImage());
        wall2.draw(config, 2, 2);
    }

}
