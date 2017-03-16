package com.msj.sync.queue;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/14 下午7:35
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 实现Comparable的Task，用于UsePriorityBlockingQueue
 */
public class Task implements Comparable<Task>{

    private int id;

    private String name;

    private String age;

    @Override
    public int compareTo(Task task) {
        //-1 0 1这样顺序排序
        return this.id>task.id? 1 : (this.id<task.id ? -1 :0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
