package com.msj.sync.future;

/**
 * @author mengxiangcheng mengshaojie@188.com
 * @date 2017/3/14 下午10:18
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 并行开发设计模式之Future
 */
public class FutureClient {

    public Date request(final String request){

        //代理对象，包装类。先返回给发送请求的客户端，告诉他请求已经接收到
        final FutureDate futureDate = new FutureDate();

        //启动一个新的线程，去加载真实的数据，传递给这个代理对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这个新线程可以慢慢加载真实对象，然后传递给代理对象
                RealDate realDate = new RealDate(request);
                futureDate.setRealDate(realDate);
            }
        }).start();

        return futureDate;

    }
}
