package ru.strebkov.t1_MyTasks.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @FutureOrPresent(message = "The date must be in the present or future")
    private LocalDate dueDate;

    private boolean completed;
}
