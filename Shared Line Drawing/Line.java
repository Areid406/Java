/*************************************************************************
 * Name        : Alex Reid & Zack Burke
 * Username    : areid & zburke
 * Description : A Line object represents a two-dimensional line with a given color. 
 * 				 It knows things like its end points and its color. 
 *               It can do things like return a string representation of itself and draw itself
 *************************************************************************/
import java.awt.Color;

public class Line {
	
	private double x0, y0, x1, y1;
	private int r, g, b;
	String line = "";
	
	//Constructor
	public Line(double x0, double y0, double x1, double y1, int r, int g, int b)
	{
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	//Formats and returns a Line object's X & Y positions and RGB values as String
	public String toString()
	{
		return String.format( "%f %f %f %f %d %d %d ", x0, y0, x1, y1, r, g, b);
	}

	//Draw method
	public void draw()
	{
		Color color = new Color(r, g, b);
		StdDraw.setPenColor(color);
		StdDraw.line(x0, y0, x1, y1);
		//This ensures the Text displayed on the StdDraw window will be black
		StdDraw.setPenColor(StdDraw.BLACK);
	}
	
	

}
