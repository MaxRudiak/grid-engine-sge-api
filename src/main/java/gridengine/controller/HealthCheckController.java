package gridengine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/check")
    public String checkStatus() {
        return "{\n" +
                "   \"status\": \"ok\"\n" +
                "}";
    }
}
