package ru.strebkov.t1_MyTasks.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;
import ru.strebkov.t1_MyTasks.repositiy.MyTaskRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@DisplayName("Тест компонента Service")
class MyTaskServiceImplTest {

    @Autowired
    private MyTaskRepository repository;

    private static long suiteStartTime;
    private long testStartTime;

    private static final String description = "Билеты  на концерт";
    private static final String title = "Продать";

    private static final LocalDate date = LocalDate.parse("2024-04-10");
    private static final boolean completed = false;
    private MyTasksEntity testTask;


    @BeforeAll
    public static void initSuite() {
        System.out.println("Running Test");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("Test complete: " + (System.nanoTime() - suiteStartTime) / 1000000 + " ms");
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new Test");
        testStartTime = System.nanoTime();
        testTask = new MyTasksEntity();
        testTask.setTitle(title);
        testTask.setDueDate(date);
        testTask.setDescription(description);
        testTask.setCompleted(completed);

        repository.save(testTask);
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete:" + (System.nanoTime() - testStartTime) / 1000000 + " ms");

        repository.deleteById(testTask.getIdTask());
    }

    @DisplayName("Тест на проверку получения задачи по id")
    @Test
    void getTaskByIdTest() {
        MyTasksEntity found = repository.findById(testTask.getIdTask()).get();
        assertNotNull(found);
        assertEquals(testTask.getTitle(), found.getTitle());

    }

    @DisplayName("Тест на проверку обновления задачи по id")
    @Test
    void updateMyTaskTest() {
        testTask.setTitle("updatedTitle");
        testTask.setDescription("updatedDescription");
        repository.save(testTask);

        MyTasksEntity savedUser = repository.findById(testTask.getIdTask()).orElse(null);
        assertNotNull(savedUser);
        assertEquals("updatedTitle", savedUser.getTitle());
        assertEquals("updatedDescription", savedUser.getDescription());
    }

}