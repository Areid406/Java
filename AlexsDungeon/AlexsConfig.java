// Provides the defaults of sizes, sounds, and images for a game.
// Also controls how keyboard input is mapped to an action in the game.
// By subclassing this class, you could create a new "skin" for the game.
//
// NOTE: This is the version for the Dungeon 2 assignment.
// 
// Do NOT modify this file.

public class AlexsConfig
{
    // Translates a letter hit by the player into the action that that
    // key causes to happen in the game. Currently this is just the
    // cardinal direction that the hero is attempting to move in.
    // If the character isn't a direction, returns INVALID.
    public Action mapKeyToAction(char ch)
    {
        switch (ch)
        {
            case 'w':  return Action.NORTH;
            case 's':  return Action.SOUTH;
            case 'a':  return Action.WEST;
            case 'd':  return Action.EAST;
            case 'q':  return Action.QUIT;
            default :  return Action.INVALID;                
        }
    }
    
    // Maps a specified item type to the image that is used to display it
    public String mapItemToImage(Item item)
    {
        switch (item)
        {
            case KEY:     return "item-key.png";
            case GEM:     return "item-egg.png";
            case PICKAXE: return "gun.png";
            default:      return "";
        }
    }
    
    // Maps a specified item type to the sound file played when it is collected
    public String mapItemToCollectSound(Item item)
    {
        switch (item)
        {
            case KEY:     return "collect-key.wav";
            case GEM:     return "collect-gem.wav";
            case PICKAXE: return "collect-pickaxe.wav";            
            default:      return "";
        }
    }
    
    // Maps a specified item type to the sound file played when an item is used    
    public String mapItemToUseSound(Item item)
    {
        switch (item)
        {
            case PICKAXE: return "blast.wav";
            default:      return "";
        }
    }

    // Maps a specified item type to the sound file played when an item has been consumed     
    public String mapItemToConsumedSound(Item item)
    {
        switch (item)
        {
            case KEY:     return "consume-key.wav";
            case PICKAXE: return "consume-pickaxe.wav";
            default:      return "";
        }
    }

    // Returns the cell size in pixels
    public int cellPixels()
    {
        return 64;
    }
    
    // Returns filename of the sound played when walking
    public String walkSound()
    {
        return "walk.wav";
    }
    
    // Returns filename of the sound played when hero hits a wall
    public String hitWallSound()
    {
        return "wall.wav";
    }
        
    // Returns filename of the default image used for the hero
    public String heroImage()
    {
        return "character-chicken.png";
    }

    // Returns filename of the wall image 
    public String wallImage()
    {
        return "tile-brickwall.png";
    }
        
    // Returns filename of the passageway image 
    public String passageImage()
    {
        return "tile-passage.png";
    }

    // Returns filename of an open door image 
    public String openDoorImage()
    {
        return "tile-door-open.png";
    }

    // Returns filename of an open door image 
    public String closedDoorImage()
    {
        return "tile-door-closed.png";
    }
    
    // Returns an array of 2 or more images corresponding to the number of
    // times a rock must be pickaxed before it is cleared. 
    public String [] rockImages()
    {
        final int NUM_ROCK_IMAGES = 10;        
        String [] result = new String[NUM_ROCK_IMAGES];
        for (int i = 0; i < NUM_ROCK_IMAGES; i++)
        {
            result[i] = String.format("osama%d.png", i);
        }        
        return result;
    }
    
    // Decides whether using an item has caused it to be consumed.
    // For some items this is always true, others such as the pickaxe can be reused.
    public boolean itemWasConsumed(Item item)
    {
        switch (item)
        {
            case KEY:     return true;                     // Keys are always used up
            case PICKAXE: return (Math.random() < 0.05);   // Pickaxe has a 5% chance of breaking                    
            default:      return false;                
        }
    }
    
}
