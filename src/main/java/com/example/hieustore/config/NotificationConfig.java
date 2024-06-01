package com.example.hieustore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {
    @Value("${notification.chat.id}")
    public String CHAT_ID;

    @Value("${notification.chat.token}")
    public String TOKEN;

    @Value("${notification.order.topic}")
    public String ORDER;

    @Value("${notification.orderStatus.topic}")
    public String ORDERSTATUS;

    @Value("${notification.newUser.topic}")
    public String NEWUSER;

    @Value("${notification.review.topic}")
    public String REVIEW;

}
