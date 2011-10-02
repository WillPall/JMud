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

/**
 * Abstract command class for implementing all commands.
 * 
 * @author Will Pall
 */
public abstract class CommandTemplate
{	
	/**
	 * Executes the command for the given descriptor with the given arguments.
	 * 
	 * @param descriptor Client descriptor that used the command
	 * @param args Command arguments
	 * @return True if the command was successful or false if not
	 */
	public abstract boolean exec( ClientDescriptor descriptor, String args );
}