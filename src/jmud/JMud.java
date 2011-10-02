package jmud;

import java.util.ArrayList;

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
	}
	
	public static void log( String str )
	{
		// TODO: add actual file logging
		System.out.println( str );
	}
	
	public static synchronized CommandHandler getCommandHandler()
	{
		return commandHandler;
	}
	
	public static synchronized RoomList getRoomList()
	{
		return roomList;
	}
	
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
