package com.msj.network.netty.heartBeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/24 下午4:41
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 心跳检测
 */
public class Client {

	
	public static void main(String[] args) throws Exception{
		
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
		 .channel(NioSocketChannel.class)
		 .handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
				sc.pipeline().addLast(new ClienHeartBeattHandler());
			}
		});
		
		ChannelFuture cf = b.connect("127.0.0.1", 8765).sync();

		cf.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
}
