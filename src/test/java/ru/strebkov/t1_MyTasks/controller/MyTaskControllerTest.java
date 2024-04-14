package ru.strebkov.t1_MyTasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.strebkov.t1_MyTasks.dto.MyTasksDto;
import ru.strebkov.t1_MyTasks.service.MyTaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MyTaskController.class)
@DisplayName("Тест компонента Controller")
class MyTaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MyTaskService myTaskService;
    private static long suiteStartTime;
    private long testStartTime;
    private static final String END_POINT_PATH = "/tasks";
    private static final String END_POINT_PATH_ID = "/tasks/1";
    private static final String description = "Билеты  на концерт";
    private static final String title = "Продать";
    private static final long id = 1L;
    private static final LocalDate date = LocalDate.parse("2024-04-10");
    private static final boolean completed = false;
    private static MyTasksDto myTasks;
    private static List<MyTasksDto> allTasks;


    @BeforeAll
    public static void initSuite() {
        System.out.println("Running Tests");
        suiteStartTime = System.nanoTime();
        myTasks = new MyTasksDto(id, title, description, date, completed);
        allTasks = List.of(myTasks);
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("Tests complete: " + (System.nanoTime() - suiteStartTime) / 1000000 + " ms");
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new Test");
        testStartTime = System.nanoTime();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("New test complete:" + (System.nanoTime() - testStartTime) / 1000000 + " ms");
    }

    @Test
    @DisplayName("Тест на статус 200 при получение всех заплонировнных задачи")
    void getAllTasksTest() throws Exception {
        Mockito.when(myTaskService.getAllTasks()).thenReturn(allTasks);
        String requestBody = objectMapper.writeValueAsString(myTasks);
        mockMvc.perform(get(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Тест на получение задачи по id")
    void getTaskByIdTest() {
        Mockito.when(myTaskService.getTaskById(myTasks.getIdTask())).thenReturn(Optional.ofNullable(myTasks));
        MyTasksDto found = myTaskService.getTaskById(id).get();
        assertThat(found.getTitle())
                .isEqualTo(title);
        assertThat(found.getDescription())
                .isEqualTo(description);
        assertThat(found)
                .isEqualTo(myTasks);
    }

    @Test
    @DisplayName("Тест на получение задачи по id статус 200")
    void getTaskByIdTestStatus() throws Exception {
        Mockito.when(myTaskService.getTaskById(id)).thenReturn(Optional.ofNullable(myTasks));
        String requestBody = objectMapper.writeValueAsString(myTasks);
        mockMvc.perform(get(END_POINT_PATH_ID).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Тест на удаление задачи")
    void deleteMyTaskByIdTest() throws Exception {
        String requestBody = objectMapper.writeValueAsString(myTasks);
        mockMvc.perform(delete(END_POINT_PATH_ID).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
    }
}