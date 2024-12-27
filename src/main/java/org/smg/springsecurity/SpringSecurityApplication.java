package org.smg.springsecurity;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        // koristio sam matarijal sa https://onlinetutorialspoint.com/spring-boot/how-to-set-spring-boot-settimezone.html
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+04:00"));
    }


}
