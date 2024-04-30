package edu.tcu.cs.monnigtest;

import edu.tcu.cs.monnigtest.loans.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context .annotation. Bean;

@SpringBootApplication
public class MonnigTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonnigTestApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

}