package com.msj.sync.masterworker;

import java.util.Random;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/15 下午3:25
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc Master-Worker模式
 */
public class Main {

	public static void main(String[] args) {

		System.out.println("我的机器java虚拟机可用线程数量"+Runtime.getRuntime().availableProcessors());
		Master master = new Master(new Worker(), Runtime.getRuntime().availableProcessors()+1);
		
		Random r = new Random();
		for(int i = 1; i <= 100; i++){
			Task t = new Task();
			t.setId(i);
			t.setPrice(r.nextInt(1000));
			master.submit(t);
		}
		master.execute();
		long start = System.currentTimeMillis();
		
		while(true){
			if(master.isComplete()){
				long end = System.currentTimeMillis() - start;
				int priceResult = master.getResult();
				System.out.println("最终结果：" + priceResult + ", 执行时间：" + end);
				break;
			}
		}
		
	}
}
