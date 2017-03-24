package com.msj.network.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 着重看下注释内容
 */
public class Server {

	public static void main(String[] args) throws Exception {
		//1 第一个线程组 是用于接收Client端连接的
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//2 第二个线程组 是用于实际的业务处理操作的
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		//3 创建一个辅助类Bootstrap，就是对我们的Server进行一系列的配置
		ServerBootstrap b = new ServerBootstrap(); 
		//把俩个工作线程组加入进来
		b.group(bossGroup, workerGroup)
		//我要指定使用NioServerSocketChannel这种类型的通道
		 .channel(NioServerSocketChannel.class)
		//一定要使用 childHandler 去绑定具体的 事件处理器
		 .childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(new ServerHandler());
			}
		})
				/**
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
				//设置tcp缓冲区
//		.option(ChannelOption.SO_BACKLOG,128)
				//保持连接
		.childOption(ChannelOption.SO_KEEPALIVE,true);

		//绑定指定的端口 进行监听
		ChannelFuture f = b.bind(9876).sync();
		
		//Thread.sleep(1000000);
		//阻塞程序
		f.channel().closeFuture().sync();
		
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		 
		
		
	}
	
}
