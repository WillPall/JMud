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

import java.util.ArrayList;

import jmud.ChatColor;
import jmud.Command;
import jmud.Player;

/**
 * Represents a command to say a message in the player's current room to
 * a specific person.
 * 
 * @author Will Pall
 */
public class SayTo extends CommandTemplate
{	
	/**
	 * Constructs a new command template for the "SayTo" command.
	 * 
	 * @param command The command object on which to base this template.
	 */
	public SayTo( Command command )
	{
		super( command );
	}

	public boolean exec( Player player, String args )
	{
		String argsArray[] = args.split( " ", 2 );
		
		if( argsArray.length > 1 )
		{
			Player target = null;
			for( Player p : player.getCharacter().getCurrentRoom().getPlayers() )
			{
				if( p.getCharacter().getName().toLowerCase().startsWith( argsArray[0].toLowerCase() ) )
				{
					target = p;
					break;
				}
			}
			
			if( target == null )
			{
				player.sendMessage( "That person isn't here.\r\n" );
				return true;
			}
			
			ArrayList<Player> players = new ArrayList<Player>();
			players.add( player );
			players.add( target );

			target.sendMessage( ChatColor.GREEN + player.getCharacter().getName() + " says to you, \"" + ChatColor.CLEAR + argsArray[1] + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
			player.getCharacter().getCurrentRoom().sendMessage( ChatColor.GREEN + player.getCharacter().getName() + " says to " + target.getCharacter().getName() + ", \"" + ChatColor.CLEAR + argsArray[1] + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n", players );
			player.sendMessage( ChatColor.GREEN + "You say to " + target.getCharacter().getName() + ", \"" + ChatColor.CLEAR + argsArray[1] + ChatColor.CLEAR + ChatColor.GREEN + "\"\r\n" );
		}
		else
			player.sendMessage( this.command.getUsageString() );
			
		return true;
	}
}