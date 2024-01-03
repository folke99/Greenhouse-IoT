package ai.aitia.greenhouse.lightsensor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LightSensorController {

    @GetMapping
    public int getLightLevels() {
        return (int)(Math.random() * 100 + 1);
    }
}
