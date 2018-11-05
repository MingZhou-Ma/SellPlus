package tech.greatinfo.sellplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SellPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellPlusApplication.class, args);
    }
}
