package com.example.hieustore.controller;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@RequiredArgsConstructor
@RestController
public class ZaloPayController {
    private final String APP_ID = "2553";
    private final String KEY1 = "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL";
    private final String KEY2 = "kLtgPl8HHhfvMuDHPwKfgfsY4Ydm9eIz";
    private final String ENDPOINT = "https://sb-openapi.zalopay.vn/v2/create";



    // Sửa đổi phương thức này
    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> createPayment(@RequestBody Map<String, Object> request) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> embedData = new HashMap<>();
        embedData.put("redirecturl", "https://phongthuytaman.com");

        Random rand = new Random();
        int transID = rand.nextInt(1000000);

        Map<String, Object> order = new HashMap<>();
        order.put("app_id", APP_ID);
        order.put("app_trans_id", new SimpleDateFormat("yyMMdd").format(new Date()) + "_" + transID);
        order.put("app_user", "user123");
        order.put("app_time", new Date().getTime());
        order.put("item", objectMapper.writeValueAsString(new ArrayList<>()));
        order.put("embed_data", objectMapper.writeValueAsString(embedData));
        order.put("amount", 200);
        order.put("callback_url", "https://b074-1-53-37-194.ngrok-free.app/callback");
        order.put("description", "Lazada - Payment for the order #" + transID);
        order.put("bank_code", "");

        String data = APP_ID + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|" +
                order.get("amount") + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|" + order.get("item");

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(KEY1.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        String mac = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes()));

        order.put("mac", mac);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : order.entrySet()) {
            params.add(entry.getKey(), entry.getValue().toString());
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, params, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> result = response.getBody();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "Failed to make payment"), HttpStatus.BAD_REQUEST);
        }
    }


}