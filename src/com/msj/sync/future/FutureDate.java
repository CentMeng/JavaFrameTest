package com.msj.sync.future;

/**
 * @author mengxiangcheng mengshaojie@188.com
 * @date 2017/3/15 上午9:15
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc Date包装类,用于存放真实数据
 */
public class FutureDate implements Date{

    private RealDate realDate;

    private boolean isReady = false;

    public synchronized void setRealDate(RealDate realDate){

        //如果已经装载完毕了，就直接返回
        if(isReady){
            return;
        }

        //如果没有装载，进行装载真实对象
        this.realDate = realDate;
        isReady = true;

        //进行通知(很关键，配合wait(),)
        notify();
    }

    @Override
    public synchronized String getRequest() {
         //如果没加载好，程序就一直阻塞状态
        while(!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realDate.getRequest();
    }
}
