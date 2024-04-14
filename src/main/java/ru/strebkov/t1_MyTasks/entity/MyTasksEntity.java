package ru.strebkov.t1_MyTasks.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "t1_tasks", name = "my_task")
public class MyTasksEntity {

    @Id
    @Column(name = "id_my_task")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTask;
    @Column(length = 200, nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Column(nullable = false)
    private boolean completed;


}
