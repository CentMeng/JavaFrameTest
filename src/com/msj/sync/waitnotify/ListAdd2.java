package com.msj.sync.waitnotify;

import java.util.ArrayList;
import java.util.List;

/**
 * wait和notify方式：启动两个线程，线程1增加队列，线程2一直循环遍历，当队列数等于5的时候，终止线程2
 * wait和notify实现方式的弊端：不实时问题---> 解决方式:使用CountDownLatch类
 * @author Vincent.M mengshaojie@188.com on 2017/3/13.
 */
public class ListAdd2 {
	private volatile static List list = new ArrayList();	
	
	public void add(){
		list.add("bjsxt");
	}
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		
		final ListAdd2 list2 = new ListAdd2();

		//解决实时问题,1表示只要一次countdown（）才能唤醒await函数，2表示调用两次countdown(),才能唤醒await函数
//		final CountDownLatch countDownLatch = new CountDownLatch(1);

		//使用同一个对象，调用同一个对象的notify和wait
		final Object lock = new Object();//使用CountDownLatch屏蔽此行
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock) { //使用CountDownLatch屏蔽此行
						System.out.println("t1启动..");
						for(int i = 0; i <10; i++){
							list2.add();
							System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
							Thread.sleep(500);
							if(list2.size() == 5){
								System.out.println("已经发出通知..");
								lock.notify();//使用CountDownLatch屏蔽此行
								//解决实时问题
//								countDownLatch.countDown();
							}
						}						
					}//使用CountDownLatch屏蔽此行
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) { //使用CountDownLatch屏蔽此行
					System.out.println("t2启动..");
					if(list2.size() != 5){
						try {
							lock.wait();//使用CountDownLatch屏蔽此行
							//解决实时问题
//								countDownLatch.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知线程停止..");
					throw new RuntimeException();
				}//使用CountDownLatch屏蔽此行
			}
		}, "t2");

		//先启动t2再启动t1，结果是t1执行完毕后，t2才执行，t2线程结束。这是因为，t1中的lock.notify()方法不释放锁，
		//只有当t1线程synchronized代码块运行完，才会释放锁。t2不会阻止t1是因为，lock.wait（）会释放锁
		t2.start();
		t1.start();

		//t1先执行，则notify不会释放锁，t2不会执行，只有t1synchronized代码块执行完毕后，t2才会执行
//		t1.start();
//		t2.start();
	}
	
}
