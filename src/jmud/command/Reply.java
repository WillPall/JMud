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
import jmud.Command;
import jmud.Player;
import jmud.entity.Person;

/**
 * Represents a command to reply to the last character that "telled" this one.
 * 
 * @author Will Pall
 */
public class Reply extends CommandTemplate
{	
	/**
	 * Constructs a new command template for the "Reply" command.
	 * 
	 * @param command The command object on which to base this template.
	 */
	public Reply( Command command )
	{
		super( command );
	}

	public boolean exec( Player player, String args )
	{
		if( !args.equals( "" ) )
		{
			Player target = player.getReplyToPlayer();
			if( target == null )
			{
				player.sendMessage( "There is no one to reply to.\r\n" );
				return true;
			}
			
			target.sendMessage( ChatColor.GREEN + player.getCharacter().getName() + " replies, \"" + ChatColor.CLEAR + args + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
			target.setReplyToPlayer( player );
			player.sendMessage( ChatColor.GREEN + "You reply, \"" + ChatColor.CLEAR + args + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
		}
		else
			player.sendMessage( this.command.getUsageString() );
			
		return true;
	}
}