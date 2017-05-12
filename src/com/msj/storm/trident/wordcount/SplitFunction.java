package com.msj.storm.trident.wordcount;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
import backtype.storm.tuple.Values;
/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 测试Trident WordCount
 */
public class SplitFunction extends BaseFunction {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String subjects = tuple.getStringByField("subjects");
		//获取tuple输入内容
		//逻辑处理，然后发射给下一个组件
		for(String sub : subjects.split(" ")) {
			collector.emit(new Values(sub));
		}
	}
}
