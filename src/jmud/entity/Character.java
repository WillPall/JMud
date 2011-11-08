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

import jmud.ClientDescriptor;
import jmud.Room;

/**
 * Represents a player character.
 * 
 * @author Will Pall
 */
public class Character extends Person
{
	private ClientDescriptor descriptor;
	// TODO: do this better. like a new class (Player?)
	private Character replyToCharacter;
	
	/**
	 * Constructs a player character.
	 * 
	 * @param name Character's name
	 * @param description Character's description
	 * @param currentRoom Character's current room
	 * @param descriptor Client descriptor associated with the player character
	 */
	public Character( String name, String description, Room currentRoom, ClientDescriptor descriptor )
	{
		super( name, description, currentRoom );
		this.descriptor = descriptor;
		this.replyToCharacter = null;
	}
	
	/**
	 * Gets the client associated with this character.
	 * 
	 * @return The character's client descriptor
	 */
	public ClientDescriptor getDescriptor()
	{
		return descriptor;
	}
	
	// TODO: this is another that should probably be moved. see reply() and setReplyToCharacter().
	/**
	 * Has the player been "telled" this session?
	 * 
	 * @return True if the player has been "telled". False if not.
	 */
	public boolean hasBeenTelled()
	{
		if( replyToCharacter == null )
			return false;
		
		return true;
	}
	
	// TODO: remove this as well. this doesn't belong here (see: setReplyToCharacter())
	/**
	 * Replies to the last person who "telled" this character.
	 * 
	 * @param message The message to reply
	 */
	public void reply( String message )
	{
		replyToCharacter.getDescriptor().sendMessage( message );
		replyToCharacter.setReplyToCharacter( this );
	}
	
	// TODO: fix this crap. this shouldn't be here
	/**
	 * Sets the last "telled" character.
	 * 
	 * @param character The character that last "telled" this character
	 */
	public void setReplyToCharacter( Character character )
	{
		replyToCharacter = character;
	}
	
	@Override
	public void moveToRoom( Room destination )
	{
		descriptor.sendMessageToRoom( name + " left the room.\r\n" );
		synchronized( this )
		{
			currentRoom.removeEntity( this );
			destination.addEntity( this );
		}
		currentRoom = destination;
		descriptor.sendMessageToRoom( name + " entered the room.\r\n" );
	}
}
