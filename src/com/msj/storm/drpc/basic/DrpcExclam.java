package com.msj.storm.drpc.basic;

import backtype.storm.utils.DRPCClient;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 远程调用drpc测试
 */
public class DrpcExclam {

	public static void main(String[] args) throws Exception {
		DRPCClient client = new DRPCClient("192.168.1.114", 3772);
	      for (String word : new String[]{ "hello", "goodbye" }) {
	    	  System.out.println(client.execute("exclamation", word));
	      }
	}
}
