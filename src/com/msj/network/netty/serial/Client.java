package com.msj.network.netty.serial;

import com.msj.utils.GzipUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/20 下午4:41
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 编解码技术，序列化传递对象
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
				sc.pipeline().addLast(new ClientHandler());
			}
		});
		
		ChannelFuture cf = b.connect("127.0.0.1", 8765).sync();
		
		for(int i = 0; i < 5; i++ ){
			Req req = new Req();
			req.setId("" + i);
			req.setName("pro" + i);
			req.setRequestMessage("数据信息" + i);	
			String path = System.getProperty("user.dir") + File.separatorChar + "sources" +  File.separatorChar + "001.jpg";
			File file = new File(path);
	        FileInputStream in = new FileInputStream(file);  
	        byte[] data = new byte[in.available()];  
	        in.read(data);  
	        in.close(); 
			req.setAttachment(GzipUtils.gzip(data));
			cf.channel().writeAndFlush(req);
		}

		cf.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
}
