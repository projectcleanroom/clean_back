package com.clean.cleanroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Redis 연결 팩토리 설정
        template.setConnectionFactory(redisConnectionFactory);

        // 키는 String으로 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        // 값은 JSON으로 직렬화하여 객체를 저장 가능하게 설정
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Hash의 키와 값에 대한 직렬화 설정
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 트랜잭션 지원 여부 설정
        template.setEnableTransactionSupport(true);

        template.afterPropertiesSet();
        return template;
    }
}
