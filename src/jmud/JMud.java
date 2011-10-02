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

/**
 * A Java MUD server project.
 * 
 * @author Will Pall
 */
public class JMud
{
	private static Server server = null;
	private static CommandHandler commandHandler = null;
	
	protected static RoomList roomList = null;
	
	private static void init()
	{
		server = new Server();
		commandHandler = new CommandHandler();
		
		roomList = new RoomList();
		roomList.load();
		
		// TODO: change this. this is just for testing
		Room room = new Room( 0, "Starting Room", "This is a starting room." );
		Person p = new Person( "Test NPC", "This is a test NPC", room );
		room.addEntity( p );
		roomList.add( room );
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
	 * Gets the command handler for client commands and input.
	 * 
	 * @return Server's command handler
	 */
	public static synchronized CommandHandler getCommandHandler()
	{
		return commandHandler;
	}
	
	/**
	 * Gets the server room list.
	 * 
	 * @return The room list
	 */
	public static synchronized RoomList getRoomList()
	{
		return roomList;
	}
	
	/**
	 * Gets the server object.
	 * 
	 * @return The server object
	 */
	public static synchronized Server getServer()
	{
		return server;
	}
	
	public static void main( String args[] )
	{
		JMud.init();
		JMud.getServer().start();
	}
}
