package com.msj.storm.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Map;


/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午2:57
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc Redis与storm-初始化Jedis
 */
public class RedisOperations implements Serializable {

	private static final long serialVersionUID = 1L;
	Jedis jedis = null;

	public RedisOperations(String redisIP, int port) {
		// Connecting to Redis on localhost，也可以使用集群
		jedis = new Jedis(redisIP, port);
	}

	public void insert(Map<String, Object> record, String id) {
		try {
			jedis.set(id, new ObjectMapper().writeValueAsString(record));
		} catch (Exception e) {
			System.out.println("Record not persist into datastore : ");
		}
	}
}
