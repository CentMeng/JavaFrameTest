package com.msj.sync;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/13 下午4:05
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc
 */
public class MultiSyncCommonOneTest {

    private static volatile int count = 0;

    public  void addCount(){
        count ++;
        System.out.println(""+count);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){



        for(int i=0;i<1000;i++){
            final MultiSyncCommonOneTest test1 = new MultiSyncCommonOneTest();
            final MultiSyncCommonOneTest test2 = new MultiSyncCommonOneTest();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    test1.addCount();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    test2.addCount();
                }
            }).start();
        }
    }
}
