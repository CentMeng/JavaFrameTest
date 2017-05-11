package com.msj.storm.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import com.msj.storm.bolt.PrintBolt;
import com.msj.storm.bolt.WriteBolt;
import com.msj.storm.spout.PWSpout;

/**
 * <B>系统名称：</B>工作进程、并行度、任务<BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 北京尚学堂（alienware）
 * @since 2015年12月3日
 */
public class PWTopology2 {

	public static void main(String[] args) throws Exception {
		
		Config cfg = new Config();
		cfg.setNumWorkers(2);//设置使用俩个工作进程
		cfg.setDebug(false);
		TopologyBuilder builder = new TopologyBuilder();
		//设置sqout的并行度和任务数（产生2个执行器和俩个任务）
		builder.setSpout("spout", new PWSpout(), 2);//.setNumTasks(2);
		//设置bolt的并行度和任务数:（产生2个执行器和4个任务）
		builder.setBolt("print-bolt", new PrintBolt(), 2).shuffleGrouping("spout").setNumTasks(4);
		//设置bolt的并行度和任务数:（产生6个执行器和6个任务）
		builder.setBolt("write-bolt", new WriteBolt(), 6).shuffleGrouping("print-bolt");
		
		
		//1 本地模式
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology("top2", cfg, builder.createTopology());
//		Thread.sleep(10000);
//		cluster.killTopology("top2");
//		cluster.shutdown();
		
		//2 集群模式
		StormSubmitter.submitTopology("top2", cfg, builder.createTopology());
		
	}
}
