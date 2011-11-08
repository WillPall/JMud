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

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import jmud.JMud;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * @author Will Pall
 * 
 */
public class Server
{
	private static final int PORT = 4444;
	private ServerBootstrap bootstrap;
	
	private Server( int port )
	{
		JMud.log( "Setting up server ..." );
		
		// TODO: load server maps, mobs, items, etc.
		
		// setup the thread pools and configure the server
		bootstrap = new ServerBootstrap( new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool() ) );
		
		// setup the pipeline factory for client connections
		bootstrap.setPipelineFactory( new jmud.network.ClientPipelineFactory() );
	}
	
	private static class instanceHolder
	{
		public static Server instance = new Server( PORT );
	}
	
	/**
	 * Gets a new instance of Server.
	 * 
	 * @return A {@link Server} instance
	 */
	public static Server getInstance()
	{
		return instanceHolder.instance;
	}
	
	/**
	 * Forces the server to perform a shutdown.
	 */
	public void shutdown()
	{
		// TODO: make it shutdown
	}

	public void start()
	{
		// Bind and start to accept incoming connections.
		bootstrap.bind( new InetSocketAddress( PORT ) );
		
		JMud.log( "Success\r\nAccepting connections on port " + PORT );
	}
}
