/*************************************************************************
 * Name        : Alex Reid & Zack Burke
 * Username    : areid & zburke
 * Description : Line server program, multi-threaded version that
 *               supports multiple persistent clients. 
 *               This program's job is to spawn a worker thread to handle 
 *               an incoming client and then carrying on listening for new clients.
 *               
 *************************************************************************/

import java.io.*;
import java.net.*;

public class LineServer
{
	public static void main(String[] args) 
	{		
		// Default port, optionally specify as first command line argument
		int port = 5000;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		
		try
		{
			// Setup the port number the server is listening on
			@SuppressWarnings("resource")
			ServerSocket serverSock = new ServerSocket(port);
			while (true)
			{
				// Wait for a client to connect
				Socket sock = serverSock.accept();
				//Creat a new thread and worker for each client
				Thread t = new Thread(new LineServerWorker(sock));
				t.start();
			}			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}

}