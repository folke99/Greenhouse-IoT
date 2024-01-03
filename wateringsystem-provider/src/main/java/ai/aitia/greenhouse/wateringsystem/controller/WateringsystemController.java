package ai.aitia.greenhouse.wateringsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WateringsystemController {
    @PostMapping("/on")
    public String setWateringOn() {
        return "Watering On";
    }
    @PostMapping("/off")
    public String setWateringOff() {
        return "Watering Off";
    }
}
