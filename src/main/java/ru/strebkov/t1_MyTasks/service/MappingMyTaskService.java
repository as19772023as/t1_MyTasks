package ru.strebkov.t1_MyTasks.service;

import org.springframework.stereotype.Service;
import ru.strebkov.t1_MyTasks.entity.MyTasksDto;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;

@Service
public class MappingMyTaskService {
    //из entity в dto
    public MyTasksDto mapToMyTaskDto(MyTasksEntity entity) {
        MyTasksDto dto = new MyTasksDto();

        dto.setIdTask(entity.getIdTask());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCompleted(entity.isCompleted());
        dto.setDueDate(entity.getDueDate());

        return dto;
    }

    //из dto в entity
    public MyTasksEntity mapToProductEntity(MyTasksDto dto) {
        MyTasksEntity entity = new MyTasksEntity();

        entity.setIdTask(dto.getIdTask());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCompleted(dto.isCompleted());
        entity.setDueDate(dto.getDueDate());

        return entity;
    }
}
