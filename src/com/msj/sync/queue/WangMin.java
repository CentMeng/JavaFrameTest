package com.msj.sync.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/3/14 下午7:55
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 网民信息用于UseDelayQueue
 */
public class WangMin implements Delayed{

    private int id;

    private String name;

    private long endtime;

    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public WangMin(int id,String name,long endtime){
        this.id = id;
        this.name = name;
        this.endtime = endtime;
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

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * 用来判断是否到了截止时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return endtime - System.currentTimeMillis();
    }

    /**
     * 相互比较排序用
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        WangMin w = (WangMin) o;
        return this.getDelay(this.timeUnit)-w.getDelay(this.timeUnit) > 0 ?1:0;
    }
}
