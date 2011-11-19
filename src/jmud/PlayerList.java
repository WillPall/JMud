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

import jmud.network.ClientHandler;

/**
 * @author Will Pall
 *
 */
public class PlayerList extends ArrayList<Player>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3840162674695846340L;

	private PlayerList()
	{
		super();
	}
	
	private static class instanceHolder
	{
		public static PlayerList instance = new PlayerList();
	}
	
	/**
	 * Gets a new instance of Server.
	 * 
	 * @return A Server instance
	 */
	public static PlayerList getInstance()
	{
		return instanceHolder.instance;
	}
	
	// TODO: get rid of this
	public Player getPlayerByClientHandler( ClientHandler clientHandler )
	{
		for( Player p : this )
		{
			if( p.getClientHandler().equals( clientHandler ) )
				return p;
		}
		
		return null;
	}
	
	public Player getPlayerByCharacterName( String name )
	{
		for( Player p : this )
		{
			if( p.getCharacter().getName().equalsIgnoreCase( name ) )
				return p;
		}
		
		return null;
	}
}
