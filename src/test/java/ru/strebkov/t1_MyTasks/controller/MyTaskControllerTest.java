package ru.strebkov.t1_MyTasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.strebkov.t1_MyTasks.entity.MyTasksDto;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;
import ru.strebkov.t1_MyTasks.service.MappingMyTaskService;
import ru.strebkov.t1_MyTasks.service.MyTaskService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(MyTaskController.class)
@DisplayName("Тест компонента Controller")
//@DataJpaTest
class MyTaskControllerTest {
    //    @Autowired
//    private TestEntityManager entityManager;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MyTaskService myTaskService;
    @MockBean
    private MappingMyTaskService mapping;
    private static long suiteStartTime;
    private long testStartTime;

    private static final String END_POINT_PATH = "/tasks";
    private static final String description = "Билеты  на концерт";
    private static final String title = "Продать";
    private static final long id = 1L;
    private static final LocalDate date = LocalDate.parse("2024-04-10");
    private static final boolean completed = false;
    private static MyTasksDto myTasks;
    private static  List<MyTasksDto> allTasks;

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running Tests");
        suiteStartTime = System.nanoTime();
        myTasks = new MyTasksDto(id, title, description, date, completed);
        allTasks = Arrays.asList(myTasks);
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
    @DisplayName("Тест на статуса 200 при получение всех заплонировнных задачи")
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
    @DisplayName("Тест на статус 201 при создании новой задачи")
    void saveMyTask() throws Exception {
        MyTasksEntity myTasksEntity = mapping.mapToProductEntity(myTasks);
        Mockito.when(myTaskService.saveMyTask(myTasks)).thenReturn(myTasksEntity);
        String requestBody = objectMapper.writeValueAsString(myTasks);
        mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());

    }
//    @Test
//    @DisplayName("Тест на обновление данных")
//    void updateMyTaskTest() throws  Exception{
//        String expectedTitle = "Купить";
//        MyTasksEntity myTasksEntity = new MyTasksEntity(id,expectedTitle, description, date, completed);
//
//        myTaskService.updateMyTask(myTasks, id)
//
//
//        Optional<MyTasksDto> changeTask = myTaskService.getTaskById(id);
//
//        Mockito.when(myTaskService.updateMyTask(myTasks, id)).thenReturn(mapping.mapToProductEntity(changeTask.get()));
//        String requestBody = objectMapper.writeValueAsString(myTasks);
//        mockMvc.perform(put(END_POINT_PATH).contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//
//    }
}