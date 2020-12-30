package com.hardik.mcgonagall.configuration;

import java.util.UUID;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private Integer port;

	@Value("${spring.redis.jedis.pool.max-active}")
	private Integer poolMax;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private Integer poolMaxIdle;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private Integer poolMinIdle;

	@Bean
	public JedisClientConfiguration getJedisClientConfiguration() {
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder JedisPoolingClientConfigurationBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
				.builder();
		GenericObjectPoolConfig GenericObjectPoolConfig = new GenericObjectPoolConfig();
		GenericObjectPoolConfig.setMaxTotal(poolMax);
		GenericObjectPoolConfig.setMaxIdle(poolMaxIdle);
		GenericObjectPoolConfig.setMinIdle(poolMinIdle);
		return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build();
	}

	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);
		configuration.setPort(port);
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
