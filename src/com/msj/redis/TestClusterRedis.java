package com.msj.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/4/10 下午8:20
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc
 */
public class TestClusterRedis {

	public static void main(String[] args) {
	    
	    Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
	    jedisClusterNode.add(new HostAndPort("192.168.1.171", 7001));
	    jedisClusterNode.add(new HostAndPort("192.168.1.171", 7002));
	    jedisClusterNode.add(new HostAndPort("192.168.1.171", 7003));
	    jedisClusterNode.add(new HostAndPort("192.168.1.171", 7004));
	    jedisClusterNode.add(new HostAndPort("192.168.1.171", 7005));
	    jedisClusterNode.add(new HostAndPort("192.168.1.171", 7006));
	    //GenericObjectPoolConfig goConfig = new GenericObjectPoolConfig();
	    //JedisCluster jc = new JedisCluster(jedisClusterNode,2000,100, goConfig);
	    JedisPoolConfig cfg = new JedisPoolConfig();
	    cfg.setMaxTotal(100);
	    cfg.setMaxIdle(20);
	    cfg.setMaxWaitMillis(-1);
	    cfg.setTestOnBorrow(true); 
	    JedisCluster jc = new JedisCluster(jedisClusterNode,6000,1000,cfg);	    
	    
	    System.out.println(jc.set("age","20"));
	    System.out.println(jc.set("sex","男"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("name"));
	    System.out.println(jc.get("age"));
	    System.out.println(jc.get("sex"));
	    jc.close();
		
		
	}
	
}
