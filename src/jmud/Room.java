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

import jmud.entity.Character;
import jmud.entity.Entity;
import jmud.entity.Person;

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
	 * Adds an exit to the room.
	 * 
	 * @param exit Exit to add
	 */
	public void addExit( RoomExit exit )
	{
		exits.add( exit );
	}
	
	/**
	 * Get all characters in the room.
	 * 
	 * @return A list of characters
	 */
	public ArrayList<Character> getCharacters()
	{
		ArrayList<Character> characters = new ArrayList<Character>();
		
		for( Entity e : entities )
		{
			if( e instanceof Character )
				characters.add( (Character) e ); 
		}
		
		return characters;
	}
	
	/**
	 * Get all entities in the room.
	 * 
	 * @return A list of entities
	 */
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}
	
	/**
	 * Gets the exits for this room.
	 * 
	 * @return The room's exits
	 */
	public ArrayList<RoomExit> getExits()
	{
		return exits;
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
	
	public ArrayList<Person> getPersons()
	{
		ArrayList<Person> persons = new ArrayList<Person>();
		
		for( Entity e : entities )
		{
			if( e instanceof Person )
				persons.add( (Person) e ); 
		}
		
		return persons;
	}

	private String listEntities()
	{
		if( entities.isEmpty() )
			return "";
		
		String entityString = "";
		
		for( Entity e : entities )
		{
			entityString += e.getName() + ChatColor.CLEAR + " is here.\r\n";
		}
		
		return entityString;
	}
	
	private String listExits()
	{
		if( exits.isEmpty() )
			return "\tnone\r\n";
		
		String exitString = "";
		
		for( RoomExit e : exits )
		{
			exitString += "\t" + e.getLabel() + "\r\n";
		}
		
		return exitString;
	}
	
	/**
	 * Removes an entity from the room's entity list.
	 * 
	 * @param entity The entity to remove
	 */
	public void removeEntity( Entity entity )
	{
		entities.remove( entity );
	}
	
	/**
	 * Sends a message to all characters present in the room.
	 * 
	 * @param message The message to send
	 */
	public void sendMessage( String message )
	{
		// TODO: make this not send extra messages to the one leaving the room
		for( Entity entity : entities )
		{
			if( entity instanceof Character )
			{
				Character ch = (Character) entity;
				ch.getDescriptor().sendMessage( message );
			}
		}
	}
	
	public String toString()
	{
		String str = "";
		
		str += ChatColor.BOLD + ChatColor.CYAN + title + ChatColor.CLEAR + "\r\n";
		str += description + ChatColor.CLEAR + "\r\n";
		str += ChatColor.BOLD + ChatColor.WHITE + "Exits:\r\n" + ChatColor.CLEAR + listExits();
		str += listEntities();
		
		return str;
	}
}
