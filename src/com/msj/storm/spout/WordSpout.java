package com.msj.storm.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.msj.utils.ThreadUtils;

import java.util.Map;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午8:20
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 查看出现频率-spout
 */
public class WordSpout implements IRichSpout {

	private static final long serialVersionUID = 1L;
	
	private SpoutOutputCollector collector;
	 
	private int index = 0;
	
    private String[] sentences = {
            "my dog has fleas",
            "i like cold beverages",
            "the dog ate my homework",
            "don't have a cow man",
            "i don't think i like fleas"
    };
	
	@Override
	public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}
	
	@Override
	public void nextTuple() {
		this.collector.emit(new Values(sentences[index]));
        index++;
        if (index >= sentences.length) {
            index = 0;
        }
        ThreadUtils.waitForSeconds(1);
	}
	

	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}
	
	@Override
	public void ack(Object msgId) {
	}
	
	@Override
	public void fail(Object msgId) {
	}

	@Override
	public void activate() {
		
	}

	@Override
	public void close() {
		
	}

	@Override
	public void deactivate() {
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
