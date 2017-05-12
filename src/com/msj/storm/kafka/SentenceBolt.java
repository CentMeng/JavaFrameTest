package com.msj.storm.kafka;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SentenceBolt extends BaseBasicBolt {

	// list used for aggregating the words
	private List<String> words = new ArrayList<String>();

	public void execute(Tuple input, BasicOutputCollector collector) {
		// Get the word from the tuple
		String word = input.getString(0);

		if(StringUtils.isBlank(word)){
			// ignore blank lines
			return;
		}

		System.out.println("Received Word:" + word);

		// add word to current list of words
		words.add(word);

		if (word.endsWith(".")) {
			// word ends with '.' which means this is the end of
			// the sentence publishes a sentence tuple
			collector.emit(ImmutableList.of(
					(Object) StringUtils.join(words, ' ')));

			// and reset the words list.
			words.clear();
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// here we declare we will be emitting tuples with
		// a single field called "sentence"
		declarer.declare(new Fields("sentence"));
	}
}
