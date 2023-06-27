package com.poethan.hearthstoneclassic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
        "com.poethan.jear.*",
        "com.poethan.hearthstoneclassic.*"
})
public class HearthstoneServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HearthstoneServerApplication.class, args);
    }

}
