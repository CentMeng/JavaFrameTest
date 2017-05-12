package com.msj.storm.kafka;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc kafka与storm，缺少jar包，所以需要jar包地方屏蔽掉
 */
public class KafkaTopology {
	public static void main(String[] args) throws
		AlreadyAliveException, InvalidTopologyException {
		// zookeeper hosts for the Kafka cluster
		//缺jar包
//		ZkHosts zkHosts = new ZkHosts("192.168.1.114:2181");

		// Create the KafkaSpout configuartion
		// Second argument is the topic name
		// Third argument is the zookeeper root for Kafka
		// Fourth argument is consumer group id
		//缺jar包
//		SpoutConfig kafkaConfig = new SpoutConfig(zkHosts,
//				"words_topic", "", "id7");

		// Specify that the kafka messages are String
		//缺jar包
//		kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

		// We want to consume all the first messages in the topic everytime
		// we run the topology to help in debugging. In production, this
		// property should be false
		//缺jar包
//		kafkaConfig.forceFromStart = true;

		// Now we create the topology
		TopologyBuilder builder = new TopologyBuilder();

		// set the kafka spout class
		//缺jar包
//		builder.setSpout("KafkaSpout", new KafkaSpout(kafkaConfig), 1);

		// configure the bolts
		builder.setBolt("SentenceBolt", new com.msj.storm.kafka.SentenceBolt(), 1).globalGrouping("KafkaSpout");
		builder.setBolt("PrinterBolt", new com.msj.storm.kafka.PrinterBolt(), 1).globalGrouping("SentenceBolt");


		// create an instance of LocalCluster class for executing topology in local mode.
		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();

		// Submit topology for execution
		cluster.submitTopology("KafkaToplogy", conf, builder.createTopology());


		try {
			// Wait for some time before exiting
			System.out.println("Waiting to consume from kafka");
			Thread.sleep(10000);
		} catch (Exception exception) {
			System.out.println("Thread interrupted exception : " + exception);
		}

		// kill the KafkaTopology
		cluster.killTopology("KafkaToplogy");

		// shut down the storm test cluster
		cluster.shutdown();
	}
}
