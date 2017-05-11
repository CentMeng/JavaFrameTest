package com.msj.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import java.util.*;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午8:20
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 查看出现频率-记录bolt
 */
public class WordReportBolt implements IRichBolt {

    private HashMap<String, Long> counts = null;
    
	@Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.counts = new HashMap<String, Long>();
	}
	
	@Override
	public void execute(Tuple tuple) {
        String word = tuple.getStringByField("word");
        Long count = tuple.getLongByField("count");
        this.counts.put(word, count);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	@Override
	public void cleanup() {
        System.out.println("----------- FINAL COUNTS -----------");
        List<String> keys = new ArrayList<String>();
        keys.addAll(this.counts.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            System.out.println(key + " : " + this.counts.get(key));
        }
        System.out.println("-----------------------------------");
	}

}
