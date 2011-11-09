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
package jmud.entity;

import jmud.Room;

/**
 * Represents a player character.
 * 
 * @author Will Pall
 */
public class Character extends Person
{
	
	/**
	 * Constructs a player character.
	 * 
	 * @param name Character's name
	 * @param description Character's description
	 * @param currentRoom Character's current room
	 * @param descriptor Client descriptor associated with the player character
	 */
	public Character( String name, String description, Room currentRoom )
	{
		super( name, description, currentRoom );
	}
	
	@Override
	public void moveToRoom( Room destination )
	{
		// TODO: fix the messaging
		//descriptor.sendMessageToRoom( name + " left the room.\r\n" );
		synchronized( this )
		{
			currentRoom.removeEntity( this );
			destination.addEntity( this );
		}
		currentRoom = destination;
		//descriptor.sendMessageToRoom( name + " entered the room.\r\n" );
	}
}
