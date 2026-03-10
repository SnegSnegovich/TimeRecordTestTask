package com.leon.mikheevtesttask.consumer;

import com.leon.mikheevtesttask.repository.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeConsumer {

    private final BlockingDeque<LocalDateTime> timeQueue;
    private final TimeRecordRepository repository;

    @Scheduled(fixedDelay = 500)
    public void consumeTime() {
        if (timeQueue.isEmpty()) {
            return;
        }

        List<LocalDateTime> batch = new ArrayList<>();
        timeQueue.drainTo(batch, 1000);

        if (batch.isEmpty()) {
            return;
        }

        try {
            repository.saveAll(batch);
            log.debug("Успешно сохранено {} записей", batch.size());
        } catch (DataAccessException ex) {
            log.error("Ошибка БД: {}. Возврат {} записей в очередь...", ex.getMessage(), batch.size());
            // Возврат в начало очереди, если БД упала
            for (int i = batch.size() - 1; i >= 0; i--) {
                timeQueue.offerFirst(batch.get(i));
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ie) {
                log.error("Поток был прерван по причине: {}", ie.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}