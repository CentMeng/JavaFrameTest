package com.msj.network.mina.helloworld;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Mina 客户端
 * @author alienware
 *
 */
public class ClientMain {

	public static void main(String[] args) {
		//创建客户端连接
		NioSocketConnector connnector = new NioSocketConnector();
		
		connnector.getFilterChain().
		addLast("logger", new LoggingFilter());
		
		connnector.getFilterChain().
		addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		//设置连接超时检查时间
		connnector.setConnectTimeoutCheckInterval(30);
		connnector.setHandler(new TimeClientHandler());
		
		//建立连接
		ConnectFuture cf = connnector.connect(new InetSocketAddress("192.168.0.100", 6488));
		
		//等待连接创建完成
		cf.awaitUninterruptibly();
		
		cf.getSession().write("Hi Server!");
		cf.getSession().write("quit");
		
		//等待连接断开并释放连接
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		connnector.dispose();
		
	}
}
