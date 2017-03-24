package com.msj.network.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


public class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg)
	    throws Exception {
		String response = msg.content().toString(CharsetUtil.UTF_8);
		if (response.startsWith("谚语查询结果: ")) {
		    System.out.println(response);
		    ctx.close();
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
		cause.printStackTrace();
		ctx.close();
    }
}
