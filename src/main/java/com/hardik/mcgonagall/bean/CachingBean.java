package com.hardik.mcgonagall.bean;

import java.util.UUID;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.hardik.mcgonagall.configuration.RedisConfiguration;
import com.hardik.mcgonagall.configuration.RedisConfiguration.Jedis;
import com.hardik.mcgonagall.configuration.RedisConfiguration.Jedis.Pool;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableConfigurationProperties(value = RedisConfiguration.class)
public class CachingBean {

	private final RedisConfiguration redisConfiguration; 

	@Bean
	public JedisClientConfiguration getJedisClientConfiguration() {
		final var configuration = redisConfiguration.getJedis().getPool();
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder JedisPoolingClientConfigurationBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
				.builder();
		GenericObjectPoolConfig GenericObjectPoolConfig = new GenericObjectPoolConfig();
		GenericObjectPoolConfig.setMaxTotal(configuration.getMaxActive());
		GenericObjectPoolConfig.setMaxIdle(configuration.getMaxIdle());
		GenericObjectPoolConfig.setMinIdle(configuration.getMinIdle());
		return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build();
	}

	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisConfiguration.getHost());
		configuration.setPort(redisConfiguration.getPort());
		return new JedisConnectionFactory(configuration, getJedisClientConfiguration());
	}

	@Bean
	public RedisTemplate<UUID, Object> redisTemplate() {
		RedisTemplate<UUID, Object> template = new RedisTemplate<UUID, Object>();
		template.setConnectionFactory(connectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}

}
