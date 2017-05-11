package com.msj.storm.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PWSpout extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	
	private static final Map<Integer, String> map = new HashMap<Integer, String>();
	
	static {
		map.put(0, "java");
		map.put(1, "php");
		map.put(2, "groovy");
		map.put(3, "python");
		map.put(4, "ruby");
	}
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		//对spout进行初始化，必须要写这句话
		this.collector = collector;
		//System.out.println(this.collector);
	}
	
	/**
	 * <B>方法名称：</B>轮询tuple<BR>
	 * <B>概要说明：</B><BR>
	 * @see backtype.storm.spout.ISpout#nextTuple()
	 */
	@Override
	public void nextTuple() {
		//随机发送一个单词
		final Random r = new Random();
		int num = r.nextInt(5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.collector.emit(new Values(map.get(num)));
	}

	/**
	 * <B>方法名称：</B>declarer声明发送数据的field<BR>
	 * <B>概要说明：</B><BR>
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//进行声明
		declarer.declare(new Fields("print"));
	}



}
