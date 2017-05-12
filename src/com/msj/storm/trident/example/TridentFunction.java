package com.msj.storm.trident.example;

import storm.trident.Stream;
import storm.trident.TridentTopology;
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
 * @desc 测试TridentFunction
 */
public class TridentFunction {
	
	//继承BaseFunction类，重新execute方法
	public static class SumFunction extends BaseFunction {
		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			System.out.println("传入进来的内容为：" + tuple);
			//获取a、b俩个域
			int a = tuple.getInteger(0);
			int b = tuple.getInteger(1);
			int sum = a + b;
			//发射数据
			collector.emit(new Values(sum));
		}
	}
	
	//继承BaseFunction类，重新execute方法
	public static class Result extends BaseFunction {
		@Override
		public void execute(TridentTuple tuple, TridentCollector collector) {
			//获取tuple输入内容
			System.out.println();
			Integer a = tuple.getIntegerByField("a");
			Integer b = tuple.getIntegerByField("b");
			Integer c = tuple.getIntegerByField("c");
			Integer d = tuple.getIntegerByField("d");
			System.out.println("a: "+ a + ", b: " + b + ", c: " + c + ", d: " + d);
			Integer sum = tuple.getIntegerByField("sum");
			System.out.println("sum: "+ sum);
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
         * 1.输入数据源参数名称："a", "b", "c", "d"
         * 2.需要流转执行的function对象（也就是bolt对象）：new SumFunction()
         * 3.指定function对象里的输出参数名称：sum
         */
        inputStream.each(new Fields("a", "b", "c", "d"), new SumFunction(), new Fields("sum"))
        		/**
        		 *  继续使用each调用下一个function（bolt）
        		 *	第一个输入参数为："a", "b", "c", "d", "sum"
        		 *	第二个参数为：new Result() 也就是执行函数，第三个参数为没有输出
        		 */
                .each(new Fields("a", "b", "c", "d", "sum"), new Result(), new Fields());
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
