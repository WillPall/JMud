package jmud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CharacterHandler extends Thread
{
	// Socket info for this connection
	private Socket socket;
	// reader to read input that the socket recieves
	private BufferedReader in;
	// printwriter to send output to client
	private PrintWriter out;
	
	CharacterHandler( Socket socket ) throws IOException
	{
		this.socket = socket;
		
		// set "in" to the socket's input stream
		in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
		// set "out" to the socket's output stream
		out = new PrintWriter( new OutputStreamWriter( socket.getOutputStream() ) );
	}
	
	public void sendMessage( String message )
	{
		out.print( ChatColor.colorFormat( message + "{x" ) );
		out.flush();
	}
	
	private boolean doCommand( String command )
	{
		String com[] = command.trim().split( " ", 2 );
		String args = "";
		
		if( com.length > 1 )
			args = com[1];
		
		return JMud.getCommandHandler().doCommand( com[0], args, this );
	}
	
	public void run()
	{
		String command = null;
		
		try
		{
			sendMessage( "Welcome to the server!" );
			
			while( !socket.isClosed() &&
				   ( ( command = in.readLine() ) != null ) )
			{
				doCommand( command );
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
