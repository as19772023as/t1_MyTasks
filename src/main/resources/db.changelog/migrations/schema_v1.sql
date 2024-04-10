-- liquibase formatted sql

-- changeset Andrey:1

create table if not exists t1_tasks.my_task
(
id_my_task bigserial primary key not null,
title varchar(200) not null,
description varchar not null,
due_date date not null,
completed boolean not null
);