package ru.strebkov.t1_MyTasks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MyTasksDto {
    private long idTask;

    private String title;

    private String description;

    private LocalDate dueDate;

    private boolean completed;
}
