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
 * @date 2017/5/11 下午8:20
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 查看出现频率-分割bolt
 */
public class WordSplitBolt implements IRichBolt{

	private static final String ERROR_STR = "don't have a cow man";
	
	private OutputCollector collector;
	
	@Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		 this.collector = collector;
	}
	

	@Override
	public void execute(Tuple tuple) {
	        String sentence = tuple.getStringByField("sentence");
	        String[] words = sentence.split(" ");
	        for(String word : words){
	            this.collector.emit(new Values(word));
	            //this.collector.ack(tuple);
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
