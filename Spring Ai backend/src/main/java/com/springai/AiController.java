package com.springai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/{model}/{message}")
    public ResponseEntity<String> getResponse(
            @PathVariable String model,
            @PathVariable String message) {
        String response = aiService.getModelResponse(model, message);
        return ResponseEntity.ok(response);
    }
}
