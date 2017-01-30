// Name        :
// Username    :
// Description :

public class CellRock extends CellPassage
{
	private String [] rockImages;
	private String cellImage;
	private String currentRockState;
	private Config config;
	private boolean broken;
	private int hits;


	public CellRock(Config config2, String passageImage) 
	{
		
		cellImage = passageImage;
		this.config = config2;
		rockImages = this.config.rockImages();
		broken = false;
		hits = 0;
		currentRockState = rockImages [0];

	}

	@Override
	public boolean attemptToMove(Hero hero)
	{
		if(broken)
			return true;
		if(!broken && hero.attemptToUse(Item.PICKAXE))
		{
			if(hits < config.rockImages().length - 1)
			{
				hits++;
				currentRockState = rockImages[hits];
			}else{
				broken = true;
			}
		}
		return false;

	}
	@Override
	public boolean attemptToAdd(Item item)
	{
		return false;
	}

	public void draw(Config config, int x, int y)
	{
		int X = x * config.cellPixels() + config.cellPixels()/2;
		int Y = y * config.cellPixels() + config.cellPixels()/2;

		StdDraw.picture(X, Y, cellImage);
		StdDraw.picture(X, Y, currentRockState);

	}
}

