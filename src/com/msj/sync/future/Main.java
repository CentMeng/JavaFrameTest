package com.msj.sync.future;

/**
 * @author mengxiangcheng mengshaojie@188.com
 * @date 2017/3/15 下午2:19
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc
 */
public class Main {

    public static void main(String[] args){

        FutureClient fc = new FutureClient();
        Date date = fc.request("请求参数");
        System.out.println("请求发送成功");
        System.out.println("做请他的事情");

        String result =  date.getRequest();
        System.out.println(result);
    }
}
