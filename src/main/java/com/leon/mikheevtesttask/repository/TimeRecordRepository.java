package com.leon.mikheevtesttask.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TimeRecordRepository {

    private static final String INSERT_BATCH = """
                        INSERT INTO main.time_records (record_time) VALUES (:recordTime)
            """;

    private static final String SELECT_ALL = """
                        SELECT record_time FROM main.time_records
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void saveAll(List<LocalDateTime> localDateTimes) {
        SqlParameterSource[] batchArgs = localDateTimes.stream()
                .map(time -> new MapSqlParameterSource("recordTime", time))
                .toArray(SqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(INSERT_BATCH, batchArgs);
    }

    public List<LocalDateTime> findAll() {
        return jdbcTemplate.query(SELECT_ALL, (rs, rowNum) -> {
            Timestamp timestamp = rs.getTimestamp("record_time");
            return timestamp.toLocalDateTime();
        });
    }
}