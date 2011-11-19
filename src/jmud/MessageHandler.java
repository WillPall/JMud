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
public class MessageHandler
{
	private MessageHandler()
	{
	}
	
	private static class instanceHolder
	{
		public static MessageHandler instance = new MessageHandler();
	}
	
	/**
	 * Gets a new instance of MessageHandler.
	 * 
	 * @return A MessageHandler instance
	 */
	public static MessageHandler getInstance()
	{
		return instanceHolder.instance;
	}
	
	public void handleMessage( String message, ClientHandler clientHandler )
	{
		String com[] = message.trim().split( " ", 2 );
		String args = "";
		
		if( com.length > 1 )
			args = com[1];
		
		Player p = PlayerList.getInstance().getPlayerByClientHandler( clientHandler );
		
		CommandHandler.getInstance().doCommand( com[0], args, p );
	}
}
