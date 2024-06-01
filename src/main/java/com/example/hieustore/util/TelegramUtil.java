package com.example.hieustore.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramUtil {
    public void SendMessageTelegram(String token, String chatId, String topicId, String message) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("chat_id", chatId)
                .add("text", message)
                .add("message_thread_id", topicId)
                .build();

        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + token + "/sendMessage")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            log.info("SendMessageTelegram status: " + response.code());
            response.close();
        } catch (Exception e) {
            log.error("SendMessageTelegram throw exception: " + e.getMessage());
        }
    }
}
