package com.example.hieustore.proactive.alert;

import com.example.hieustore.config.NotificationConfig;
import com.example.hieustore.util.TelegramUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsyncService {
    @Value("${proactive.alert.enable}")
    private Boolean enableSendMsg;
    private final TelegramUtil telegramUtil;
    private final NotificationConfig notificationConfig;

    private boolean isEnableSendMessageToTelegram() {
        return enableSendMsg != null && enableSendMsg;
    }

    /**
     * Send Message to telegram
     *
     * @param id      notification id
     * @param message noi dung tin nhan telegram
     */
    @Async("taskExecutor")
    public void sendTelegramMessage(String topicId, String message) {
        log.info("send telegram message asynchronously in thread: " + Thread.currentThread().getName());
        try {
            if (!isEnableSendMessageToTelegram()) {
                log.info("send telegram message asynchronously DISABLE. Check environment 'proactive.alert.enable'");
                return;
            }
            telegramUtil.SendMessageTelegram(notificationConfig.TOKEN, notificationConfig.CHAT_ID, topicId, message);
        } catch (Exception e) {
            log.warn("sendTelegramMessage throw exception: " + e.getMessage());
        }
    }

}
