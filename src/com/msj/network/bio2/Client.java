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
public class Client {
	
	final static String ADDRESS = "127.0.0.1";
	final static int PORT =8765;
	
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket(ADDRESS, PORT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			out.println("Client request");
			
			String response = in.readLine();
			System.out.println("Client:" + response);
			
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
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
