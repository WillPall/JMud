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
package jmud.command;

import jmud.ChatColor;
import jmud.ClientDescriptor;
import jmud.Command;
import jmud.Server;

/**
 * Represents a command to send a message to a connected player.
 * 
 * @author Will Pall
 */
public class Tell extends CommandTemplate
{	
	/**
	 * Constructs a new command template for the "Tell" command.
	 * 
	 * @param command The command object on which to base this template.
	 */
	public Tell( Command command )
	{
		super( command );
	}

	public boolean exec( ClientDescriptor descriptor, String args )
	{
		String argsArray[] = args.split( " ", 2 );
		
		if( argsArray.length > 1 )
		{
			ClientDescriptor target = Server.getInstance().findClientByCharacterName( argsArray[0] );
			
			if( target == null )
			{
				descriptor.sendMessage( "That person isn't online.\r\n" );
				return true;
			}
			
			target.sendMessage( ChatColor.GREEN + descriptor.getCharacter().getName() + " tells you, \"" + ChatColor.CLEAR + argsArray[1] + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
			target.getCharacter().setReplyToCharacter( descriptor.getCharacter() );
			descriptor.sendMessage( ChatColor.GREEN + "You tell " + target.getCharacter().getName() + ", \"" + ChatColor.CLEAR + argsArray[1] + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
		}
		else
			descriptor.sendMessage( this.command.getUsageString() );
			
		return true;
	}
}