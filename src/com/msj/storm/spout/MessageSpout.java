package com.msj.storm.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 传递消息并且实时处理-数据源
 */
public class MessageSpout implements IRichSpout {

	private static final long serialVersionUID = 1L;

	private int index = 0;
	
	private String[] subjects = new String[]{
			"groovy,oeacnbase",
			"openfire,restful",
			"flume,activiti",
			"hadoop,hbase",
			"spark,sqoop"		
	};
	
	private SpoutOutputCollector collector;
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}
	
	@Override
	public void nextTuple() {
		if(index < subjects.length){
			String sub = subjects[index];
			//发送信息参数1 为数值， 参数2为msgId
			collector.emit(new Values(sub), index);
			index++;
		}
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("subjects"));
	}

	@Override
	public void ack(Object msgId) {
		System.out.println("【消息发送成功!!!】 (msgId = " + msgId +")");
	}

	@Override
	public void fail(Object msgId) {
		System.out.println("【消息发送失败!!!】  (msgId = " + msgId +")");
		System.out.println("【重发进行中...】");
		collector.emit(new Values(subjects[(Integer) msgId]), msgId);
		System.out.println("【重发成功!!!】");
	}

	@Override
	public void close() {

	}

	@Override
	public void activate() {

	}

	@Override
	public void deactivate() {

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
