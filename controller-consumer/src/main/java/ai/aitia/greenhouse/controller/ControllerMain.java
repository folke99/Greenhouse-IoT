package ai.aitia.greenhouse.controller;

import eu.arrowhead.common.CommonConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import static ai.aitia.greenhouse.controller.ControllerConstants.BASE_PACKAGE;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, BASE_PACKAGE})
@EnableScheduling
public class ControllerMain {

    public static void main(final String[] args) {
        SpringApplication.run(ControllerMain.class, args);
    }
}