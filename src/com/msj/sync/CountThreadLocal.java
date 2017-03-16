package com.msj.sync;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/13 下午4:41
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc ThreadLocal使用，线程局部变量
 */
public class CountThreadLocal {

    public static ThreadLocal<String> th = new ThreadLocal<String>();

    public void setTh(String value){
        th.set(value);
    }
    public void getTh(){
        System.out.println(Thread.currentThread().getName() + ":" + this.th.get());
    }

    public static void main(String[] args) throws InterruptedException {

        final CountThreadLocal ct = new CountThreadLocal();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ct.setTh("张三");
                ct.getTh();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
//                    ct.setTh("李四");
                    ct.getTh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
