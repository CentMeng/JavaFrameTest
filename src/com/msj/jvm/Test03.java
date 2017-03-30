package com.msj.jvm;

import java.util.Vector;

/**
 * 堆溢出处理
 * 在java程序的运行过程中，如果堆空间不足，则会抛出内存溢出的错误（Out Of Memory）OOM,一旦这类问题发生在生产环境，可能引起严重的业务中断，java虚拟机提供了-XX：+HeapDumpOnOutOfMemoryError，使用该参数可以在内存溢出时导出整个堆信息，与之配合使用的还有参数，-XX：HeapDumpPath，可以设置导出堆的存放路径。

 内存分析工具：Memory Analyzer 1.5.0

 */
public class Test03 {

	public static void main(String[] args) {
		
		//-Xms1m -Xmx1m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/Test03.dump
		//堆内存溢出
		Vector v = new Vector();
		for(int i=0; i < 5; i ++){
			v.add(new Byte[1*1024*1024]);
		}
		
	}
}
