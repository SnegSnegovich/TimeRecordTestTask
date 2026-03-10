package com.leon.mikheevtesttask.producer;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TimeProducerTest {

    @Test
    void generateTime_ShouldPutCurrentTimeIntoQueue() {
        BlockingDeque<LocalDateTime> queue = new LinkedBlockingDeque<>(10);
        TimeProducer producer = new TimeProducer(queue);

        producer.generateTime();

        assertEquals(1, queue.size());
        assertNotNull(queue.peekLast());
    }
}