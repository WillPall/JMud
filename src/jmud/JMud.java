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

import jmud.entity.Person;
import jmud.network.Server;

/**
 * A Java MUD server project.
 * 
 * @author Will Pall
 */
public class JMud
{
	private static Server server = null;
	
	protected static RoomList roomList = null;
	
	private static void init()
	{
		server = Server.getInstance();
		
		roomList = RoomList.getInstance();
		roomList.load();
		
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
