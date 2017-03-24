package com.msj.network.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler  extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
			//do something msg
			ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");
			System.out.println("Server: " + request);
			//写给客户端
			String response = "我是反馈的信息";
			ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer("888".getBytes()));
			//如果需要对数据发送完之后有操作，则增加监听。（此处是主动断开,数据过去就断开了，客户端如果有信息返回就会连接失败）
			f.addListener(ChannelFutureListener.CLOSE);

			

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
