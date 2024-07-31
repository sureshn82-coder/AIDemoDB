package com.example.aidemodb.service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.IOException;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String callOpenAI(String content) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://api.openai.com/v1/engines/davinci/completions");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Bearer " + openaiApiKey);

            JSONObject json = new JSONObject();
            json.put("prompt", content);
            json.put("max_tokens", 500);

            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);

            String response = EntityUtils.toString(client.execute(request).getEntity());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while processing with OpenAI";
        }
    }
}

