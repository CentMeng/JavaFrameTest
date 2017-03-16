package com.msj.sync.singleton;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/13 下午4:41
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 单例和多线程使用，静态内部类形式
 */
public class InnerSingleton {
	
	private static class Singletion {
		private static Singletion single = new Singletion();
	}
	
	public static Singletion getInstance(){
		return Singletion.single;
	}
	
}
