package jmud;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Vector;

import jmud.command.CommandTemplate;

public class CommandHandler
{
	// TODO: change this to some sort of list that will hold
	//  a command, its aliases, and the level required to use
	//  the command
	private Vector<Command> commands = new Vector<Command>(1);
	
	CommandHandler()
	{
		initCommandList();
	}
	
	public void initCommandList()
	{
		// TODO: few of these actually are implemented
		//		 these were pulled from the old JMud

		commands.add( new Command( "Commands", 0 ) );
		commands.add( new Command( "Say", 0 ) );
		/*
		// List of possible commands
		//  NOTE: suffixes work for all commands,
		//        so more important commands go to the top
		this.commandList.add( "Adjective" );
		
		this.commandList.add( "Character" );
		this.commandList.add( "Commands" );
		
		this.commandList.add( "Drop" );
		
		this.commandList.add( "Give" );
		this.commandList.add( "Greet" );
		
		this.commandList.add( "Inventory" );
		this.commandList.add( "Kill" );		
		this.commandList.add( "Look" );
		this.commandList.add( "Prompt" );
		
		this.commandList.add( "Reply" );
		this.commandList.add( "Remove" );
		
		this.commandList.add( "Say" );
				
		this.commandList.add( "Tell" );
		this.commandList.add( "Take" );
		this.commandList.add( "Trash" );
				
		this.commandList.add( "Who" );
		this.commandList.add( "Wear" );
		
		// List of immortal commands
		if( handler.getCharacter().getLevel() >= 60 )
		{
			this.commandList.add( "Create" );
			this.commandList.add( "Levelup" );
			// TODO: fix this when we get non-blocking input
			this.commandList.add( "Shutdown" );
		}*/
	}
	
	public Vector<Command> getCommandList()
	{
		return commands;
	}
	
	public boolean doCommand( String command, String args, ClientDescriptor handler )
	{	
		for( int i = 0; i < commands.size(); i++ )
		{
			if( commands.get( i ).getName().toLowerCase().startsWith( command.toLowerCase() ) )
			{
				Constructor<CommandTemplate> constr;
				CommandTemplate cmd;
				Class<CommandTemplate> c;

				try
				{
					c = (Class<CommandTemplate>) Class.forName( "jmud.command." + commands.get( i ).getName() );

					constr = c.getConstructor( new Class[]{ ClientDescriptor.class, String.class } );
					cmd = (CommandTemplate) constr.newInstance( handler, args );

					cmd.exec();
				}
				catch( InstantiationException ie )
				{
					// DEBUG:
					System.out.println( "Couldn't create instance of Class \"" + commands.get( i ) + "\"" );
				}
				catch( IllegalAccessException iae )
				{
					// DEBUG:
					System.out.println( "Illegal Access Exception\nCouldn't create instance of Class \""
							+ commands.get( i ) + "\"" );
				}
				catch( InvocationTargetException ite )
				{
					// DEBUG:
					System.out.println( "Invocation Target Exception\nCouldn't create instance of Class \"" );
				}
				catch( IllegalArgumentException iarge )
				{
					// DEBUG:
					System.out.println( "Illegal Argument Exception\nCouldn't create instance of Class \"" );
				}
				catch( ClassNotFoundException cnf )
				{
				}
				catch( NoSuchMethodException nme )
				{
				}

				return true;
			}
		}

		// Command not found
		handler.sendMessage( "Huh? (type \"commands\" for a list of commands)\r\n" );
		return false;
	}
}
