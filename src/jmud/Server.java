package jmud;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server extends Thread
{
	protected static final int PORT = 4444;
	protected static ServerSocket serverSocket = null;
	protected static boolean isRunning = true;
	protected static Vector<ClientDescriptor> descriptors = new Vector<ClientDescriptor>(1);
	
	public static void sendToAll( String str )
	{
		for( ClientDescriptor d : descriptors )
		{
			d.sendMessage( str );
		}
	}
	
	protected static void closeConnection( ClientDescriptor d )
	{
		descriptors.remove( d );
		printDesc();
	}
	
	private static void printDesc()
	{
		JMud.log( "Descriptors:" );
		
		for( ClientDescriptor d : descriptors )
		{
			JMud.log( "\t" + d.getId() );
		}
	}
	
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
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			JMud.log( "Unknown IO Exception. Here's your stack trace: " );
			e.printStackTrace();
		}
	}
}
