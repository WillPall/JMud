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
import jmud.JMud;
import jmud.Player;
import jmud.PlayerList;

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

	public boolean exec( Player player, String args )
	{
		String argsArray[] = args.split( " ", 2 );
		
		if( argsArray.length > 1 )
		{
			// TODO: fix this
			Player target = PlayerList.getInstance().getPlayerByCharacterName( argsArray[0] ); //Server.getInstance().findClientByCharacterName( argsArray[0] );
			
			if( target == null )
			{
				player.sendMessage( "That person isn't online.\r\n" );
				return true;
			}
			else if( target.equals( player ) )
			{
				player.sendMessage( "Are you talking to yourself again? You should really see a professional about that.\r\n" );
				return true;
			}
			
			target.sendMessage( ChatColor.GREEN + player.getCharacter().getName() + " tells you, \"" + ChatColor.CLEAR + argsArray[1] + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
			target.setReplyToPlayer( player );
			player.sendMessage( ChatColor.GREEN + "You tell " + target.getCharacter().getName() + ", \"" + ChatColor.CLEAR + argsArray[1] + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
		}
		else
			player.sendMessage( this.command.getUsageString() );
			
		return true;
	}
}