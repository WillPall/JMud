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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import jmud.command.CommandTemplate;

/**
 * Handles command input from clients and dispatches each command.
 * 
 * @author Will Pall
 */
public class CommandHandler
{
	// TODO: change this to some sort of list that will hold
	//  a command, its aliases, and the level required to use
	//  the command
	private Vector<Command> commands = new Vector<Command>(1);
	
	/**
	 * Constructs a command handler that represents all commands
	 * on the server.
	 */
	public CommandHandler()
	{
		initCommandList();
	}
	
	private void initCommandList()
	{
		// TODO: few of these actually are implemented
		//		 these were pulled from the old JMud

		commands.add( new Command( "Commands" ) );
		commands.add( new Command( "Look", 0, "look\r\n\tlook [object/person]" ) );
		commands.add( new Command( "Say", 0, "say [phrase-to-say]" ) );
		commands.add( new Command( "Quit", 0, true ) );
		
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
	
	/**
	 * Gets the list of all commands.
	 * 
	 * @return The command list
	 */
	public Vector<Command> getCommandList()
	{
		return commands;
	}
	
	/**
	 * Executes the given command for the client.
	 * 
	 * @param command The client's command
	 * @param args The command arguments
	 * @param descriptor The client descriptor
	 * @return True if the command was successful. False if not.
	 */
	public boolean doCommand( String command, String args, ClientDescriptor descriptor )
	{
		if( args == null )
			args = "";
		
		// First check if the command is related to an exit
		// TODO: is this the best way to do this, gameplay wise?
		//		 what if the exit is named "south", but they want to "say"?
		for( RoomExit e : descriptor.getCharacter().getCurrentRoom().getExits() )
		{
			if( e.getLabel().toLowerCase().startsWith( command.toLowerCase() ) )
			{
				descriptor.getCharacter().moveToRoom( e.getDestination() );
				doCommand( "look", null, descriptor );
				return true;
			}
		}
		
		// Not an exit, check for an actual command
		for( int i = 0; i < commands.size(); i++ )
		{
			// This checks to make sure the command is a suffix, or that the command
			// is the full command name when required
			if( ( commands.get( i ).getName().toLowerCase().startsWith( command.toLowerCase() ) && !commands.get( i ).requiresFullName() ) ||
				commands.get( i ).getName().equalsIgnoreCase( command ) )
			{
				Constructor<CommandTemplate> constr;
				CommandTemplate cmd;
				Class<CommandTemplate> c;

				try
				{
					c = (Class<CommandTemplate>) Class.forName( "jmud.command." + commands.get( i ).getName() );

					constr = c.getConstructor( new Class[]{ Command.class } );
					cmd = (CommandTemplate) constr.newInstance( commands.get( i ) );

					cmd.exec( descriptor, args );
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
					System.out.println( "Class Not Found Exception\bCouldn't find class \"" + commands.get( i ).getName() + "\"\n" );
				}
				catch( NoSuchMethodException nme )
				{
					System.out.println( "No Such Method Exception\bCouldn't find some method (probably newInstance()) for \"" + commands.get( i ).getName() + "\"\n" );
				}

				return true;
			}
			else if( commands.get( i ).getName().toLowerCase().startsWith( command.toLowerCase() ) )
			{
				// enters here if the command required a full name to use
				descriptor.sendMessage( "The \"" + commands.get( i ).getName() + "\" command requires the full command name to use.\r\n" + commands.get( i ).getUsageString() + "\r\n" );
				return true;
			}
		}

		// Command not found
		descriptor.sendMessage( "Huh? (type \"commands\" for a list of commands)\r\n" );
		return false;
	}
}
