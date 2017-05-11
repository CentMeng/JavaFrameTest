package com.msj.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 传递消息并且实时处理-输出写入bolt
 */
public class WriterBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;

	private FileWriter writer;

	private OutputCollector collector;

	@Override
	public void prepare(Map config, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		try {
			writer = new FileWriter("d://message.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean flag = false;
	
	@Override
	public void execute(Tuple tuple) {
		String word = tuple.getString(0);
//		List<String> list = (List<String>)tuple.getValueByField("word");
//		System.out.println("======================" + list);
		try {
			if(!flag && word.equals("hadoop")){
				flag = true;
				int a = 1/0;
			}
			writer.write(word);
			writer.write("\r\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			collector.fail(tuple);
		}
		collector.emit(tuple, new Values(word));
		collector.ack(tuple);
	}

	@Override
	public void cleanup() {

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
