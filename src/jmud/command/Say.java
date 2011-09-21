package jmud.command;

/*
 * Say
 * Say command - sends chat out
 */
import jmud.CharacterHandler;
import jmud.JMud;
import jmud.Server;

public class Say extends CommandTemplate
{
	private CharacterHandler handler;
	private String args;
	
	public Say( CharacterHandler handler, String args )
	{
		this.handler = handler;
		this.args = args;
	}
	
	public boolean exec()
	{
		if( !args.equals( "" ) )
		{
			//strip !%!NAME!%! from args
			args = args.replaceAll( "!%!NAME!%!", "!NAME!" );
			// replace {x in args to {g to make it look good
			args = args.replaceAll( "\\{x", "{x{g" );
			
			JMud.getServer();
			// TODO: restore the sendToRoom when rooms are implemented
			//this.handler.sendToRoom( "{x!%!NAME!%! says, {g\"" + args + "{x{g\"{x\r\n" );
			//this.handler.sendMessage( "{xYou say, {g\"" + args + "{x{g\"{x\r\n" );
			Server.sendToAll( args );
		}
		else
			this.handler.sendMessage( "\r\nUsage: say [phrase-to-say]\r\n\r\n" );
			
		return true;
	}
}