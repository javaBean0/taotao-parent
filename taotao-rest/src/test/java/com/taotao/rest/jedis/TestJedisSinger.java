package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedisSinger {

	//测试redis的get set 命令
	@Test
	public void testJedisSingie(){
		Jedis jedis = new Jedis( "192.168.0.129", 6379);
		jedis.set("key1", "jedis test");
		String str = jedis.get("key1");
		System.out.println(str);
		jedis.close();
	}
	
	/**
	 * 用连接池的redis
	 */
	@Test
	public void testJedisPool(){
		JedisPool pool = new JedisPool("192.168.0.129", 6379);
		Jedis jedis = pool.getResource();
		jedis.set("key1", "abcd");
		System.out.println(jedis.get("key1"));
		jedis.close();
		pool.close();
	}
	
	
	
	/**
	 * 集群版的redis测试
	 */
	
	
	@Test
	public void testJedisCluster(){
		HashSet<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.0.129", 7001));
		nodes.add(new HostAndPort("192.168.0.129", 7002));
		nodes.add(new HostAndPort("192.168.0.129", 7003));
		nodes.add(new HostAndPort("192.168.0.129", 7004));
		nodes.add(new HostAndPort("192.168.0.129", 7005));
		nodes.add(new HostAndPort("192.168.0.129", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("key1", "1000");
		String string = cluster.get("key1");
		System.out.println(string);
		cluster.close();
	}
	
	
	
	
	@Test
	public void TestSpringJedisSingle(){
		ApplicationContext application = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) application.getBean("redisClient");
		Jedis jedis = pool.getResource();
		//jedis.set("key2", "result");
		String str = jedis.get("key2");
		System.out.println(str);
		jedis.close();
		pool.close();
	
		
	}
	
	@Test
	public void TestSpringJedisCluster(){
		ApplicationContext application = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster cluster = (JedisCluster) application.getBean("redisClient");
		String str = cluster.get("key1");
		System.out.println(str);
		cluster.close();
	}
	
	
	
	
	
	
	
	 
	
	
	
	
	
	
	
	
	
}
