package com.msj.sync;

/**
 * synchronized可以使用任意的Object进行加锁
 * @author Vincent.M csdn@vip.163.com on 2017/3/13.
 */
public class ObjectLock {

    public void method1(){
        synchronized (this) { //对象锁
            try{
                System.out.println("do mehtod1");
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void method2(){
        synchronized (ObjectLock.class) { //类锁
            try{
                System.out.println("do mehtod2");
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    Object lock = new Object();
    public void method3(){
        synchronized (lock) { //任何对象锁
            try{
                System.out.println("do mehtod3");
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args){

        final ObjectLock objectLock = new ObjectLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method2();
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method3();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
