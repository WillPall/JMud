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
