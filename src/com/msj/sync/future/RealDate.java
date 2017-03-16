package com.msj.sync.future;

/**
 * @author mengxiangcheng mengshaojie@188.com
 * @date 2017/3/15 下午1:53
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 真实对象，放到包装对象类中.获取真实数据
 */
public class RealDate implements Date{

    private String result;

    public  RealDate(String queryStr){
        System.out.println("根据:"+queryStr+"进行查询，这是一个很耗时的操作");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("操作完毕，获取结果");
        this.result = "查询结果";
    }

    @Override
    public String getRequest(){
        return result;
    }

}
