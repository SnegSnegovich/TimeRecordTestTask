package com.leon.mikheevtesttask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Configuration
public class AppConfig {

    @Bean
    public BlockingDeque<LocalDateTime> timeQueue() {
        //Выставим лимит на очередь в случае недоступности БД, чтобы не было OutOfMemory
        return new LinkedBlockingDeque<>(100_000);
    }
}