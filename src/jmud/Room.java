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
package jmud;

import java.util.ArrayList;

/**
 * Represents a room that can contain entities.
 * 
 * @author Will Pall
 */
public class Room
{
	private int id;
	private String title;
	private String description;
	private ArrayList<Entity> entities;
	private ArrayList<RoomExit> exits;
	
	/**
	 * Constructs a room with the given id, title, and description.
	 * 
	 * The room will contain no entities and have no exits.
	 * 
	 * @param id Unique ID of the room
	 * @param title Title of the room
	 * @param description Description of the room
	 */
	public Room( int id, String title, String description )
	{
		this.id = id;
		this.title = title;
		this.description = description;
		entities = new ArrayList<Entity>();
		exits = new ArrayList<RoomExit>();
	}
	
	/**
	 * Adds an entity to the room.
	 * 
	 * @param entity Entity to add
	 */
	public void addEntity( Entity entity )
	{
		entities.add( entity );
	}
	
	/**
	 * Gets this room's id.
	 *  
	 * @return The id of this room
	 */
	public int getId()
	{
		return id;
	}
	
	private String listExits()
	{
		if( exits.isEmpty() )
			return "\tnone";
		
		String exitString = "";
		
		for( RoomExit e : exits )
		{
			exitString += "\t" + e.getLabel() + "\r\n";
		}
		
		return exitString;
	}
	
	public String toString()
	{
		String str = "";
		
		str += ChatColor.BOLD + ChatColor.CYAN + title + ChatColor.CLEAR + "\r\n";
		str += description + ChatColor.CLEAR + "\r\n";
		str += ChatColor.BOLD + ChatColor.WHITE + "Exits:\r\n" + ChatColor.CLEAR + listExits();
		
		return str;
	}
}
