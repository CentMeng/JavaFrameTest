package com.msj.sync.disruptor.base;

/**
 * 要生产的对象。把一个个生产的对象（生产一堆）传到Disruptor中的RingBuffer容器中（环形结构），消费者在RingBuffer中注册监听事件，然后RingBuffer一旦有数据就会主动把生产对象传给消费者处理。
 * 当RingBuffer容器中有数据的时候，
 */
public class LongEvent { 
    private long value;
    public long getValue() { 
        return value; 
    } 
 
    public void setValue(long value) { 
        this.value = value; 
    } 
} 