package com.msj.sync;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/14 上午11:11
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc ConcurrentMap使用
 */
public class UseConcurrentMap {

    public static void main(String[] args){
        ConcurrentHashMap<String,Object> chm = new ConcurrentHashMap<>();
        chm.put("k1","v1");
        chm.put("k2","v2");
        chm.put("k3","v3");
        //如果存在相同key的就不添加了
        chm.putIfAbsent("k3","vvv");

        for(Map.Entry<String,Object> me :chm.entrySet()){
            System.out.println("key:"+me.getKey()+" value:"+me.getValue());
        }

    }
}
