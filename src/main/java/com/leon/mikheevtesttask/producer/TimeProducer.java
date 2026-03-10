package com.leon.mikheevtesttask.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeProducer {

    private final BlockingDeque<LocalDateTime> timeQueue;

    @Scheduled(fixedRate = 1000)
    public void generateTime() {
        LocalDateTime now = LocalDateTime.now();

        if (!timeQueue.offerLast(now)) {
            log.error("Очередь переполнена! Данные отброшены: {}", now);
        } else {
            log.debug("Сгенерировано время: {}", now);
        }
    }
}