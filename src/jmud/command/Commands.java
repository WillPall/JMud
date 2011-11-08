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

import jmud.Command;
import jmud.CommandHandler;
import jmud.Player;

/**
 * Represents a command to send the client all available commands.
 * 
 * @author Will Pall
 */
public class Commands extends CommandTemplate
{	
	/**
	 * Constructs a new command template for the "Commands" command.
	 * 
	 * @param command The command object on which to base this template.
	 */
	public Commands( Command command )
	{
		super( command );
	}
	
	public boolean exec( Player player, String args )
	{	
		player.sendMessage( "{I{wAvailable commands:{x\r\n" );
		player.sendMessage( CommandHandler.getInstance().toString() );	
		player.sendMessage( "\r\n" );
		return true;
	}
}

