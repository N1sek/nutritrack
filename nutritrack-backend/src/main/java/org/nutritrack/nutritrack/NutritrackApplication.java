package org.nutritrack.nutritrack;

import org.nutritrack.nutritrack.config.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NutritrackApplication {

    static {
        new EnvLoader();
    }

    public static void main(String[] args) {
        SpringApplication.run(NutritrackApplication.class, args);
    }

}
