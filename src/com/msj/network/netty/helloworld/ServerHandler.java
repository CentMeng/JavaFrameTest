package com.msj.network.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server channel active... ");
	}


	/**
	 *
	 * @param ctx
	 * @param msg 写过来的数据
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
			ByteBuf buf = (ByteBuf) msg;
			//利用可读的数据长度，创建个空的byte[]
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			String body = new String(req, "utf-8");
			System.out.println("Server :" + body );
			//服务器端给客户端的响应
			String response = "进行返回给客户端的响应：" + body ;
			ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
		//如果需要对数据发送完之后有操作，则增加监听。（此处是主动断开,数据过去就断开了，客户端如果有信息返回就会连接失败）建议断开连接在服务端实现
		// .addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		System.out.println("读完了");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t)
			throws Exception {
		ctx.close();
	}

}
