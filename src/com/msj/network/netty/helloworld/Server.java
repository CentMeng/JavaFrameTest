package com.msj.network.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 对于ChannelOption.SO_BACKLOG的讲解
 * 服务器端TCP内核模块维护2个队列，我们称之为A，B
 * 客户端向服务器端connect的时候，会发送带有SYN标志的包（第一次握手）
 * 服务器收到客户端发来的SYN的时，向客户端发送SYN ACK确认（第二次握手）
 * 此时TCP内核模块把客户端连接加入到A队列中，然后服务器收到客户端发过来的ACK时（第三次握手），
 * TCP内核模块把客户端连接从A队列移到B队列，连接完成，应用程序的accept回返回。
 * 也就是accept从B队列中取出完成三次握手的连接。
 * A队列和B队列的长度之和是BACKLOG。当A,B队列的长度之和大于BACKLOG的时候，新连接将会被TCP内核拒绝。
 * 所以，如果BACKLOG过小，可能会出现accept速度跟不上，A，B队列满了，导致新的客户端无法连接
 * 要注意的是：BACKLOG对程序支持的连接数并没有影响，BACKLOG只是影响了还没有被accept取出的连接。
 */
public class Server {

	public static void main(String[] args) throws Exception {
		//1 创建线两个程组 
		//一个是用于处理服务器端接收客户端连接的
		//一个是进行网络通信的（网络读写的）
		EventLoopGroup pGroup = new NioEventLoopGroup();
		EventLoopGroup cGroup = new NioEventLoopGroup();
		
		//2 创建辅助工具类，用于服务器通道的一系列配置
		ServerBootstrap b = new ServerBootstrap();
		b.group(pGroup, cGroup)		//绑定俩个线程组
		.channel(NioServerSocketChannel.class)		//指定NIO的模式
		.option(ChannelOption.SO_BACKLOG, 1024)		//设置tcp缓冲区
		.option(ChannelOption.SO_SNDBUF, 32*1024)	//设置发送缓冲大小
		.option(ChannelOption.SO_RCVBUF, 32*1024)	//这是接收缓冲大小
		.option(ChannelOption.SO_KEEPALIVE, true)	//保持连接，默认是true
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				//3 在这里配置具体数据接收方法的处理
				sc.pipeline().addLast(new ServerHandler());
			}
		});//服务器是.childHandler 客户端是handler
		
		//4 进行绑定 
		ChannelFuture cf1 = b.bind(8765).sync();
		//ChannelFuture cf2 = b.bind(8764).sync();
		//5 等待关闭
		//Thread.sleep(1000000);
		//阻塞程序
		cf1.channel().closeFuture().sync();
		//cf2.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
	}
}
