--liquibase formatted sql

--changeset TimurKhamzin:1
--comment first migration

CREATE TABLE IF NOT EXISTS t_inventory
(
    id       BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    sku_code varchar(255) NOT NULL UNIQUE,
    quantity INTEGER      NOT NULL
);