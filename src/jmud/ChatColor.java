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
 * Holds a representation for ANSI color codes and provides methods for color
 * formatting strings.
 * 
 * @author Will Pall
 */
public abstract class ChatColor
{
	public static final String CLEAR = "{x";
	public static final String BOLD = "{I";
	
	public static final String BLACK = "{d";
	public static final String RED = "{r";
	public static final String GREEN = "{g";
	public static final String YELLOW = "{y";
	public static final String BLUE = "{b";
	public static final String MAGENTA = "{m";
	public static final String CYAN = "{c";
	public static final String WHITE = "{w";

	public static final String BACKGROUND_BLACK = "{D";
	public static final String BACKGROUND_RED = "{R";
	public static final String BACKGROUND_GREEN = "{G";
	public static final String BACKGROUND_YELLOW = "{Y";
	public static final String BACKGROUND_BLUE = "{B";
	public static final String BACKGROUND_MAGENTA = "{M";
	public static final String BACKGROUND_CYAN = "{C";
	public static final String BACKGROUND_WHITE = "{W";
	
	// Escape character
	private static final String CODE_ESC = "\33[";
	// Special codes
	private static final String CODE_CLEAR = "0m";
	private static final String CODE_BOLD = "1m";
	private static final String CODE_BOLDOFF = "22m"; // ANSI v2.5 only
	// Foreground CODEs
	private static final String CODE_BLACK = "30m";
	private static final String CODE_RED = "31m";
	private static final String CODE_GREEN = "32m";
	private static final String CODE_YELLOW = "33m";
	private static final String CODE_BLUE = "34m";
	private static final String CODE_MAGENTA = "35m";
	private static final String CODE_CYAN = "36m";
	private static final String CODE_WHITE = "37m";
	// Background colors
	private static final String CODE_BBLACK = "40m";
	private static final String CODE_BRED = "41m";
	private static final String CODE_BGREEN = "42m";
	private static final String CODE_BYELLOW = "43m";
	private static final String CODE_BBLUE = "44m";
	private static final String CODE_BMAGENTA = "45m";
	private static final String CODE_BCYAN = "46m";
	private static final String CODE_BWHITE = "47m";
	
	/**
	 * Formats a message to replace the color tokens with ANSI color codes.
	 * 
	 * @param chatMessage The raw message to format
	 * @return The formatted message
	 */
	public static String colorFormat( String chatMessage )
	{
		// TODO: this was done badly, fix it
		String buf = chatMessage;
		// Replace for foreground colors
		buf = buf.replaceAll( "\\{d", CODE_ESC + CODE_BLACK );
		buf = buf.replaceAll( "\\{r", CODE_ESC + CODE_RED );
		buf = buf.replaceAll( "\\{g", CODE_ESC + CODE_GREEN );
		buf = buf.replaceAll( "\\{y", CODE_ESC + CODE_YELLOW );
		buf = buf.replaceAll( "\\{b", CODE_ESC + CODE_BLUE );
		buf = buf.replaceAll( "\\{m", CODE_ESC + CODE_MAGENTA );
		buf = buf.replaceAll( "\\{c", CODE_ESC + CODE_CYAN );
		buf = buf.replaceAll( "\\{w", CODE_ESC + CODE_WHITE );
		// Replace for background colors
		buf = buf.replaceAll( "\\{D", CODE_ESC + CODE_BBLACK );
		buf = buf.replaceAll( "\\{R", CODE_ESC + CODE_BRED );
		buf = buf.replaceAll( "\\{G", CODE_ESC + CODE_BGREEN );
		buf = buf.replaceAll( "\\{Y", CODE_ESC + CODE_BYELLOW );
		buf = buf.replaceAll( "\\{B", CODE_ESC + CODE_BBLUE );
		buf = buf.replaceAll( "\\{M", CODE_ESC + CODE_BMAGENTA );
		buf = buf.replaceAll( "\\{C", CODE_ESC + CODE_BCYAN );
		buf = buf.replaceAll( "\\{W", CODE_ESC + CODE_BWHITE );
		// Replace for intensity
		buf = buf.replaceAll( "\\{I", CODE_ESC + CODE_BOLD );
		// Replace for the clear code
		buf = buf.replaceAll( "\\{x", CODE_ESC + CODE_CLEAR );
		
		return buf;
	}
	
	/**
	 * Removes all color from an unformatted string.
	 * 
	 * @param chatMessage The raw message to strip tokens from
	 * @return The original message with all color tokens stripped
	 */
	public static String stripColorTokens( String chatMessage )
	{
		String buf = chatMessage.replaceAll( "\\{[bcdgmrwxyBCDGIMRWY]", "" );
		
		return buf;
	}
}