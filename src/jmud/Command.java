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
 * Represents a command entry, including its name, aliases, and minimum
 * character level required to use the command.
 * 
 * @author Will Pall
 */
public class Command
{
	private String name;
	private int minLevel;
	private String aliases[];
	private boolean requiresFullName;
	private String usage;
	
	/**
	 * Constructs a command entry with the given name.
	 * 
	 * Minimum level defaults to 0, aliases will be empty, the command will
	 * be usable by suffix and the usage string for the command will default
	 * to the command name only.
	 * 
	 * @param name The name of the command
	 * @param minLevel The minimum level required to use the command
	 */
	public Command( String name )
	{
		this.name = name;
		this.minLevel = 0;
		this.aliases = null;
		this.requiresFullName = false;
		this.usage = name.toLowerCase();
	}
	
	/**
	 * Constructs a command entry with the given name and minimum level.
	 * 
	 * Aliases will be empty, the command will be usable by suffix and the
	 * usage string for the command will default to the command name only.
	 * 
	 * @param name The name of the command
	 * @param minLevel The minimum level required to use the command
	 */
	public Command( String name, int minLevel )
	{
		this( name );
		this.minLevel = minLevel;
	}
	
	/**
	 * Constructs a command entry with the given name, minimum level,
	 * and full-command-name requirement.
	 * 
	 * @param name The name of the command
	 * @param minLevel The minimum level required to use the command
	 * @param requiresFullName Whether the command requires the full name to be used successfully
	 */
	public Command( String name, int minLevel, boolean requiresFullName )
	{
		this( name, minLevel );
		this.requiresFullName = requiresFullName;
	}
	
	/**
	 * Constructs a command entry with the given name, minimum level,
	 * and usage string.
	 * 
	 * @param name The name of the command
	 * @param minLevel The minimum level required to use the command
	 * @param usage The command's usage string
	 */
	public Command( String name, int minLevel, String usage )
	{
		this( name, minLevel );
		this.usage = usage;
	}

	/**
	 * Constructs a command entry with the given name, minimum level
	 * and aliases.
	 * 
	 * @param name The name of the command
	 * @param minLevel The minimum level required to use the command
	 * @param aliases A set of aliases for the command
	 */
	Command( String name, int minLevel, String aliases[] )
	{
		this( name, minLevel );
		this.aliases = aliases;
	}
	
	/**
	 * Gets the name of the command.
	 * 
	 * @return The name of the command
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the minimum level required to use the command.
	 * 
	 * @return The minimum level
	 */
	public int getMinLevel()
	{
		return minLevel;
	}
	
	/**
	 * Gets the usage string for the command.
	 * 
	 * For example, if the usage for the command is "somecommand [sometarget]",
	 * this will return "Usage: somecommand [sometarget]".
	 * 
	 * @return The usage string for the command
	 */
	public String getUsageString()
	{
		return "Usage:\r\n\t" + usage;
	}
	
	/**
	 * Gets whether the command requires the full name for use by a player.
	 * 
	 * @return True if the full name is required. False if not.
	 */
	public boolean requiresFullName()
	{
		return this.requiresFullName;
	}
	
	/**
	 * Checks if input is an alias of this command.
	 * 
	 * @param input The client's input to check for aliases
	 * @return True if input is an alias. False if not.
	 */
	public boolean isAlias( String input )
	{
		if( aliases == null )
			return false;
		
		for( String alias : aliases )
		{
			// TODO: make sure we want to use the shortened versions
			if( alias.toLowerCase().startsWith( input.toLowerCase() ) )
				return true;
		}
		
		return false;
	}
}