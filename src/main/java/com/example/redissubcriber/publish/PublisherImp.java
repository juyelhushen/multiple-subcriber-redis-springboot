package com.example.redissubcriber.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class PublisherImp {
    @Autowired
    private  Publisher publisher;

    @Value("${Topic.name}")
    private String topic;

    @Value("${BulkTopic.name}")
    private String bulkTopic;

    @RequestMapping(value = "/email" , method = RequestMethod.POST)
    public void publishing(@RequestBody String message){
        publisher.sendEmail(message);
    }

    @RequestMapping(value = "/bulk" , method = RequestMethod.POST)
    public void bulkPublishere(@RequestBody String message){
        publisher.sendBulkMessage(message);
    }
}
