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

import jmud.entity.Character;
import jmud.entity.Person;
import jmud.network.ClientHandler;

/**
 * @author Will Pall
 *
 */
public class Player
{
	/**
	 * The character associated with this player.
	 */
	private Character character;
	/**
	 * The player who last "telled" this player since they logged on.
	 */
	private Player lastTelled;
	
	private ClientHandler clientHandler;
	
	public Player( Character character, ClientHandler clientHandler )
	{
		this.character = character;
		lastTelled = null;
		this.clientHandler = clientHandler;
	}
	
	public Character getCharacter()
	{
		return character;
	}
	
	public ClientHandler getClientHandler()
	{
		return clientHandler;
	}
	
	public Player getReplyToPlayer()
	{
		return lastTelled;
	}
	
	public void setReplyToPlayer( Player player )
	{
		this.lastTelled = player;
	}
	
	// TODO: should this go here?
	public void sendMessage( String message )
	{
		clientHandler.sendMessage( ChatColor.colorFormat( message + "{x" ) );
	}
	
	/*public void sendMessageToRoom( String message )
	{
		for( Player p : character.getCurrentRoom().getCharacters() )
	}*/
}
