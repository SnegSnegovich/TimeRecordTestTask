package com.leon.mikheevtesttask.consumer;

import com.leon.mikheevtesttask.repository.TimeRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeConsumerTest {

    @Mock
    private TimeRecordRepository repository;

    @Test
    void consumeTime_WhenDatabaseFails_ShouldReturnDataToQueueInCorrectOrder() {
        BlockingDeque<LocalDateTime> timeQueue = new LinkedBlockingDeque<>();
        LocalDateTime time1 = LocalDateTime.of(2026, 3, 10, 10, 0, 1);
        LocalDateTime time2 = LocalDateTime.of(2026, 3, 10, 10, 0, 2);
        LocalDateTime time3 = LocalDateTime.of(2026, 3, 10, 10, 0, 3);

        timeQueue.offerLast(time1);
        timeQueue.offerLast(time2);
        timeQueue.offerLast(time3);

        TimeConsumer consumer = new TimeConsumer(timeQueue, repository);

        doThrow(new DataAccessResourceFailureException("БД упала"))
                .when(repository).saveAll(anyList());

        Thread.currentThread().interrupt();

        consumer.consumeTime();

        assertEquals(3, timeQueue.size(), "Все 3 записи должны вернуться в очередь");
        assertEquals(time1, timeQueue.pollFirst(), "Первым должен остаться time1");
        assertEquals(time2, timeQueue.pollFirst(), "Вторым должен остаться time2");
        assertEquals(time3, timeQueue.pollFirst(), "Третьим должен остаться time3");

        verify(repository, times(1)).saveAll(anyList());
    }
}