package com.msj.sync.queue;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/14 下午7:35
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc
 */
public class UsePriorityBlockingQueue {

    public static void main(String[] args){

        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        Task t1 = new Task();
        t1.setId(4);
        Task t3 = new Task();
        t3.setId(3);
        Task t2 = new Task();
        t2.setId(1);

        queue.add(t1);
        queue.add(t2);
        queue.add(t3);

        //并没有进行所有的排序
        for(Iterator iterator = queue.iterator();iterator.hasNext();){
            Task task = (Task) iterator.next();
            System.out.println("task id "+ task.getId());
        }

        //take的时候进行排序，然后取出优先级最高的
        try {
            System.out.println("容器："+queue.take().getId());
            System.out.println("容器："+queue.take().getId());
            System.out.println("容器："+queue.take().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
