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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * Represents a client connection and handles socket IO for each connection.
 * 
 * @author Will Pall
 *
 */
public class ClientDescriptor extends Thread
{
	// Socket info for this connection
	private Socket socket;
	// reader to read input that the socket recieves
	private BufferedReader in;
	// printwriter to send output to client
	private PrintWriter out;
	// Character associated with this client
	private Character character;
	
	/**
	 * Creates a new client connection associated with the given Socket.
	 * 
	 * @param socket The new connection to attach
	 * @throws IOException
	 */
	public ClientDescriptor( Socket socket ) throws IOException
	{
		this.socket = socket;
		
		// set "in" to the socket's input stream
		in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		// set "out" to the socket's output stream
		out = new PrintWriter( new OutputStreamWriter( socket.getOutputStream() ) );
	}
	
	/**
	 * Gets the character associated with this client descriptor.
	 * 
	 * @return This client's associated character
	 */
	public Character getCharacter()
	{
		return this.character;
	}
	
	/**
	 * Sends message to the client connected over this handler.
	 * 
	 * @param message Message to send
	 */
	public void sendMessage( String message )
	{
		out.print( ChatColor.colorFormat( message + "{x" ) );
		out.flush();
	}
	
	/**
	 * Disconnects this client from the server.
	 */
	public void disconnect()
	{
		try
		{
			// TODO: make sure this is enough. don't want any stupid memory leaks
			this.socket.close();
		}
		catch( IOException e )
		{
			// DEBUG:
			System.out.println( "Descriptor for character \"" + character.getName() + "\" failed to disconnect. Has it already been disconnected?\r\n" );
		}
	}
	
	/**
	 * Handles client input and dispatches commands to the game.
	 * 
	 * @param input Input from the client
	 * @return True on success. False if the input could not be handled.
	 */
	private boolean processInput( String input )
	{
		String com[] = input.trim().split( " ", 2 );
		String args = "";
		
		if( com.length > 1 )
			args = com[1];
		
		return JMud.getCommandHandler().doCommand( com[0], args, this );
	}
	
	/**
	 * Starts the IO thread for the client.
	 */
	public void run()
	{
		String command = null;
		
		try
		{
			sendMessage( "Welcome to the server!\r\n" );
			sendMessage( "What is your name? " );
			
			// Make sure the player didn't kill the connection before logging
			// in and that their name only has characters 
			while( ( ( command = in.readLine() ) != null ) &&
				   !Pattern.matches( "[a-zA-Z]{4,15}", command.trim() ) )
			{
				command = command.trim();
				sendMessage( "\r\nThat's not a valid name.\r\nNames must be letters only and between 4 and 15 characters long.\r\n\r\nWhat is your name? " );
			}
			
			// player must have killed the connection
			if( command == null )
				return;
			
			// TODO: make this load character info
			character = new Character( command, "This guy is a noob.", JMud.getRoomList().get( 0 ), this );
			
			// Clean the output line
			sendMessage( "\r\nHi " + character.getName() + "!\r\nType \"commands\" for a list of commands.\r\n\r\n" );
			
			while( !socket.isClosed() &&
				   ( ( command = in.readLine() ) != null ) )
			{
				processInput( command );
			}
			
			// TODO: set this to a player name or connection ID
			JMud.log( socket.getInetAddress() + " has disconnected" );
			JMud.getServer();
			// Remove the descriptor from the server
			Server.closeConnection( this );
		}
		catch( IOException ioe )
		{
			// TODO: handle the exception
			ioe.printStackTrace();
		}
	}
}
