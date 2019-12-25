package com.mdh.gmall.pms.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

/**
 * redis的配置
 *
 * 原生的redis序列化是采用jdk的序列化
 * 改成json的序列化方式
 * @author madonghao
 * @create 2019-12-25 10:53
 **/
@Component
public class PmsRedisConfig {

    /**
     * 在RedisAutoConfiguration中@ConditionalOnMissingBean(name = "redisTemplate")
     * 只需配置相同名称的bean即可改变原生的redis的配置
     *
     * 在RedisTemplate<K, V> 类中配置了关于redis的相关配置
     * if (this.defaultSerializer == null) {
     *             this.defaultSerializer = new JdkSerializationRedisSerializer(this.classLoader != null ? this.classLoader : this.getClass().getClassLoader());
     *         }
     * 修改默认配置
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置序列化规则为可支持泛型的json序列化
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
