package com.leon.mikheevtesttask.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class TimeRecordRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18-alpine");

    @Autowired
    private TimeRecordRepository repository;

     @Test
    void shouldSaveBatchAndReturnInChronologicalOrder() {
        LocalDateTime t1 = LocalDateTime.of(2026, 1, 1, 12, 0, 1);
        LocalDateTime t2 = LocalDateTime.of(2026, 1, 1, 12, 0, 2);
        LocalDateTime t3 = LocalDateTime.of(2026, 1, 1, 12, 0, 3);

        List<LocalDateTime> batch = List.of(t1, t2, t3);

        repository.saveAll(batch);

        List<LocalDateTime> records = repository.findAll();

        assertEquals(3, records.size());

        assertEquals(t1, records.get(0));
        assertEquals(t2, records.get(1));
        assertEquals(t3, records.get(2));
    }
}