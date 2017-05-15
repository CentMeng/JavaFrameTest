package com.msj.storm.redis;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc Redis与storm
 */
public class Topology {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		
		TopologyBuilder builder = new TopologyBuilder();
		
		List<String> zks = new ArrayList<String>();
		zks.add("192.168.1.114");
		
		List<String> cFs = new ArrayList<String>();
		cFs.add("personal");
		cFs.add("company");
		
		// set the spout class
		builder.setSpout("spout", new SampleSpout(), 2);
		// set the bolt class
		builder.setBolt("bolt", new StormRedisBolt("192.168.1.114",6379), 2).shuffleGrouping("spout");

		Config conf = new Config();
		conf.setDebug(true);
		// create an instance of LocalCluster class for
		// executing topology in local mode.
		LocalCluster cluster = new LocalCluster();

		// StormRedisTopology is the name of submitted topology.
		cluster.submitTopology("StormRedisTopology", conf, builder.createTopology());
		try {
			Thread.sleep(10000);
		} catch (Exception exception) {
			System.out.println("Thread interrupted exception : " + exception);
		}
		// kill the StormRedisTopology
		cluster.killTopology("StormRedisTopology");
		// shutdown the storm test cluster
		cluster.shutdown();
}
}