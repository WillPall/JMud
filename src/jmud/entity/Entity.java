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
 * Represents an entity.
 * 
 * Entities can be objects, monsters, NPCs, and even characters. Entities
 * represent anything that is not static in the game world.
 * 
 * @author Will Pall
 */
public class Entity
{
	protected String name;
	protected String description;
	protected Room currentRoom;
	
	/**
	 * Constructs an entity with the given name and description, and places
	 * it in the given room.
	 * 
	 * @param name The entity name
	 * @param description The entity description
	 * @param currentRoom Entities current room
	 */
	public Entity( String name, String description, Room currentRoom )
	{
		this.name = name;
		this.description = description;
		this.currentRoom = currentRoom;
	}
	
	/**
	 * Gets the name of the entity.
	 * 
	 * @return The entity's name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Get the description of the entity.
	 * 
	 * @return The entity's description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Get the entity's current room.
	 * 
	 * @return The entity's current room
	 */
	public Room getCurrentRoom()
	{
		return currentRoom;
	}
}
