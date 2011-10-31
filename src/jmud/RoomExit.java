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

/**
 * Represents an exit from a room.
 * 
 * This class contains information about a room exit. This includes the exit's
 * label (such as "north" or "glowing portal"), its destination, and any
 * restrictions on usage (such as level requirement or keys needed).
 * 
 * @author Will Pall
 */
public class RoomExit
{
	private String label;
	private Room destination;
	
	/**
	 * Constructs a room exit with the given label and destination.
	 * 
	 * @param label The exit's label
	 * @param destination The destination room for the exit
	 */
	public RoomExit( String label, Room destination )
	{
		this.label = label;
		this.destination = destination;
	}
	
	/**
	 * Constructs a room exit with the given label and destination given
	 * by the destination room's id.
	 * 
	 * @param label The exit's label
	 * @param destinationId The id of the destination room
	 */
	public RoomExit( String label, int destinationId )
	{
		this( label, JMud.getRoomList().getRoomById( destinationId ) );
	}
	
	/**
	 * Gets the exit's label.
	 * 
	 * @return The exit's label
	 */
	public synchronized String getLabel()
	{
		return label;
	}
	
	/**
	 * Get's the exit's destination room.
	 * 
	 * @return The exit's destination
	 */
	public synchronized Room getDestination()
	{
		return destination;
	}
}
