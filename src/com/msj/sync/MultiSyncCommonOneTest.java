package com.msj.sync;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/13 下午4:05
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 屏蔽掉对象可以测试锁对象使用，让不同对象能够同步方法
 */
public class MultiSyncCommonOneTest {

    private static volatile int count = 0;

    public  void addCount(){
//    public  void addCount(String lock){
//        synchronized (lock) {
            count++;
//        }
        System.out.println(""+count);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        for(int i=0;i<1000;i++){
            //锁对象使用，让不同对象能够同步方法
//            final String lock = "lock";
            final MultiSyncCommonOneTest test1 = new MultiSyncCommonOneTest();
            final MultiSyncCommonOneTest test2 = new MultiSyncCommonOneTest();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    test1.addCount();
//                    test1.addCount(lock);
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    test2.addCount();
//                    test2.addCount(lock);
                }
            }).start();
        }
    }
}
