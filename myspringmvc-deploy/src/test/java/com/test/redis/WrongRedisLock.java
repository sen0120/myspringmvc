package com.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WrongRedisLock {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";//只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。 只在键已经存在时，才对键进行设置操作。
    private static final String SET_WITH_EXPIRE_TIME = "PX";//单位,EX second ：设置键的过期时间为 second 秒.PX millisecond ：设置键的过期时间为 millisecond 毫秒

    static {
        Properties properties = new Properties();
        InputStream in = WrongRedisLock.class.getClassLoader().getResourceAsStream("config/config.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String maxIdle = properties.getProperty("redis.maxIdle");
        String maxTotal = properties.getProperty("redis.maxTotal");
        String maxWaitMillis = properties.getProperty("redis.maxWaitMillis");
        String testOnBorrow = properties.getProperty("redis.testOnBorrow");

        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(Integer.parseInt(maxTotal));
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(Integer.parseInt(maxIdle));
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));


        String host = properties.getProperty("redis.host");
        int port = Integer.parseInt(properties.getProperty("redis.port"));
        String pass = properties.getProperty("redis.pass");
        int timeout = Integer.parseInt(properties.getProperty("redis.timeout"));

        List<JedisShardInfo> shares = new ArrayList<>();
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port, timeout);
        shares.add(jedisShardInfo);
        ShardedJedisPool pool = new ShardedJedisPool(config, shares);
        ShardedJedis shardedJedis = pool.getResource();
        long expireTime = 1000L;
        /*
        shardedJedis.set第三个参数 String nxxx,
        NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
        XX ：只在键已经存在时，才对键进行设置操作。
        */
        /*
         shardedJedis.set第四个参数 String expx,
         EX second ：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value 。
         PX millisecond ：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value 。
         */
        String result = shardedJedis.set("lockKey", "requestId", SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (result.equals("1")) {
            System.out.println("get lock success");
        } else {
            System.out.println("get lock failure");
        }


        shardedJedis.close();

    }

    public static void main(String[] args) {
//        single();
    }

    /**
     * 单台才支持事务
     */
    private static void single() {
        JedisPool jedisPool = new JedisPool();
        Jedis jedis = jedisPool.getResource();
        jedis.watch("key");//阻塞key,加锁
        Transaction transaction = jedis.multi();
        transaction.setnx("key", "value");
        transaction.expire("key", 1);
        transaction.exec();
        jedis.close();
        jedis.unwatch();//释放锁

    }
}
