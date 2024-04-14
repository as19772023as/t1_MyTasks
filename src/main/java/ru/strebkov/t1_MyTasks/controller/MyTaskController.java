package ru.strebkov.t1_MyTasks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.strebkov.t1_MyTasks.dto.MyTasksDto;
import ru.strebkov.t1_MyTasks.entity.MyTasksEntity;
import ru.strebkov.t1_MyTasks.service.MyTaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks") // localhost:8080/tasks
@RequiredArgsConstructor
public class MyTaskController {

    private final MyTaskService myTaskService;

    @GetMapping()
    public ResponseEntity<List<MyTasksDto>> getAllTasks() {
        List<MyTasksDto> listTasks = new ArrayList<MyTasksDto>(myTaskService.getAllTasks());
        if (listTasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listTasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<MyTasksDto>> getTaskById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(myTaskService.getTaskById(id), HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity<MyTasksEntity> saveMyTask(@Valid @RequestBody MyTasksDto myTasksDto) {
        MyTasksEntity myTasksEntity = myTaskService.saveMyTask(myTasksDto);
        return new ResponseEntity<>(myTasksEntity, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MyTasksEntity> updateMyTask(@Valid @RequestBody MyTasksDto myTasksDto, @PathVariable("id") Long id) {
        return new ResponseEntity<>(myTaskService.updateMyTask(myTasksDto, id), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMyTaskById(@PathVariable("id") Long id) {
        myTaskService.deleteMyTaskById(id);
        return new ResponseEntity<String>("Задача удалена успешно!.", HttpStatus.OK);
    }

}
