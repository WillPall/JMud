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

import jmud.ClientDescriptor;
import jmud.Command;
import jmud.JMud;
import jmud.Server;

/**
 * Represents a command to say a message in the player's current room.
 * 
 * @author Will Pall
 */
public class Say extends CommandTemplate
{	
	/**
	 * Constructs a new command template for the "Say" command.
	 * 
	 * @param command The command object on which to base this template.
	 */
	public Say( Command command )
	{
		super( command );
	}

	public boolean exec( ClientDescriptor descriptor, String args )
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
			Server.sendToAll( args + "\r\n" );
		}
		else
			descriptor.sendMessage( this.command.getUsageString() );
			
		return true;
	}
}