package com.kgr.challenge.transactionstatistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

import static java.time.Clock.systemDefaultZone;

@Configuration
@EnableScheduling
public class AppConfiguration {

    @Bean
    public Clock getClock() {
        return systemDefaultZone();
    }

}
