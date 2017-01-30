/*************************************************************************
 * Name        : Alex Reid & Zack Burke
 * Username    : areid & zburke
 * Description : LineServer's multi-threaded server, worker thread.
 *               This program's run() method handles one client
 *               for as long as they want to draw lines.
 *               Requires access to the Lines object so it can add new lines 
 *               or clear all the lines if a client requests it. 
 *               The class also keeps track of the total number of clients. 
 *               The worker waits for a single line of text from the client 
 *               and always responds with a single line. 
 *               
 *************************************************************************/

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LineServerWorker implements Runnable
{

	private Socket 			sock 	= null;
	private PrintWriter     writer 	= null;
	private BufferedReader 	reader 	= null;
	private static int clientCount = 0;
	//Lines object required to add new lines and clear them as well.
	private Lines linesList = new Lines();

	LineServerWorker(Socket sock)
	{
		try
		{
			//Creates a socket, reader, and writer for each Client and prints a standard message upon connecting.
			this.sock 	= sock;
			this.writer = new PrintWriter(sock.getOutputStream(), true);
			this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			increment();
			System.out.println("HELLO: " + " (" + sock + ")");
			System.out.println("CONNECTIONS: " + " (" + clientCount + ")");


		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}
	
	//This method updates the number of Clients in a synchronized fashion, prevents multiple clients from 
	//simultaneously modifying the client count
	public synchronized void increment()
	 {
		clientCount++;
	 }
	//Similar method, just handles the decrementing function.
	public synchronized void decrement()
	 {
		clientCount--;
	 }
	// This method handles the communication with a single client
	public void run()
	{
		String command = "";
		try
		{          
			try{
				while(true)
				{	
					//Whole line is read in
					command = reader.readLine();   

					//If nothing is sent, break out of the loop. This shouldn't ever really happen in normal conditions.
					if(command == null)
						break;

					//Scanner object to help go through what we read in with "reader.readLine" above
					Scanner scanner = new Scanner(command);
					
					//The first bit of String from what was read in is assigned as the command's "Name"
					// i.e. GET, ADD, QUIT, CLEAR
					String commandName = scanner.next();
					
		
					String result = "";

					//Returns the linesList size, number of clients, and number of lines currently existing
					if(commandName.equals("GET"))
					{
						result = "" + linesList.size() +  " " + clientCount + " " + linesList;
						writer.println(result);
					}
					//Returns OK and clears the linesList
					else if(commandName.equals("CLEAR"))
					{
						result = "OK";
						linesList.clear();
						writer.println(result);
					}

					//Reurns OK, creates the new Line object based on what the client has passed in, and adds it to linesList
					else if(commandName.equals("ADD"))
					{
						System.out.println(commandName + " ( " + sock + ")");
						result = "OK";
						Line line = new Line(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(),
								scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
						linesList.add(line);
						writer.println(result);
					}
					
					//Returns OK, decrements the client count by 1, and prints a standard goodbye/hangup message.
					else if(command.equals("QUIT"))
					{
						result = "OK";
						decrement();
						writer.println(result);
						
						System.out.println("GOODBYE: " + " (" + sock + ")");
						System.out.println("CONNECTIONS: " + " (" + clientCount + ")");
						
					}
					scanner.close();
				}
			}
			catch(SocketException e)
			{
				 // This happens if the client just terminates without politely sending a QUIT command
				// Still decrements and says goodbye since the client has disconnected 
				decrement();
				System.out.println("GOODBYE: " + " (" + sock + ")");
				System.out.println("CONNECTIONS: " + " (" + clientCount + ")");
				
			}

			// Close the stream and the connection			
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