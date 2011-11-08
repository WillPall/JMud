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
import java.util.ArrayList;

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
	private final int PORT = 4444;
	/**
	 * Socket descriptor for the server instance
	 */
	private ServerSocket serverSocket = null;
	/**
	 * Keeps track of whether the server is currently running and accepting
	 * connections.
	 * 
	 * Is used to allow admins to stop the server from within the game.
	 */
	private boolean isRunning;
	/**
	 * Contains the descriptor to each currently connected client
	 */
	private ArrayList<ClientDescriptor> descriptors = new ArrayList<ClientDescriptor>(1);

	/**
	 * Constructs a server with default settings.
	 */
	private Server()
	{
		JMud.log( "Starting server ..." );
		// TODO: load server maps, mobs, items, etc.
		try
		{
			serverSocket = new ServerSocket( PORT );
			JMud.log( "Success!\r\nListening for connections on port " + PORT );
			
			isRunning = true;
		}
		catch( BindException be )
		{
			JMud.log( "Error binding to port " + PORT + ". Is a server already running?" );
		}
		catch( IOException e )
		{
			JMud.log( "IOException opening a new ServerSocket on " + PORT );
		}
	}
	
	private static class instanceHolder
	{
		public static Server instance = new Server();
	}
	
	/**
	 * Gets a new instance of Server.
	 * 
	 * @return A Server instance
	 */
	public static Server getInstance()
	{
		return instanceHolder.instance;
	}
	
	public ClientDescriptor findClientByCharacterName( String name )
	{
		ClientDescriptor target = null;
		synchronized( this )
		{
			for( ClientDescriptor d : descriptors )
			{
				if( d.getCharacter().getName().equalsIgnoreCase( name ) )
				{
					target = d;
					break;
				}
			}
		}
		
		return target;
	}
	
	/**
	 * Sends message to each client connected to the server
	 * 
	 * @param message Message to send to clients
	 */
	public void sendToAll( String message )
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
	protected void closeConnection( ClientDescriptor descriptor )
	{
		synchronized( this )
		{
			descriptors.remove( descriptor );
		}
		
		// TODO: remove this. it's debug
		printDesc();
	}
	
	// TODO: remove this debug method
	private void printDesc()
	{
		JMud.log( "Descriptors:" );
		
		for( ClientDescriptor d : descriptors )
		{
			JMud.log( "\t" + d.getId() );
		}
	}
	
	/**
	 * Forces the server to shutdown.
	 * 
	 * This does not shutdown the server gracefully. The shutdown will happen
	 * immediately and some things may not get saved correctly.
	 */
	public synchronized void shutdown()
	{
		isRunning = false;
		try
		{
			new Socket( serverSocket.getInetAddress(), serverSocket.getLocalPort() ).close();
		}
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the server thread and begins accepting connections
	 */
	public void run()
	{	
		try
		{
			while( isRunning )
			{
				Socket socket = serverSocket.accept();
				
				// check to see if the accept failed because we're shutting down
				if( !isRunning )
				{
					// TODO: add a more graceful shutdown sequence
					sendToAll( "Server shutting down. Goodbye!\r\n" );
					for( ClientDescriptor d : descriptors )
					{
						// TODO: this causes clientdescriptors to throw an error because
						//		 Socket.readline() doesn't work for closed sockets
						d.disconnect();
					}
					return;
				}
				
				JMud.log( "New connection from " + socket.getInetAddress() );
				
				ClientDescriptor d = new ClientDescriptor( socket );
				synchronized( this )
				{
					descriptors.add( d );
				}
				d.start();
				printDesc();
			}
			
		}
		catch( IOException e )
		{
			JMud.log( "Unknown IO Exception. Here's your stack trace: " );
			e.printStackTrace();
		}
	}
}
