package com.msj.storm.trident.example;

import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFilter;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.tuple.TridentTuple;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 测试Trident过滤功能
 */
public class TridentFilter {
	
	//继承BaseFilter类，重新isKeep方法
	public static class CheckFilter extends BaseFilter {
		@Override
		public boolean isKeep(TridentTuple tuple) {
			int a = tuple.getInteger(0);
			int b = tuple.getInteger(1);
			if((a + b) % 2 == 0){
				return true;
			}
			return false;
		}
	}
	
	//继承BaseFunction类，重新execute方法
	public static class Result extends BaseFunction {
		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			//获取tuple输入内容
			Integer a = tuple.getIntegerByField("a");
			Integer b = tuple.getIntegerByField("b");
			Integer c = tuple.getIntegerByField("c");
			Integer d = tuple.getIntegerByField("d");
			System.out.println("a: "+ a + ", b: " + b + ", c: " + c + ", d: " + d);
		}
	}

    public static StormTopology buildTopology() {
        TridentTopology topology = new TridentTopology();
		//设定数据源
		FixedBatchSpout spout = new FixedBatchSpout(
		new Fields("a", "b", "c", "d"),	//声明输入的域字段为"a"、"b"、"c"、"d"
		4, 						//设置批处理大小为1
		//设置数据源内容
		//测试数据
		new Values(1, 4, 7, 10),
		new Values(1, 1, 3, 11),
		new Values(2, 2, 7, 1),
		new Values(2, 5, 7, 2));
		//指定是否循环
		spout.setCycle(false);
		//指定输入源spout
        Stream inputStream = topology.newStream("spout", spout);
        /**
         * 要实现流sqout - bolt的模式 在trident里是使用each来做的
         * each方法参数：
         * 1.输入数据源参数名称：subjects
         * 2.需要流转执行的function对象（也就是bolt对象）：new Split()
         */
        inputStream.each(new Fields("a", "b", "c", "d"), new CheckFilter())
                //继续使用each调用下一个function（bolt）输入参数为subject和count，第二个参数为new Result() 也就是执行函数，第三个参数为没有输出
                .each(new Fields("a", "b", "c", "d"), new Result(), new Fields());
        return topology.build();	//利用这种方式，我们返回一个StormTopology对象，进行提交
    }
	
	public static void main(String[] args) throws Exception {
	
	  	Config conf = new Config();
	  	//设置batch最大处理
	  	conf.setNumWorkers(2);
	  	conf.setMaxSpoutPending(20);
	  	if(args.length == 0) {
	        LocalCluster cluster = new LocalCluster();
	        cluster.submitTopology("trident-function", conf, buildTopology());
	        Thread.sleep(10000);
	        cluster.shutdown();
	  	} else {
	  		StormSubmitter.submitTopology(args[0], conf, buildTopology());
	  	}	
		
	}
}
