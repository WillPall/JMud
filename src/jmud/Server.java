/**
 * Copyright 2011 Will Pall
 * 
 * This file is part of JMud.
 *
 * JMud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JMud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Server class that handles connecting and communicating with clients.
 * 
 * @author Will Pall
 *
 */
public class Server extends Thread
{
	/**
	 * Port number the server attaches to
	 */
	protected static final int PORT = 4444;
	/**
	 * Socket descriptor for the server instance
	 */
	protected static ServerSocket serverSocket = null;
	/**
	 * Keeps track of whether the server is currently running and accepting
	 * connections.
	 * 
	 * Is used to allow admins to stop the server from within the game.
	 */
	protected static boolean isRunning = true;
	/**
	 * Contains the descriptor to each currently connected client
	 */
	protected static Vector<ClientDescriptor> descriptors = new Vector<ClientDescriptor>(1);

	/**
	 * Constructs a server with default settings.
	 */
	public Server()
	{
		// TODO: do something cool here
	}
	
	/**
	 * Sends message to each client connected to the server
	 * 
	 * @param message Message to send to clients
	 */
	public static void sendToAll( String message )
	{
		for( ClientDescriptor d : descriptors )
		{
			d.sendMessage( message );
		}
	}
	
	/**
	 * Closes the connection with the client associated with descriptor
	 * 
	 * @param descriptor Client descriptor to disconnect
	 */
	protected static void closeConnection( ClientDescriptor descriptor )
	{
		descriptors.remove( descriptor );
		
		// TODO: remove this. it's debug
		printDesc();
	}
	
	// TODO: remove this debug method
	private static void printDesc()
	{
		JMud.log( "Descriptors:" );
		
		for( ClientDescriptor d : descriptors )
		{
			JMud.log( "\t" + d.getId() );
		}
	}
	
	/**
	 * Starts the server thread and begins accepting connections
	 */
	public void run()
	{
		System.out.println( "Starting server ..." );
		
		// TODO: load server maps, mobs, items, etc.
		
		try
		{
			serverSocket = new ServerSocket( PORT );
			JMud.log( "Success!\r\nListening for connections on port " + PORT );
			
			while( isRunning )
			{
				Socket socket = serverSocket.accept();
				
				JMud.log( "New connection from " + socket.getInetAddress() );
				
				ClientDescriptor d = new ClientDescriptor( socket );
				descriptors.add( d );
				d.start();
				printDesc();
			}
		}
		catch( BindException be )
		{
			JMud.log( "Error binding to port " + PORT + ". Is a server already running?" );
		}
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			JMud.log( "Unknown IO Exception. Here's your stack trace: " );
			e.printStackTrace();
		}
	}
}
