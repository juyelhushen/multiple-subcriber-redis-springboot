package com.example.redissubcriber.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);


    public void receiveNotificationMessage(String message) {
        LOGGER.info("Message Received from notification channel: <" + message + ">");

    }

    public void receiveBulkMessage(String message) {
        LOGGER.info("Message Received from count channel: <" + message + ">");
    }
}
