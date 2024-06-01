package com.example.hieustore.zalo;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/orders")
public class OrderPayController {

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        // Xử lý logic đặt hàng ở đây
        // Gọi hàm gửi thông báo Zalo
        sendZaloNotification(order);
        return "Order placed successfully!";
    }

    private void sendZaloNotification(Order order) {
        String zaloApiUrl = "https://openapi.zalo.me/v2.0/oa/message";
        String accessToken = "gJPG2OaBJ1sZC54DX01E6wCyK0s26nPQzrPOB98N7KFuJGS2kmTC7UnVKLEzEI5Crq1aEwmX92-CGcjXzMOR3w8v2dcTKL0s-6uwPviJUYN3UmjIXY9JHRbf5ZATLbHVsJPFGQHA2WFx8X9YW65YCwG5A77rVMy1fambIeSpP3hCLnubjG53U-evBH-lT79EoJSf3ArSS7pf7ZjsgLfrFzS24bgsPLaNqrGqIeeyV2B1PWHAupPQFxzK1LV4P5b0YHWHDEz9LLgL93SLncTG7u4cF4h7H6zKXtuS7VqdHdEtJpyjzorTVxy9HHQOM45YimC_ByHi9a-OD7WZ_aWoQfugJnx5TY5Ie1H41zD-EbIIGL4RtsKUTKyI2IadZn5B5m"; // Thay thế bằng Access Token của bạn

        HttpHeaders headers = new HttpHeaders();
        headers.set("access_token", accessToken);

        String message = "Order received: " + order.getProductName() + " x " + order.getQuantity();
        String payload = "{\"recipient\":{\"user_id\":\"USER_ID\"},\"message\":{\"text\":\"" + message + "\"}}";

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(zaloApiUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Notification sent successfully!");
            System.out.println("Response: " + response.getBody());
        } else {
            System.out.println("Failed to send notification");
            System.out.println("Response: " + response.getBody());
        }
    }
}
