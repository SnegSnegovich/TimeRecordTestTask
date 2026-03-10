package com.leon.mikheevtesttask.service;

import com.leon.mikheevtesttask.repository.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeQueryService {

    private final TimeRecordRepository repository;

    @Transactional(readOnly = true)
    public List<LocalDateTime> getAllTimes() {
        log.info("Запрос на получение списка временных отрезков из БД");
        return repository.findAll();
    }
}