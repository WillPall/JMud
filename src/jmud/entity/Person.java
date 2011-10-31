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
 * Represents a person entity.
 * 
 * Can be extended to provide functionality for characters and NPCs
 * 
 * @author Will Pall
 *
 */
public class Person extends Entity
{
	/**
	 * Initializes a new person with the given attributes and associates that
	 * person with the given room.
	 * 
	 * @param name The person's name
	 * @param description The person's description
	 * @param currentRoom The room where the person is currently located
	 */
	public Person( String name, String description, Room currentRoom )
	{
		super( name, description, currentRoom );
	}
	
	/**
	 * Moves the person to a new room and notifies the characters
	 * that the person has left/entered the room.
	 * 
	 * @param destination The room to move the person to
	 */
	public void moveToRoom( Room destination )
	{
		currentRoom.sendMessage( name + " left the room.\r\n" );
		synchronized( this )
		{
			currentRoom.removeEntity( this );
			destination.addEntity( this );
		}
		currentRoom = destination;
		currentRoom.sendMessage( name + " entered the room.\r\n" );
	}
}
