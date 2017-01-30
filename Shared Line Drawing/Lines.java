import java.util.*;

/*************************************************************************
 * Name        : Alex Reid & Zack Burke
 * Username    : areid & zburke
 * Description : The Lines class handles the ArrayList that is used to hold
 * 				 all the line objects, with getter, size, and clear methods
 * 				 that modify or relay information about the lines ArrayList
 *************************************************************************/

public class Lines {

	public static ArrayList<Line> lines = new ArrayList<Line>();
	
	//Synchronized to avoid mutliple threads trying to add lines at the same time
	public synchronized void add(Line line)
	{
		lines.add(line);
	}
	
	//Turns the lines' numerical data into String to allow sending between Server and Client
	public synchronized String toString()
	{
		String lineString = "";
		for(int i = 0; i < lines.size(); i++)
		{
			lineString += lines.get(i).toString();
		}
		return lineString;
	}
	
	//Returns the arraylist size
	public int size()
	{
		return lines.size();
	}
	
	//Clears the list
	public void clear()
	{
		lines.clear();
	}
}
