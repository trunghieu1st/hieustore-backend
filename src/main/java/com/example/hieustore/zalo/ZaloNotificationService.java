package com.example.hieustore.zalo;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class ZaloNotificationService {

    private static final String ZALO_API_URL = "https://openapi.zalo.me/v2.0/oa/message";
    private static final String ACCESS_TOKEN = "gJPG2OaBJ1sZC54DX01E6wCyK0s26nPQzrPOB98N7KFuJGS2kmTC7UnVKLEzEI5Crq1aEwmX92-CGcjXzMOR3w8v2dcTKL0s-6uwPviJUYN3UmjIXY9JHRbf5ZATLbHVsJPFGQHA2WFx8X9YW65YCwG5A77rVMy1fambIeSpP3hCLnubjG53U-evBH-lT79EoJSf3ArSS7pf7ZjsgLfrFzS24bgsPLaNqrGqIeeyV2B1PWHAupPQFxzK1LV4P5b0YHWHDEz9LLgL93SLncTG7u4cF4h7H6zKXtuS7VqdHdEtJpyjzorTVxy9HHQOM45YimC_ByHi9a-OD7WZ_aWoQfugJnx5TY5Ie1H41zD-EbIIGL4RtsKUTKyI2IadZn5B5m";

    public void sendZaloNotification(String recipientId, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + ACCESS_TOKEN);

        String requestBody = "{\"recipient\":{\"user_id\":\"" + recipientId + "\"},\"message\":{\"text\":\"" + message + "\"}}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(ZALO_API_URL, requestEntity, String.class);

        // Xử lý phản hồi từ Zalo API nếu cần
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Thông báo đã được gửi thành công đến Zalo ID: " + recipientId);
        } else {
            System.out.println("Có lỗi xảy ra khi gửi thông báo đến Zalo ID: " + recipientId);
            System.out.println("Phản hồi từ Zalo API: " + response.getBody());
        }
    }
}
