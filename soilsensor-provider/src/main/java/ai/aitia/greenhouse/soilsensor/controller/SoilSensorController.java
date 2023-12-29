package ai.aitia.greenhouse.soilsensor.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class SoilSensorController {

    @GetMapping
    public int getMoisturePercent() {
        return 10;
    }
}
