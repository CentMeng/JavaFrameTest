package com.msj.network.bio2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/20 下午4:41
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 传统BIO编程，线程池控制创建撑爆个数的客户端
 */
public class ServerHandler implements Runnable {

	private Socket socket;
	public ServerHandler (Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String body = null;
			while(true){
				body = in.readLine();
				if(body == null) break;
				System.out.println("Server:" + body);
				out.println("Server response");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(socket != null){
				try {
					socket.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
			socket = null;			
		}
		
		
	}

}
