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

/**
 * Represents a command to disconnect from the server.
 * 
 * @author Will Pall
 */
public class Quit extends CommandTemplate
{	
	/**
	 * Constructs a new command template for the "Quit" command.
	 * 
	 * @param command The command object on which to base this template.
	 */
	public Quit( Command command )
	{
		super( command );
	}

	public boolean exec( ClientDescriptor descriptor, String args )
	{
		descriptor.sendMessage( "K thx bai!" );
		descriptor.disconnect();
			
		return true;
	}
}