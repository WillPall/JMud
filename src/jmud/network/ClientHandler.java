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
package jmud.network;

import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * TODO: change all of this or add the appropriate license
 * 
 * @author Will Pall
 */
public class ClientHandler extends SimpleChannelUpstreamHandler
{

	private static final Logger logger = Logger.getLogger( ClientHandler.class.getName() );

	@Override
	public void handleUpstream( ChannelHandlerContext ctx, ChannelEvent e ) throws Exception
	{
		if( e instanceof ChannelStateEvent )
		{
			logger.info( e.toString() );
		}
		super.handleUpstream( ctx, e );
	}

	@Override
	public void channelConnected( ChannelHandlerContext ctx, ChannelStateEvent e ) throws Exception
	{
		// Send greeting for a new connection.
		e.getChannel().write( "Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n" );
		e.getChannel().write( "It is " + new Date() + " now.\r\n" );
	}

	@Override
	public void messageReceived( ChannelHandlerContext ctx, MessageEvent e )
	{

		// Cast to a String first.
		// We know it is a String because we put some codec in
		// TelnetPipelineFactory.
		String request = (String) e.getMessage();

		// Generate and write a response.
		String response;
		boolean close = false;
		if( request.length() == 0 )
		{
			response = "Please type something.\r\n";
		}
		else if( request.toLowerCase().equals( "bye" ) )
		{
			response = "Have a good day!\r\n";
			close = true;
		}
		else
		{
			response = "Did you say '" + request + "'?\r\n";
		}

		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at TelnetPipelineFactory will do the
		// conversion.
		ChannelFuture future = e.getChannel().write( response );

		// Close the connection after sending 'Have a good day!'
		// if the client has sent 'bye'.
		if( close )
		{
			future.addListener( ChannelFutureListener.CLOSE );
		}
	}

	@Override
	public void exceptionCaught( ChannelHandlerContext ctx, ExceptionEvent e )
	{
		logger.log( Level.WARNING, "Unexpected exception from downstream.", e.getCause() );
		e.getChannel().close();
	}
}
