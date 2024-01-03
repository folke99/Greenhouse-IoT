package ai.aitia.greenhouse.hardwaresystems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HardwaresystemController {
    @PostMapping("/on")
    public String setWateringOn() {
        return "Watering On";
    }
    @PostMapping("/off")
    public String setWateringOff() {
        return "Watering Off";
    }
    @PostMapping("/adjustLight")
    public String setLight() {
        return "Adjusting Light";
    }
}
