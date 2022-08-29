package com.example.redissubcriber.config;

import com.example.redissubcriber.subscriber.RedisReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Configuration
public class RedisConfig {

    @Value("${host.redis}")
    private  String redisHost;

    @Value("${port.redis}")
    private int redisPort;

    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration factory = new RedisStandaloneConfiguration();
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        return new JedisConnectionFactory(factory);
    }

    @Bean
    public RedisMessageListenerContainer container(JedisConnectionFactory jedisConnectionFactory,
                                                   @Qualifier("notificationListenerAdapter") MessageListenerAdapter notificationListenerAdapter,
                                                   @Qualifier("bulkListenerAdapter") MessageListenerAdapter bulkListenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(notificationListenerAdapter,  new PatternTopic("notification"));
        container.addMessageListener(bulkListenerAdapter, new PatternTopic("bulk"));
        return container;
    }

    @Bean("notificationListenerAdapter")
    MessageListenerAdapter notificationListenerAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "receiveNotificationMessage");
    }

    @Bean("bulkListenerAdapter")
    MessageListenerAdapter countListenerAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "receiveBulkMessage");
    }

    @Bean
    RedisReceiver receiver() {
        return new RedisReceiver();
    }

//    @Bean
//    public ChannelTopic topic() {
//        return new ChannelTopic("messageChannel");
//    }


    @Bean
    StringRedisTemplate template(JedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }

}
