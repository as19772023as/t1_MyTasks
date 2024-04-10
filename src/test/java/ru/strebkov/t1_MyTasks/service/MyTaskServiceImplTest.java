package ru.strebkov.t1_MyTasks.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.strebkov.t1_MyTasks.repositiy.MyTaskRepository;

import static org.junit.jupiter.api.Assertions.*;
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = PersistenceTestConfig.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:init.sql"})
class MyTaskServiceImplTest {
    @Autowired
    MyTaskRepository myTaskRepository;
    @Test
    void getAllTasks() {


    }

    @Test
    void getTaskById() {
    }

    @Test
    void saveMyTask() {
    }

    @Test
    void updateMyTask() {
    }

    @Test
    void deleteMyTaskById() {
    }
}