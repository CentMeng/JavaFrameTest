package com.msj.storm.trident.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.testing.FixedBatchSpout;
/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 测试Trident WordCount 聚合示例
 */
public class WordCountTopology {
	
    public static StormTopology buildTopology() {
    	TridentTopology topology = new TridentTopology();
        
		//设定数据源
		FixedBatchSpout spout = new FixedBatchSpout(
		new Fields("subjects"),	//声明输入的域字段为"numbers"
		4, 						//设置批处理大小为4
		//设置数据源内容
		//测试数据1
//		new Values("java java c++ python ruby java java python python c++"));	
		//测试数据2
		new Values("java java php ruby c++"),
		new Values("java python python python c++"),
		new Values("java java java java ruby"),
		new Values("c++ java ruby php java"));
		//指定是否循环
		spout.setCycle(false);
		
    	//使用IBatchSpout接口实例化一个spout
        //SubjectsSpout spout = new SubjectsSpout(4);
		//指定输入源spout
        Stream inputStream = topology.newStream("spout", spout);
        /**
         * 要实现流sqout - bolt的模式 在trident里是使用each来做的
         * each方法参数：
         * 1.输入数据源参数名称：subjects
         * 2.需要流转执行的function对象（也就是bolt对象）：new Split()
         * 3.指定function对象里的输出参数名称：subject
         */
        //设置随机分组
        inputStream.shuffle()
        		.each(new Fields("subjects"), new SplitFunction(), new Fields("sub"))
        		//进行分组操作：参数为分组字段subject，比较类似于我们之前所接触的FieldsGroup
                .groupBy(new Fields("sub"))
                //对分组之后的结果进行聚合操作:参数1为聚合方法为count函数，输出字段名称为count
                .aggregate(new Count(), new Fields("count"))
                //继续使用each调用下一个function（bolt）输入参数为subject和count，第二个参数为new Result() 也就是执行函数，第三个参数为没有输出
                .each(new Fields("sub", "count"), new ResultFunction(), new Fields())
                .parallelismHint(1);
        return topology.build();	//利用这种方式，我们返回一个StormTopology对象，进行提交
    }	
	
	public static void main(String[] args) throws Exception {
		
	  	Config conf = new Config();
	  	//设置batch最大处理
	  	conf.setNumWorkers(2);
	  	conf.setMaxSpoutPending(20);
	  	if(args.length == 0) {
	        LocalCluster cluster = new LocalCluster();
	        cluster.submitTopology("trident-wordcount", conf, buildTopology());
	        Thread.sleep(10000);
	        cluster.shutdown();
	  	} else {
	  		StormSubmitter.submitTopology(args[0], conf, buildTopology());
	  	}
	}
}
