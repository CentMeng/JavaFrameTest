package com.msj.jvm;

/**
 * 堆分配参数
 *
 * 在实际工作中，我们可以直接将初始的堆大小与最大堆大小设置相等，这样的好处是可以减少程序运行时的垃圾回收，从而提高性能。
 */
public class Test01 {

	public static void main(String[] args) {

		//-Xms5m -Xmx20m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+PrintCommandLineFlags
		
		//查看GC信息
		System.out.println("max memory:" + Runtime.getRuntime().maxMemory());
		System.out.println("free memory:" + Runtime.getRuntime().freeMemory());
		System.out.println("total memory:" + Runtime.getRuntime().totalMemory());
		
		byte[] b1 = new byte[1*1024*1024];
		System.out.println("分配了1M");
		System.out.println("max memory:" + Runtime.getRuntime().maxMemory());
		System.out.println("free memory:" + Runtime.getRuntime().freeMemory());
		System.out.println("total memory:" + Runtime.getRuntime().totalMemory());
		
		byte[] b2 = new byte[4*1024*1024];
		System.out.println("分配了4M");
		System.out.println("max memory:" + Runtime.getRuntime().maxMemory());
		System.out.println("free memory:" + Runtime.getRuntime().freeMemory());
		System.out.println("total memory:" + Runtime.getRuntime().totalMemory());
		
	}
	
}
