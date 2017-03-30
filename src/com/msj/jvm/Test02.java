package com.msj.jvm;

/**
 *新生代配置：
 *
 * 不同的堆分布情况，对系统执行会产生一定的影响，在实际工作中，应该根据系统的特点做出合理的配置，基本策略：尽可能将对象预* 留在新生代，减少老年代的GC次数。除了可以设置新生代的绝对大小（-Xmn），还可以使用（-XX：NewRatio）设置新生代和老年* 代的比例：-XX：NewRatio=老年代/新生代
 */
public class Test02 {

	public static void main(String[] args) {
		
		//第一次配置
		//-Xms20m -Xmx20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
		
		//第二次配置
		//-Xms20m -Xmx20m -Xmn7m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
		
		//第三次配置
		//-Xms20m -Xmx20m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC

		//第四次配置
		//-Xms20m -Xmx20m -XX:NewRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC
		
		byte[] b = null;
		//连续向系统申请10MB空间
		for(int i = 0 ; i <10; i ++){
			b = new byte[1*1024*1024];
		}
	}
}
