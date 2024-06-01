package com.example.hieustore.service.impl;

import com.example.hieustore.domain.dto.request.CheckZaloPayStatusRequestDto;
import com.example.hieustore.domain.dto.request.ZaloPayRequestDto;
import com.example.hieustore.service.ZaloPayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ZaloPayServiceImpl implements ZaloPayService {

    @Value("${zalopay.appId}")
    private String appId;

    @Value("${zalopay.key1}")
    private String key1;

    @Value("${zalopay.key2}")
    private String key2;

    @Value("${zalopay.endpoint}")
    private String endpoint;

    @Value("${zalopay.callbackUrl}")
    private String callbackUrl;

    @Value("${zalopay.redirectUrl}")
    private String redirectUrl;

    @Value("${zalopay.queryEndpoint}")
    private String queryEndpoint;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createSignature(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public ResponseEntity<?> createPayment(ZaloPayRequestDto requestDto) {
        try {
            int transID = (int) (Math.random() * 1000000);
            String appTransId = String.format("%s_%s", new java.text.SimpleDateFormat("yyMMdd").format(new java.util.Date()), transID);

            Map<String, Object> embedData = new HashMap<>();
            embedData.put("redirecturl", redirectUrl);

            Map<String, Object> order = new HashMap<>();
            order.put("app_id", appId);
            order.put("app_trans_id", appTransId);
            order.put("app_user", requestDto.getAppUser());
            order.put("app_time", System.currentTimeMillis());
            order.put("item", objectMapper.writeValueAsString(new Object[]{}));
            order.put("embed_data", objectMapper.writeValueAsString(embedData));
            order.put("amount", requestDto.getAmount());
            order.put("callback_url", callbackUrl);
            order.put("description", requestDto.getDescription());
            order.put("bank_code", requestDto.getBankCode());

            String data = String.join("|", appId, appTransId, requestDto.getAppUser(), String.valueOf(requestDto.getAmount()), String.valueOf(System.currentTimeMillis()), objectMapper.writeValueAsString(embedData), objectMapper.writeValueAsString(new Object[]{}));
            return getResponseEntity(order, data, endpoint);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Server error", "error", e.getMessage()));
        }
    }

    private ResponseEntity<?> getResponseEntity(Map<String, Object> order, String data, String endpoint) throws Exception {
        order.put("mac", createSignature(data, key1));

        HttpPost post = new HttpPost(endpoint);
        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        for (Map.Entry<String, Object> entry : order.entrySet()) {
            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        post.setEntity(new StringEntity(urlParameters.toString()));
        CloseableHttpClient client = HttpClients.createDefault();
        String response = EntityUtils.toString(client.execute(post).getEntity());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> checkStatusOrder(CheckZaloPayStatusRequestDto requestDto) {
        try {
            Map<String, Object> postData = new HashMap<>();
            postData.put("app_id", appId);
            postData.put("app_trans_id", requestDto.getAppTransId());

            String data = String.join("|", appId, requestDto.getAppTransId(), key1);
            return getResponseEntity(postData, data, queryEndpoint);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Server error", "error", e.getMessage()));
        }
    }
}
