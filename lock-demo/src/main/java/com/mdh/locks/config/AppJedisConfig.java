package com.mdh.locks.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author madonghao
 * @create 2020-01-17 15:01
 **/
public class AppJedisConfig {

    @Bean
    public JedisPool jedisPoolConfig(RedisProperties properties) throws Exception {
        //1、连接工厂中所有信息都有。
        JedisPoolConfig config = new JedisPoolConfig();

        RedisProperties.Pool pool = properties.getJedis().getPool();


        //这些配置
        config.setMaxIdle(pool.getMaxIdle());
        config.setMaxTotal(pool.getMaxActive());

        JedisPool jedisPool = null;
        jedisPool = new JedisPool(config, properties.getHost(), properties.getPort());
        return jedisPool;
    }
}
