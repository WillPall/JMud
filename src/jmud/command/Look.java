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
package jmud.command;

import jmud.ChatColor;
import jmud.ClientDescriptor;

/**
 * Represents a command to look at rooms or entities.
 * 
 * @author Will Pall
 */
public class Look extends CommandTemplate
{	
	public Look()
	{
	}
	
	// return a list of characters
	/*private String listCharacters()
	{
		String str = "";
		
		// If there is someone other than the player in the room
		if( this.handler.getRoom().getHandlerList().size() > 1 )
		{
			for( Enumeration<String> e = this.handler.getRoom().getHandlerList().keys(); e.hasMoreElements(); )
			{
				// Convoluted way to get the name and handler
				// TODO: fix the loop and this to be more efficient
				String name = e.nextElement().toString();
				ClientDescriptor h = this.handler.getRoom().getHandlerList().get( name );
				
				// if it isn't this player
				if( !name.equals( this.handler.getCharacter().getName() ) )
					str += h.getCharacter().getARName( this.handler.getCharacter() ) + " is here.\r\n";
			}
		}
		
		return str;
	}
	
	public boolean exec( ClientDescriptor descriptor, String args )
	{
		if( args.equals( "" ) )
			descriptor.sendToChar( descriptor.getRoom().toString() + listCharacters()  );
		else
		{
			if( args.toLowerCase().equals( "self" ) )
				descriptor.sendToChar( descriptor.getCharacter().toString() );
			else
			{
				ClientDescriptor target = descriptor.getHandlerByName( args );
				
				if( ( target != null ) && ( target.getRoom() == descriptor.getRoom() ) )
				{
					descriptor.sendToChar( target.getCharacter().toString() );
				}
				else
					descriptor.sendToChar( "No one called \"" + args + "\" is here.\r\n" );
			}
		}
				
		return true;
	}*/
	
	public boolean exec( ClientDescriptor descriptor, String args )
	{
		if( args.equals( "" ) )
			descriptor.sendMessage( ChatColor.colorFormat( descriptor.getCharacter().getCurrentRoom().toString() ) );
		else
		{
			if( args.equalsIgnoreCase( "self" ) )
			{
				// TODO: send the character his own description/equipment
			}
			else
			{
				// TODO: send info about the targeted entity
			}
		}
		
		// TODO: make this useful
		return true;
	}
}