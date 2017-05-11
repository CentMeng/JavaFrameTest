package com.msj.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 传递消息并且实时处理-单词分割
 */
public class SpliterBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;

	private OutputCollector collector;
	
	@Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}
	
	
	private boolean flag = false;
	
	@Override
	public void execute(Tuple tuple) {
		try {
			String subjects = tuple.getStringByField("subjects");
			//放开此处可以模拟splitbolt错误情况
//			if(!flag && subjects.equals("flume,activiti")){
//				flag = true;
//				int a = 1/0;
//			}
			
			String[] words = subjects.split(",");
			//List<String> list = new ArrayList<String>();
			//int index = 0; 
			for (String word : words) {
				//注意这里循环发送消息，要携带tuple对象，用于处理异常时重发策略
				collector.emit(tuple, new Values(word));
				//list.add(word);
				//index ++;
			}
			//collector.emit(tuple, new Values(list));
			collector.ack(tuple);
		} catch (Exception e) {
			e.printStackTrace();
			collector.fail(tuple);
		}
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
	
	@Override
	public void cleanup() {

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
