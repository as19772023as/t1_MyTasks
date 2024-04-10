package ru.strebkov.t1_MyTasks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.strebkov.t1_MyTasks.entity.MyTasksDto;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;
import ru.strebkov.t1_MyTasks.exception.ErrorInputData;
import ru.strebkov.t1_MyTasks.repositiy.MyTaskRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequestMapping("/tasks")
@RequiredArgsConstructor
//@CacheConfig(cacheNames={"tasks"})
public class MyTaskServiceImpl implements MyTaskService {

    private final MyTaskRepository myTaskRepository;
    private final MappingMyTaskService mapping;

    @Cacheable(value = "tasks")  //, key ="#p0") // "#tatle")
    @Transactional
    @Override
    public List<MyTasksDto> getAllTasks() {
        return myTaskRepository.findAll().stream()
                .map(mapping::mapToMyTaskDto)
                .collect(Collectors.toList());
    }

    @CachePut(value = "tasks")
    @Transactional
    @Override
    public Optional<MyTasksDto> getTaskById(Long id) {
        return Optional.ofNullable(mapping.mapToMyTaskDto(myTaskRepository.findById(id)
                .orElse(new MyTasksEntity())));
    }

    @Transactional
    @Override
    public MyTasksEntity saveMyTask(MyTasksDto myTasksDto) {
        final String title = myTasksDto.getTitle();
        final String description = myTasksDto.getDescription();
        taskCheck(title, description);
        final String dueDate = myTasksDto.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateCheck(dueDate);

        MyTasksEntity myTasksEntity = mapping.mapToProductEntity(myTasksDto);
        return myTaskRepository.save(myTasksEntity);
    }

    @CachePut(value = "tasks")
    @Transactional
    @Override
    public MyTasksEntity updateMyTask(MyTasksDto myTasksEntity, Long id) {
        MyTasksEntity tasksDb = myTaskRepository.findById(id).get();
        if (Objects.nonNull(myTasksEntity.getTitle()) && !"".equalsIgnoreCase(myTasksEntity.getTitle())) {
            tasksDb.setTitle(myTasksEntity.getTitle());
        }
        if (Objects.nonNull(myTasksEntity.getDescription()) && !"".equalsIgnoreCase(myTasksEntity.getDescription())) {
            tasksDb.setDescription(myTasksEntity.getDescription());
        }
        if (Objects.nonNull(myTasksEntity.getDueDate()) && !"".equals(myTasksEntity.getDueDate())) {
            tasksDb.setDueDate(myTasksEntity.getDueDate());
        }
        if (Objects.nonNull(myTasksEntity.isCompleted()) && !"".equals(myTasksEntity.isCompleted())) {
            tasksDb.setCompleted(myTasksEntity.isCompleted());
        }
        return myTaskRepository.save(tasksDb);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Transactional
    @Override
    public void deleteMyTaskById(Long id) {
        myTaskRepository.deleteById(id);
    }



    private void taskCheck(String title, String description) {
        if (title == null) {
            throw new ErrorInputData("Название задачи должно быть заполнено");
        } else if (description == null) {
            throw new ErrorInputData("Определение задачи должно быть заполнено");
        }
    }

    private void dateCheck(String dueDate) {
        StringBuilder sb = new StringBuilder(dueDate);
        int month = Integer.parseInt(sb.substring(5, 7));
        if (month > 12) {
            throw new ErrorInputData("Текущий месяц не может быть больше 12");
        }
        int day = Integer.parseInt(sb.substring(8, 10));
        if (day > 31) {
            throw new ErrorInputData("В месяце не может быть больше 31 дня");
        }
        int year = Integer.parseInt(sb.substring(0, 4));
        if (LocalDate.now().getYear() > year) {
            throw new ErrorInputData("В месяце не может быть больше 31 дня");
        }
        if (LocalDate.now().getYear() == year && LocalDate.now().getMonthValue() > month) {
            throw new ErrorInputData("Дата уже прошла");
        }
    }
}


