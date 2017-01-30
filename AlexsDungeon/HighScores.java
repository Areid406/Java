// Name        : Alexander Reid
// Username    : AReid
// Description :

import java.io.*;
import java.util.*;

public class HighScores
{
	private String filename;
	private ArrayList <String> names;
	private ArrayList <Double >times;

	private int maxSize;

	// Construct based on the given filename containing the current high scores.
	// We store at most maxSize high scores.
	// If the filename is not found, then we are starting a new file.
	public HighScores(String filename, int maxSize)
	{

		Scanner scanner;

		this.filename = filename;
		this.maxSize = maxSize;
		names = new ArrayList <String>();
		times = new ArrayList <Double>();

		try
		{	
			scanner = new Scanner(new File(filename));
			while(scanner.hasNext())
			{
				names.add(scanner.next());
				times.add(scanner.nextDouble());
			}	
			scanner.close();
		}
		catch(FileNotFoundException e)
		{

		}
	}

	// Returns how many high scores are in our list
	public int getSize()
	{
		return times.size();
	}

	// Returns the name of the ith person in the high score list.
	// i = 0 would be the fastest person, i = 1 the next fastest, etc.
	public String getName(int i)
	{   
		return names.get(i);
	}

	// Returns the time of the ith person in the high score list.
	// i = 0 would be the fastest person, i = 1 the next fastest, etc.
	public double getTime(int i)
	{   
		return times.get(i);
	}

	// Add the specified winner to the correct position in the high score list.
	// Does not add this name and time if the time is >= the current slowest time.
	// Returns the 0-based index of the position the winner was added at.
	public int addWinner(String name, double time)
	{

		int position = -1;
		if(times.size() == 0)
		{
			times.add(time);
			names.add(name);
			return 0;
		}
		else
		{
			for(int i = 0; i < times.size(); i++ )
			{
				if(time <= times.get(i))
				{
					position = i;
					break;
				}	
			}
			
			if(position == -1)
			{
				position = times.size();
			}	
			
			times.add(position, time);
			names.add(position, name);

			if(times.size() > maxSize)
			{
				names.remove(maxSize);
				times.remove(maxSize);
				
				if(time >= times.get(getSize() - 1))
				{
					return -1;
				}
			}	
			
		}	
		return position;

		
	}

	// Write the high scores back to disk.
	// Returns true on success, false otherwise.
	public boolean save() 
	{
		try
		{
			PrintWriter writer = new PrintWriter(filename);
			for (int i = 0;	i <	times.size(); i++)	
			{
				String scores = String.format("%s	%.2f", names.get(i),times.get(i));
				writer.println(scores);	
			}
			writer.close();
			return true;

		}
		catch(FileNotFoundException e)
		{
		}
		return false;
	}

	// Test main
	public static void main(String [] args)
	{
		HighScores scores = new HighScores(args[0], Integer.parseInt(args[1]));
		String [] names = {"Abe", "Bob", "Carol", "Dave", "Edith", "Frank", "George", "Hank", "Iaian", "Jake", "Keith", "Lisa"};
		double [] times = {10.0 , 5.0 ,  15.5   , 23.7  , 17.2   , 23.9   , 5.0     , 6.6   ,  98.93 , 52.5  , 4.99   , 23.7 }; 
		scores.save();
	}

}
