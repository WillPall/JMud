package jmud;

public class ChatColor
{
	// Escape character
	private static final String COLOR_ESC = "\33[";
	// Special codes
	private static final String COLOR_CLEAR = "0m";
	private static final String COLOR_BOLD = "1m";
	private static final String COLOR_BOLDOFF = "22m"; // ANSI v2.5 only
	// Foreground colors
	private static final String COLOR_BLACK = "30m";
	private static final String COLOR_RED = "31m";
	private static final String COLOR_GREEN = "32m";
	private static final String COLOR_YELLOW = "33m";
	private static final String COLOR_BLUE = "34m";
	private static final String COLOR_MAGENTA = "35m";
	private static final String COLOR_CYAN = "36m";
	private static final String COLOR_WHITE = "37m";
	// Background colors
	private static final String COLOR_BBLACK = "40m";
	private static final String COLOR_BRED = "41m";
	private static final String COLOR_BGREEN = "42m";
	private static final String COLOR_BYELLOW = "43m";
	private static final String COLOR_BBLUE = "44m";
	private static final String COLOR_BMAGENTA = "45m";
	private static final String COLOR_BCYAN = "46m";
	private static final String COLOR_BWHITE = "47m";
	
	public static String colorFormat( String str )
	{
		String buf = str;
		// Replace for foreground colors
		buf = buf.replaceAll( "\\{d", COLOR_ESC + COLOR_BLACK );
		buf = buf.replaceAll( "\\{r", COLOR_ESC + COLOR_RED );
		buf = buf.replaceAll( "\\{g", COLOR_ESC + COLOR_GREEN );
		buf = buf.replaceAll( "\\{y", COLOR_ESC + COLOR_YELLOW );
		buf = buf.replaceAll( "\\{b", COLOR_ESC + COLOR_BLUE );
		buf = buf.replaceAll( "\\{m", COLOR_ESC + COLOR_MAGENTA );
		buf = buf.replaceAll( "\\{c", COLOR_ESC + COLOR_CYAN );
		buf = buf.replaceAll( "\\{w", COLOR_ESC + COLOR_WHITE );
		// Replace for background colors
		buf = buf.replaceAll( "\\{D", COLOR_ESC + COLOR_BBLACK );
		buf = buf.replaceAll( "\\{R", COLOR_ESC + COLOR_BRED );
		buf = buf.replaceAll( "\\{G", COLOR_ESC + COLOR_BGREEN );
		buf = buf.replaceAll( "\\{Y", COLOR_ESC + COLOR_BYELLOW );
		buf = buf.replaceAll( "\\{B", COLOR_ESC + COLOR_BBLUE );
		buf = buf.replaceAll( "\\{M", COLOR_ESC + COLOR_BMAGENTA );
		buf = buf.replaceAll( "\\{C", COLOR_ESC + COLOR_BCYAN );
		buf = buf.replaceAll( "\\{W", COLOR_ESC + COLOR_BWHITE );
		// Replace for intensity
		buf = buf.replaceAll( "\\{I", COLOR_ESC + COLOR_BOLD );
		// Replace for the clear code
		buf = buf.replaceAll( "\\{x", COLOR_ESC + COLOR_CLEAR );
		
		return buf;
	}
	
	public static String stripColor( String str )
	{
		String buf = str.replaceAll( "\\{[bcdgmrwxyBCDGIMRWY]", "" );
		
		return buf;
	}
}