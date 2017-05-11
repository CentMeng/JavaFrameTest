package com.msj.storm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import com.msj.storm.bolt.SpliterBolt;
import com.msj.storm.bolt.WriterBolt;
import com.msj.storm.spout.MessageSpout;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 传递消息并且实时处理
 */
public class MessageTopology {
	
	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new MessageSpout());
		builder.setBolt("split-bolt", new SpliterBolt()).shuffleGrouping("spout");
		builder.setBolt("write-bolt", new WriterBolt()).shuffleGrouping("split-bolt");
        //本地配置
        Config config = new Config();
        config.setDebug(false);
        LocalCluster cluster = new LocalCluster();
        System.out.println(cluster);
        cluster.submitTopology("message", config, builder.createTopology());
        Thread.sleep(10000);
        cluster.killTopology("message");
        cluster.shutdown();
	}
}
