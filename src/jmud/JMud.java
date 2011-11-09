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

import java.util.ArrayList;
import java.util.HashMap;

import jmud.entity.Person;
import jmud.entity.Character;
import jmud.network.Server;
import jmud.network.ClientHandler;

/**
 * A Java MUD server project.
 * 
 * @author Will Pall
 */
public class JMud
{
	private static Server server = null;
	
	protected static RoomList roomList = null;
	
	private static PlayerList players = null;
	
	private static void init()
	{
		server = Server.getInstance();
		
		roomList = RoomList.getInstance();
		roomList.load();
		
		players = PlayerList.getInstance();
		
		// TODO: change this. this is just for testing
		Room room0 = new Room( 0, "Starting Room", "This is a starting room." );
		Room room1 = new Room( 1, "Northern Room", "This is a room north of the starting room." );
		roomList.add( room0 );
		roomList.add( room1 );
		Person p = new Person( "Test NPC", "This is a test NPC", room0 );
		room0.addEntity( p );
		RoomExit e = new RoomExit( "n", 1 );
		room0.addExit( e );

		RoomExit e2 = new RoomExit( "s", 0 );
		room1.addExit( e2 );
		
	}
	
	/**
	 * Logs message.
	 * 
	 * @param message Message to log
	 */
	public static void log( String message )
	{
		// TODO: add actual file logging
		System.out.println( message );
	}
	
	/**
	 * Adds a new player to the player list and links that player to their
	 * {@link ClientHandler}.
	 * 
	 * @param name The name of the player
	 * @param handler The {@link ClientHandler} associated with the player
	 */
	// TODO: this should add a player from the DB, not by name
	public static void addPlayer( String name, ClientHandler handler )
	{
		for( Player p : players )
		{
			p.sendMessage( name + " has joined the game.\r\n" );
		}
		players.add( new Player( new Character( name, "noob", roomList.getRoomById( 0 ) ), handler ) );
	}
	
	/**
	 * Gets the server room list.
	 * 
	 * @return The room list
	 */
	/*public synchronized static RoomList getRoomList()
	{
		return roomList;
	}*/
	
	/**
	 * Gets the server object.
	 * 
	 * @return The server object
	 */
	/*private synchronized static Server getServer()
	{
		return server;
	}*/
	
	public static void main( String args[] )
	{
		JMud.init();
		server.start();
	}
}
