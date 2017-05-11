package com.msj.storm.bolt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class PrintBolt extends BaseBasicBolt {

	private static final Log log = LogFactory.getLog(PrintBolt.class);
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		//获取上一个组件所声明的Field
		String print = input.getStringByField("print");
		log.info("【print】： " + print);
		//System.out.println("Name of input word is : " + word);
		//进行传递给下一个bolt
		collector.emit(new Values(print));
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("write"));
	}

}
