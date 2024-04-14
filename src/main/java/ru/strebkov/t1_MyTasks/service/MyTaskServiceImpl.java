package ru.strebkov.t1_MyTasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strebkov.t1_MyTasks.dto.MyTasksDto;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;
import ru.strebkov.t1_MyTasks.exception.NoSuchTasksEndpointException;
import ru.strebkov.t1_MyTasks.repositiy.MyTaskRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyTaskServiceImpl implements MyTaskService {

    private final MyTaskRepository myTaskRepository;
    private final MappingMyTaskService mapping;

    @Override
    @Cacheable(value = "tasks")
    @Transactional
    public List<MyTasksDto> getAllTasks() {
        return myTaskRepository.findAll().stream()
                .map(mapping::mapToMyTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "tasks")
    @Transactional
    public Optional<MyTasksDto> getTaskById(Long id) {
        return Optional.ofNullable(mapping.mapToMyTaskDto(myTaskRepository.findById(id)
                .orElseThrow(() -> new NoSuchTasksEndpointException("Такой задачи нет"))));
    }

    @Override
    @Transactional
    public MyTasksEntity saveMyTask(MyTasksDto myTasksDto) {
        MyTasksEntity myTasksEntity = mapping.mapToProductEntity(myTasksDto);
        return myTaskRepository.save(myTasksEntity);

    }

    @Override
    @CachePut(value = "tasks")
    @Transactional
    public MyTasksEntity updateMyTask(MyTasksDto myTasksEntity, Long id) {
        if (myTaskRepository.findById(id).isPresent()) {
            MyTasksEntity tasksDb = myTaskRepository.findById(id).get();
            if (Objects.nonNull(myTasksEntity.getTitle()) && !"".equalsIgnoreCase(myTasksEntity.getTitle())) {
                tasksDb.setTitle(myTasksEntity.getTitle());
            }
            if (Objects.nonNull(myTasksEntity.getDescription()) && !"".equalsIgnoreCase(myTasksEntity.getDescription())) {
                tasksDb.setDescription(myTasksEntity.getDescription());
            }
            if (Objects.nonNull(myTasksEntity.getDueDate())) {
                tasksDb.setDueDate(myTasksEntity.getDueDate());
            }
            tasksDb.setCompleted(myTasksEntity.isCompleted());
            return myTaskRepository.save(tasksDb);
        } else throw new NoSuchTasksEndpointException("Невозможно обновить задачу: такой задачи нет");
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    @Transactional
    public void deleteMyTaskById(Long id) {
        myTaskRepository.deleteById(id);
    }

}


