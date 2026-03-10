--liquibase formatted sql

--changeset mikheevaa@leon.ru:2026-03-04-create-time-records-table

CREATE SCHEMA IF NOT EXISTS main;

CREATE TABLE IF NOT EXISTS main.time_records (
    record_time TIMESTAMP NOT NULL
);