package com.smartship.backend.app;

import com.smartship.backend.app.config.InitialData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.transaction.Transactional;
import java.util.Arrays;

@ComponentScan({"com.smartship.backend.app"}) // scan for testing
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final InitialData initialData;
    private final Environment environment;

    @Autowired
    public BackendApplication(InitialData initialData, Environment environment) {
        this.initialData = initialData;
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    /**
     * Add initial data
     *
     * @param args incoming main method arguments
     */
    @Override
    @Transactional
    public void run(String... args) {
        if (Arrays.stream(environment.getActiveProfiles()).noneMatch(profile -> profile.equals("test")))
            initialData.addData();
    }
}
