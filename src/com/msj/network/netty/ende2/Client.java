package com.msj.network.netty.ende2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/20 下午4:41
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc TCP粘包拆包---定长解决方案 FixedLengthFrameDecoder
 */
public class Client {

	public static void main(String[] args) throws Exception {
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		Bootstrap b = new Bootstrap();
		b.group(group)
		 .channel(NioSocketChannel.class)
		 .handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				//设置定长字符串接收
				sc.pipeline().addLast(new FixedLengthFrameDecoder(5));
				//设置字符串形式的解码，write时候还是buffer但是handler接收时候可以直接强转成string
				sc.pipeline().addLast(new StringDecoder());
				sc.pipeline().addLast(new ClientHandler());
			}
		});
		
		ChannelFuture cf = b.connect("127.0.0.1", 8765).sync();
		
		cf.channel().writeAndFlush(Unpooled.wrappedBuffer("aaaaabbbbb".getBytes()));
		cf.channel().writeAndFlush(Unpooled.copiedBuffer("ccccccc".getBytes()));
		
		//等待客户端端口关闭
		cf.channel().closeFuture().sync();
		group.shutdownGracefully();
		
	}
}
