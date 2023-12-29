package ai.aitia.greenhouse.soilsensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import eu.arrowhead.common.CommonConstants;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, SoilSensorConstants.BASE_PACKAGE})
public class SoilSensorMain {
    public static void main(final String[] args) {
        SpringApplication.run(SoilSensorMain.class, args);
    }
}