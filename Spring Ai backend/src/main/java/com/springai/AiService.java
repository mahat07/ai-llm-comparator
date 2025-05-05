package com.springai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate;

    @Value("${anthropic.api.key}")
    private String anthropicApiKey;

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getModelResponse(String model, String prompt) {
        String endpoint;
        String modelName;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        switch (model.toLowerCase()) {
            case "deepseek":
                endpoint = "http://localhost:1234/v1/chat/completions";
                modelName = "deepseek-r1-distill-qwen-7b";
                break;
            case "llama":
                endpoint = "http://localhost:1234/v1/chat/completions";
                modelName = "llama-3.2-1b-instruct-q8_0";
                break;
            case "anthropic":
                endpoint = "https://api.anthropic.com/v1/messages";
                modelName = "claude-3-opus-20240229";
                headers.set("x-api-key", anthropicApiKey);
                headers.set("anthropic-version", "2023-06-01");
                break;
            default:
                throw new IllegalArgumentException("Unsupported model: " + model);
        }

        Map<String, Object> body = model.equals("anthropic")
                ? Map.of(
                    "model", modelName,
                    "max_tokens", 1000,
                    "messages", List.of(Map.of("role", "user", "content", prompt))
                )
                : Map.of(
                    "model", modelName,
                    "messages", List.of(Map.of("role", "user", "content", prompt)),
                    "max_tokens", 512
                );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, entity, String.class);
        return response.getBody();
    }
}
