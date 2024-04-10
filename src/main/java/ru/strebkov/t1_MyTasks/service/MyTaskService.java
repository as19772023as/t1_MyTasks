package ru.strebkov.t1_MyTasks.service;

import ru.strebkov.t1_MyTasks.entity.MyTasksDto;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;

import java.util.List;
import java.util.Optional;

public interface MyTaskService {
    List<MyTasksDto> getAllTasks();

    Optional<MyTasksDto> getTaskById(Long id);

    MyTasksEntity saveMyTask(MyTasksDto myTasksDto);

    MyTasksEntity updateMyTask(MyTasksDto myTasksDto, Long id);

    void deleteMyTaskById(Long id);

}
