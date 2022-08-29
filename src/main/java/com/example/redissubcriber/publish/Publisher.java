package com.example.redissubcriber.publish;

import com.example.redissubcriber.subscriber.RedisReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    private RedisReceiver receiver;

    @Value("${Topic.name}")
    private String topic;

    @Value("${BulkTopic.name}")
    private String bulkTopic;

    private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Publisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendEmail(String message) {
        redisTemplate.convertAndSend(topic, message);
    }


    //for bulk messages

    public void sendBulkMessage(String message) {
        redisTemplate.convertAndSend(bulkTopic, message);
    }


}
