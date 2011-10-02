package jmud.command;

/*
 * Commands
 * Lists the commands that are available to the player
 * TODO: Set them up for immortals
 */

import java.util.LinkedList;
import java.util.Vector;

import jmud.ClientDescriptor;
import jmud.Command;
import jmud.CommandHandler;
import jmud.JMud;

public class Commands extends CommandTemplate
{
	private ClientDescriptor handler;
	private String args;
	
	public Commands( ClientDescriptor handler, String args )
	{
		this.handler = handler;
		this.args = args;
	}
	
	public boolean exec()
	{
		Vector<Command> commandList = JMud.getCommandHandler().getCommandList();
		
		this.handler.sendMessage( "{I{wAvailable commands:{x\r\n" );
		
		int col = 0;
		int i = 0;
		while( ( col < 4 ) && ( i < commandList.size() ) )
		{
			if( commandList.get( i ).getName() != "Commands" )
			{
				// We've gotta make it pretty
				if( commandList.get( i ).getName().length() < 5 )
					this.handler.sendMessage( commandList.get( i ).getName() + "\t" );
				else
					this.handler.sendMessage( commandList.get( i ).getName() + " " );
				
				col++;
			}
			
			i++;
			if( col == 4 )
			{
				col = 0;
				this.handler.sendMessage( "\r\n" );
			}
		}
		
		this.handler.sendMessage( "\r\n" );
		return true;
	}
}

