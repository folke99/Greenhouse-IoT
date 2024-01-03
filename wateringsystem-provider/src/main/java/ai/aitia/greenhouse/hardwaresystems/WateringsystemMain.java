package ai.aitia.greenhouse.hardwaresystems;

import eu.arrowhead.common.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, WateringsystemConstants.BASE_PACKAGE})
public class WateringsystemMain {
    public static void main(final String[] args) {
        SpringApplication.run(WateringsystemMain.class, args);
    }

}
