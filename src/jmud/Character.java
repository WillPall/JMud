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

/*
 * Character
 * Holds info on each character running in each thread
 */

import java.sql.*;
import java.util.Vector;
import jmud.Color;
import jmud.Item;
import jmud.Database;
import jmud.StringFunction;

public class Character extends Person
{
	// reference to the current character's handler
	private CharHandler handler;
	private String password;
	private int experience;
	// Descriptive word for strangers
	private String adjective;
	// Prompt after each command
	private String prompt = "{I{d[{rHP:{w%h%{d|{yMV:{w%m%{d]{x%n";
	// Their current room number, needed for saving and loading a char
	private int roomNum = 0;
	// the last person they whispered to
	private String lastWhisper = "";
	// all the people the character has "greeted"
	private String[] greetedList;
	
	Character()
	{
		name = "Noob";
	}
	
	Character( String name )
	{
		this.name = name;
		
		loadChar( name );
	}
	
	// loads a character's info from the database
	public boolean loadChar( String name )
	{
		try
		{
			Class.forName( "com.mysql.jdbc.Driver" );
         Connection con = DriverManager.getConnection( "jdbc:mysql://" + Database.SERVER + "/" + Database.DB, Database.USERNAME, Database.PASSWORD );
			Statement query = con.createStatement();
			ResultSet result;
			
			result = query.executeQuery( "SELECT * FROM players WHERE UPPER( name )=UPPER( '" + name + "' ) LIMIT 1" );
			
			if( !result.first() )
				return false;
			
			// Load the common info all people share
			load( result );
			
			this.password = result.getString( "password" );
			this.experience = result.getInt( "experience" );
			
			this.adjective = result.getString( "adjective" );
			this.prompt = result.getString( "prompt" );
			this.roomNum = result.getInt( "roomNum" );
			
			this.greetedList = result.getString( "greetedlist" ).split( "," ); 
			
			con.close();
			
			return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}
	
	// save character's info to database
	public void saveChar()
	{
		try
		{
			Class.forName( "com.mysql.jdbc.Driver" );
			Connection con = DriverManager.getConnection( "jdbc:mysql://" + Database.SERVER + "/" + Database.DB, Database.USERNAME, Database.PASSWORD );
			Statement query = con.createStatement();
			
			query.executeUpdate( "REPLACE INTO players SET " +
				"description='" + this.description + "'," +
				"password='" + this.password + "'," +
				"prompt='" + this.prompt + "'," +
				"level='" + this.level + "'," +
				"experience='" + this.experience + "'," +
				"race='" + this.race + "'," +
				"adjective='" + this.adjective + "'," +
				//"class='" + this.chClass + "'," + // Classes removed
				
				"curhealth='" + this.curHealth + "'," +
				//"curmana='" + this.curMana + "'," + // Mana removed
				"curmove='" + this.curMove + "'," +
				"tothealth='" + this.totHealth + "'," +
				//"totmana='" + this.totMana + "'," +
				"totmove='" + this.totMove + "'," +
				
				"greetedlist='" + Database.listGreeted( this.greetedList ) + "'," +
				
				"items='" + Database.listItems( this.items ) + "'," +
				"equippeditems='" + Database.listItems( this.equippedItems ) + "'," +
				"roomNum='" + this.roomNum + "'," +
				"name='" + this.name + "'" );
			
			con.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	// return the character's name/adjective and race depending
	//  one whether the characters have been greeted
	// TODO: fix the stupid convoluted name of the method "AR" name?
	//       are you serious?
	public String getARName( Character ch )
	{
		// if this player has met the character
		if( this.hasGreeted( ch.getName() ) )
			return this.name;
		else if( adjective != "" )
		{
			// Make sure the adjective doesn't need the 'an' pronoun
			if( StringFunction.startsWithVowel( adjective ) ||
				 adjective.toLowerCase().startsWith( "honor" ) )
				return "An " + this.adjective + " " + this.getRaceAsString();
			
			return "A " + this.adjective + " " + this.getRaceAsString();
		}
		else
		{
			if( StringFunction.startsWithVowel( getRaceAsString() ) )
				return "An " + getRaceAsString();
				
			return "A " + this.getRaceAsString();
		}
	}
	
	// TODO: make it cleaner
	public void setHandler( CharHandler handler )
	{
		this.handler = handler;
	}
	
	public CharHandler getHandler()
	{
		return handler;
	}
		
	// Password functions
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setPassword( String password )
	{
		this.password = password;
	}
	
	// Level and Experience functions
	
	public int getExperience()
	{
		return experience;
	}
	
	public void setExperience( int experience )
	{
		this.experience = experience;
	}
	
	// takes a number (experience points) and returns a level
	//  that corresponds to the dnd definition of xp and levels
	public static int levelFromExperience( int experience )
	{
		int curLevel = 1;
		int nextLvlXP = 1000;
		
		while( experience >= nextLvlXP )
		{
			curLevel++;
			nextLvlXP += curLevel*1000;
		}
		
		return curLevel;
	}
	
	// takes a number (level) and returns the minimum experience
	//  required to achieve that level
	public static int experienceFromLevel( int level )
	{
		int minXP = 0;
		
		for( int i = 1; i < level; i++ )
			minXP += i*1000;
		
		return minXP;
	}
	
	// Adjective functions
	public String getAdjective()
	{
		if( adjective != null )
			return adjective;
		
		return "";
	}
	
	public void setAdjective( String adjective )
	{
		this.adjective = adjective;
	}
		
	public String getHealthPercentage()
	{
		String buf = "";
		
		buf += (100 * curHealth)/totHealth;
		
		return buf;
	}
	
	public String getMovePercentage()
	{
		String buf = "";
		
		buf += (100 * curMove)/totMove;
		
		return buf;
	}
	
	// return the prompt
	public String getPrompt()
	{
		return prompt;
	}
	
	// set the prompt
	public void setPrompt( String prompt )
	{
		this.prompt = prompt;
	}
	
	// return the roomNum
	public int getRoomNum()
	{
		return this.roomNum;
	}
	
	// set room number
	public void setRoomNum( int roomNum )
	{
		this.roomNum = roomNum;
	}
	
	// return the last whisperer
	public String getLastWhisper()
	{
		return lastWhisper;
	}
	
	// set the last whisper
	public void setLastWhisper( String lastWhisper )
	{
		this.lastWhisper = lastWhisper;
	}
	
	// add a character to the greeted list
	public void addGreeted( String targetName )
	{
		String[] temp = new String[ greetedList.length + 1 ];
		
		for( int i = 0; i < greetedList.length; i++ )
			temp[i] = greetedList[i];
		
		temp[ greetedList.length ] = targetName;
		
		greetedList = temp;
	}
	
	// has the character greeted another
	public boolean hasGreeted( String targetName )
	{
		for( int i = 0; i < greetedList.length; i++ )
		{
			if( greetedList[i].equalsIgnoreCase( targetName ) )
				return true;
		}
		
		return false;
	}
	
	// returns a description of the character, used for "Look"
	public String toString()
	{
		String str;
		boolean naked = true;
		
		str = "{I{w" + name + " is here.{x\r\n";
		
		str += name + " is wearing:\r\n";
		for( int i = 0; i < equippedItems.length; i++ )
		{
			if( equippedItems[i] != null )
			{
				str += "  ";
				// tell the character where they are wearing the item
				switch( i )
				{
					case Item.RIGHT_HAND:
						str += "(RHand)";
						break;
					case Item.LEFT_HAND:
						str += "(LHand)";
						break;
					case Item.HEAD:
						str += "(Head)";
						break;
					case Item.NECK:
						str += "(Neck)";
						break;
					case Item.RIGHT_SHOULDER:
						str += "(RShoulder)";
						break;
					case Item.LEFT_SHOULDER:
						str += "(LShoulder)";
						break;
					case Item.WRISTS:
						str += "(Wrists)";
						break;
					case Item.HANDS:
						str += "(Hands)";
						break;
					case Item.CHEST:
						str += "(Chest)";
						break;
					case Item.WAIST:
						str += "(Waist)";
						break;
					case Item.LEGS:
						str += "(Legs)";
						break;
					case Item.FEET:
						str += "(Feet)";
						break;
					default:
						str += "\t";
						break;
				}
						
				str += "\t" + equippedItems[i].getName() + "\r\n";
				naked = false;
			}
		}
		
		if( naked )
			str += "  ...absolutely nothing.\r\n";
		
		return str;
	}
}