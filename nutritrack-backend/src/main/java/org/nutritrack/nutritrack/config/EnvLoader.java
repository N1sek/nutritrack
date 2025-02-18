package org.nutritrack.nutritrack.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvLoader {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    static {
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    public static String getEnv(String key){
        return System.getProperty(key);
    }
}
