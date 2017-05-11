package com.msj.storm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.msj.storm.bolt.WordCountBolt;
import com.msj.storm.bolt.WordReportBolt;
import com.msj.storm.bolt.WordSplitBolt;
import com.msj.storm.spout.WordSpout;
import com.msj.utils.ThreadUtils;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午8:20
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 查看出现频率-Topology
 */
public class WordTopology {

	//定义常量
    private static final String WORD_SPOUT_ID = "word-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String REPORT_BOLT_ID = "report-bolt";
    private static final String TOPOLOGY_NAME = "word-count-topology";

    public static void main(String[] args) throws Exception {

    	//实例化对象
        WordSpout spout = new WordSpout();
        WordSplitBolt splitBolt = new WordSplitBolt();
        WordCountBolt countBolt = new WordCountBolt();
        WordReportBolt reportBolt = new WordReportBolt();

        //构建拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(WORD_SPOUT_ID, spout);
        
        // WordSpout --> WordSplitBolt
        builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(WORD_SPOUT_ID);
        
        // WordSplitBolt --> WordCountBolt
        builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));
        
        // WordCountBolt --> WordReportBolt
        builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID);

        //本地配置
        Config config = new Config();
        config.setDebug(false);
        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        ThreadUtils.waitForSeconds(10);
        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();
        
    }
}
