package jmud;

/*
 * Command
 * Prototype for commands
 */

public class Command
{
	private String name;
	private int minLevel;
	private String aliases[];
	
	Command( String name, int minLevel )
	{
		this.name = name;
		this.minLevel = minLevel;
		this.aliases = null;
	}
	
	Command( String name, int minLevel, String aliases[] )
	{
		this( name, minLevel );
		this.aliases = aliases;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getMinLevel()
	{
		return minLevel;
	}
	
	public boolean isAlias( String str )
	{
		if( aliases == null )
			return false;
		
		for( String alias : aliases )
		{
			// TODO: make sure we want to use the shortened versions
			if( alias.toLowerCase().startsWith( str.toLowerCase() ) )
				return true;
		}
		
		return false;
	}
	
	public boolean exec()
	{
		return true;
	}
}