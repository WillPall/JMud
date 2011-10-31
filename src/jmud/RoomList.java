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
 * List of all rooms on the server.
 * 
 * @author Will Pall
 */
public class RoomList extends ArrayList<Room>
{

	/**
	 * Auto-generated serial UID.
	 */
	private static final long serialVersionUID = 4557959986981949079L;

	/**
	 * Constructs an empty list of rooms.
	 */
	public RoomList()
	{
		super();
	}
	
	public Room getRoomById( int id )
	{
		for( Room r : this )
		{
			if( r.getId() == id )
				return r;
		}
		
		// couldn't find the room
		// TODO: add an exception for this
		JMud.log( "RoomList.getRoomById(): couldn't find a room with id " + id + "\r\n" );
		return null;
	}
	
	/**
	 * Loads rooms from the database.
	 */
	public void load()
	{
		// TODO: load the info from the database
	}
}
