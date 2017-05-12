package com.msj.storm.kafka;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class PrinterBolt extends BaseBasicBolt {

	public void execute(Tuple input, BasicOutputCollector collector) {
		// get the sentence from the tuple and print it
		String sentence = input.getString(0);
		System.out.println("Received Sentence:" + sentence);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// we don't emit anything
	}
}
