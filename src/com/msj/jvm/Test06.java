package com.msj.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用PretenureSizeThreshod可以进行指定进入老年代的对象大小，但是要注意TLAB区域有限分配空间。
 */
public class Test06 {

	public static void main(String[] args) {
		
		//参数：-Xmx30M -Xms30M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:PretenureSizeThreshold=1000
		//这种现象原因为：虚拟机对于体积不大的对象 会优先把数据分配到TLAB区域中，因此就失去了在老年代分配的机会
		//参数：-Xmx30M -Xms30M -XX:+UseSerialGC -XX:+PrintGCDetails -XX:PretenureSizeThreshold=1000 -XX:-UseTLAB
		Map<Integer, byte[]> m = new HashMap<Integer, byte[]>();
		for(int i=0; i< 5*1024; i++){
			byte[] b = new byte[1024];
			m.put(i, b);
		}
	}
}
