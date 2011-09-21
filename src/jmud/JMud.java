package jmud;

public class JMud
{
	private static Server server = null;
	private static CommandHandler commandHandler = null;
	
	private static void init()
	{
		server = new Server();
		commandHandler = new CommandHandler();
	}
	
	public static void log( String str )
	{
		// TODO: add actual file logging
		System.out.println( str );
	}
	
	public static synchronized Server getServer()
	{
		return server;
	}
	
	public static synchronized CommandHandler getCommandHandler()
	{
		return commandHandler;
	}
	
	public static void main( String args[] )
	{
		JMud.init();
		JMud.getServer().start();
	}
}
