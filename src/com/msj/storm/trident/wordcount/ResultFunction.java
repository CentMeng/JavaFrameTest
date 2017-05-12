package com.msj.storm.trident.wordcount;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;
/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 测试Trident WordCount
 */
public class ResultFunction extends BaseFunction {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		//获取tuple输入内容
		String sub = tuple.getStringByField("sub");
		Long count = tuple.getLongByField("count");
		System.out.println(sub +" : "+ count);
	}
}
