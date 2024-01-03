package ai.aitia.greenhouse.lightsensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import eu.arrowhead.common.CommonConstants;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, LightSensorConstants.BASE_PACKAGE})
public class LightSensorMain {
    public static void main(final String[] args) {
        SpringApplication.run(LightSensorMain.class, args);
    }
}