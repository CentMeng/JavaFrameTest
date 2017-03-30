package com.msj.jvm;

/**
 * 年龄配置：
 -XX:MaxTenuringThreshold，默认情况下为15。
 * 根据设置MaxTenuringThreshold参数，可以指定新生代对象经过多少次回收后进入老年代。

 另外，大对象（新生代eden区无法装入时，也会直接进入老年代）。JVM里有个参数可以设置对象的大小超过在指定大小之后，直接晋升老年代。
 */
public class Test05 {

	public static void main(String[] args) {
		//初始的对象在eden区
		//参数：-Xmx64M -Xms64M -XX:+PrintGCDetails
//		for(int i=0; i< 5; i++){
//			byte[] b = new byte[1024*1024];
//		}
		
		
		
		//测试进入老年代的对象
		//
		//参数：-Xmx1024M -Xms1024M -XX:+UseSerialGC -XX:MaxTenuringThreshold=15 -XX:+PrintGCDetails 
		//-XX:+PrintHeapAtGC
//		Map<Integer, byte[]> m = new HashMap<Integer, byte[]>();
//		for(int i =0; i <5 ; i++) {
//			byte[] b = new byte[1024*1024];
//			m.put(i, b);
//		}
//		
//		for(int k = 0; k<20; k++) {
//			for(int j = 0; j<300; j++){
//				byte[] b = new byte[1024*1024]; 
//			}
//		}
		
	
	}
}
