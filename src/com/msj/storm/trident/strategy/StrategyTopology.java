package com.msj.storm.trident.strategy;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 测试Trident分组功能
 */
public class StrategyTopology {
	
    public static StormTopology buildTopology() {
        TridentTopology topology = new TridentTopology();
		//设定数据源
		FixedBatchSpout spout = new FixedBatchSpout(
		new Fields("sub"),	//声明输入的域字段为"sub"
		4, 						//设置批处理大小为4
		//设置数据源内容
		//测试数据
		new Values("java"),
		new Values("python"),
		new Values("php"),
		new Values("c++"),
		new Values("ruby"));
		//指定是否循环
		spout.setCycle(true);
		//指定输入源spout
        Stream inputStream = topology.newStream("spout", spout);
        /**
         * 要实现流sqout - bolt的模式 在trident里是使用each来做的
         * each方法参数：
         * 1.输入数据源参数名称："sub"
         * 2.需要流转执行的function对象（也就是bolt对象）：new WriteFunction()
         * 3.指定function对象里的输出参数名称,没有则不输出任何内容
         */
        inputStream
        //随机分组：shuffle 
        .shuffle()
        //分区分组：partitionBy
        //.partitionBy(new Fields("sub"))
        //全局分组：global
        //.global()
        //广播分组：broadcast
        //.broadcast()
        .each(new Fields("sub"), new WriteFunction(), new Fields()).parallelismHint(4);
        return topology.build();	//利用这种方式，我们返回一个StormTopology对象，进行提交
    }
	
	public static void main(String[] args) throws Exception {
	
	  	Config conf = new Config();
	  	//设置batch最大处理
	  	conf.setNumWorkers(2);
	  	conf.setMaxSpoutPending(20);
	  	if(args.length == 0) {
	        LocalCluster cluster = new LocalCluster();
	        cluster.submitTopology("trident-strategy", conf, buildTopology());
	        Thread.sleep(5000);
	        cluster.shutdown();
	  	} else {
	  		StormSubmitter.submitTopology(args[0], conf, buildTopology());
	  	}	
		
	}
}
