package com.msj.sync.queue;

import java.util.concurrent.DelayQueue;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/14 下午7:54
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 使用DelayQueue 根据网民上网的下机来说明使用
 */
public class UseDelayQueue implements Runnable{

    DelayQueue<WangMin> delayQueue = new DelayQueue<>();

    public boolean yinye = true;

    public void shangji(int id,String name,int money){
        WangMin wangMin = new WangMin(id,name,1000*money+System.currentTimeMillis());
        System.out.println("网名：" + wangMin.getName()+ "id："+ wangMin.getId()+ "交钱"+ money +"块，开始上机");
        this.delayQueue.add(wangMin);
    }

    public void xiaji(WangMin wangMin){
        System.out.println("网名：" + wangMin.getName()+ "id："+ wangMin.getId()+ "时间到下机");
    }

    @Override
    public void run() {
        while(yinye){
            try{
                WangMin wangMin = delayQueue.take();
                xiaji(wangMin);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){

        System.out.println("网吧开始营业");
        UseDelayQueue useDelayQueue = new UseDelayQueue();
        Thread shangwang = new Thread(useDelayQueue);
        shangwang.start();

        useDelayQueue.shangji(1,"路人甲",3);

        useDelayQueue.shangji(2,"路人乙",8);

        useDelayQueue.shangji(3,"路人丙",6);

    }

}
