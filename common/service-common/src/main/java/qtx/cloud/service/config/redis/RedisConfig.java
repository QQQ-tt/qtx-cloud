package qtx.cloud.service.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qtx
 * @since 2022/10/30 20:52
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String host;

    private int port;

    private String password;

    private Integer timeout;

    private Integer database;

    @Bean

    public RedisTemplate<String, Object> redisTemplate() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setDatabase(database);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //???????????????????????????????????????
        ObjectMapper om = new ObjectMapper();
        // ??????LocalDateTime???????????????
        //LocalDatetime?????????
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(timeModule);
        // ???????????????????????????field,get???set,????????????????????????ANY???????????????private???public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        // fastjson redis ?????????????????? ????????????????????????????????????GenericFastJsonRedisSerializer
        //FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        StringRedisSerializer serializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer fastJsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        // ?????????????????????????????????,spring??????????????? LettuceConnectionFactory ????????????
        factory.afterPropertiesSet();

        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        // ?????????????????????????????????,spring??????????????? ????????????
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * ?????? redis ????????????????????????
     * ??????@cacheable ???????????????
     *
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        GenericJackson2JsonRedisSerializer fastJsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                fastJsonRedisSerializer)).entryTtl(Duration.ofMinutes(timeout));
        return configuration;
    }
}
