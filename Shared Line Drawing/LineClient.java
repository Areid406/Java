/*************************************************************************
 * Name        : Alex Reid & Zack Burke
 * Username    : AReid & ZBurke
 * Description :  This is the program that does the actual drawing. 
 * 				  The client periodically polls the server (approximately every 100ms) 
 * 			      using a GET command to obtain the current set of lines and the number 
 *                of clients currently connected.
 *************************************************************************/

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.*;

public class LineClient
{


	public static void main(String [] args)
	{
		// Default value for host and port
		String host = "localhost";
		int port = 5000;
		int red = (int)(Math.random()* 256);
		int green = (int)(Math.random()* 256);
		int blue = (int)(Math.random()* 256);
		double x0 = 0 , y0 = 0;
		double x1 = 0 , y1 = 0;


		// Read in optional different hostname, port, name
		if(args.length == 3 || args.length == 4 || args.length > 5)
		{	
			System.out.println("LineClient [server name] [server port] [<red, 0-255> <green, 0-255> <blue, 0-255>]");
			return;
		}	

		//Assigns host, port, and RGB values based on given command line arguments
		if (args.length > 0)
			host = args[0];
		if (args.length > 1)
			port = Integer.parseInt(args[1]);
		if(args.length == 5)
		{	
			red   = Integer.parseInt(args[2]);
			green = Integer.parseInt(args[3]);
			blue  = Integer.parseInt(args[4]);
		}	

		boolean pressed = true; 
		
		try
		{

			// Create a socket connection
			Socket sock = new Socket(host, port);

			// Get ready to read in a string from the server
			InputStreamReader stream = new InputStreamReader(sock.getInputStream());
			BufferedReader reader = new BufferedReader(stream);


			// NOTE: PrintWriter is set to auto-flush each line of output
			PrintWriter 	  writer = new PrintWriter(sock.getOutputStream(), true);	


			while (true)
			{					
				StdDraw.clear();	
				
				//Initial X & Y position are assigned upon mouse click
				//Pressed variable keeps track of whether it has been pressed ot not
				if(StdDraw.mousePressed() && pressed)
				{
					pressed = !pressed;
					x0 = StdDraw.mouseX();
					y0 = StdDraw.mouseY();
				}	
				
				//Upon letting off the mouse, the second X & Y position is gained and the pressed variable is reset
				else if(!StdDraw.mousePressed() && !pressed)
				{
					pressed = !pressed;
					x1 = StdDraw.mouseX();
					y1 = StdDraw.mouseY();	
				
					Line line = new Line(x0, y0, x1, y1, red, green, blue);
					writer.println("ADD " + line);
					reader.readLine();
				
				}
				
				//Checks for CLEAR or QUIT commands based on key typing
				if(StdDraw.hasNextKeyTyped())
				{
					char key = StdDraw.nextKeyTyped();
					if (key == 'c')
					{
						writer.println("CLEAR");
						reader.readLine();
					}
					else if (key == 'q')
					{
						writer.println("QUIT");
						break;
					}
				}
				//Periodic GET request
				writer.println("GET");
				//Read in what the server returns and figure out how many lines are currently drawn + number of lines
				String words = reader.readLine();
				Scanner scanner = new Scanner(words);
				
				int numLines = scanner.nextInt();
				int numClients = scanner.nextInt();
				
				
				StdDraw.text(.1, .9, "" + numLines);
				StdDraw.text(.1, .87, "" + numClients);
				
				//Loops through, creates each line, then draws
				for(int i = 0; i < numLines; i++)
				{
					Line line = new Line(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(),
							scanner.nextDouble(), scanner.nextInt(),  scanner.nextInt(),  scanner.nextInt());
					line.draw();
				}	
				
				scanner.close();
				StdDraw.show(100);
			}

			System.out.println("CLIENT DONE");

			// Close the reader, writer, and the socket
			reader.close();
			writer.close();
			sock.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}
}