package com.msj.storm.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import com.msj.storm.bolt.PrintBolt;
import com.msj.storm.bolt.WriteBolt;
import com.msj.storm.spout.PWSpout;


public class PWTopology1 {

	public static void main(String[] args) throws Exception {
		//
		Config cfg = new Config();
		cfg.setNumWorkers(2);
		cfg.setDebug(true);
		
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new PWSpout());
		//随机分组，在spout执行完毕后执行print-bolt
		builder.setBolt("print-bolt", new PrintBolt()).shuffleGrouping("spout");
		//执行完print-bolt之后执行write-bolt
		builder.setBolt("write-bolt", new WriteBolt()).shuffleGrouping("print-bolt");
		
		
		//1 本地模式
//		LocalCluster cluster = new LocalCluster();
		//第三个参数是写死的
//		cluster.submitTopology("top1", cfg, builder.createTopology());
//		Thread.sleep(10000);
//		cluster.killTopology("top1");
//		cluster.shutdown();
		
		//2 集群模式
		StormSubmitter.submitTopology("top1", cfg, builder.createTopology());
		
	}
}
