package com.leon.mikheevtesttask.controller;

import com.leon.mikheevtesttask.service.TimeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/times")
public class TimeController {

    private final TimeQueryService timeQueryService;

    @GetMapping
    public ResponseEntity<List<LocalDateTime>> getTimes() {
        return ResponseEntity.ok(timeQueryService.getAllTimes());
    }
}