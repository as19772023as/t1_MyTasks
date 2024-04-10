package ru.strebkov.t1_MyTasks.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.strebkov.t1_MyTasks.entity.MyTasksDto;
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
        try {
            List<MyTasksDto> listTasks = new ArrayList<MyTasksDto>(myTaskService.getAllTasks());
            if (listTasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listTasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyTasksDto> getTaskById(@PathVariable("id") Long id) {
        Optional<MyTasksDto> taskById = myTaskService.getTaskById(id);
        return taskById.map(myTasksDto -> new ResponseEntity<>(myTasksDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<MyTasksEntity> saveMyTask(@RequestBody @Valid MyTasksDto myTasksDto) {
        try {
            MyTasksEntity myTasksEntity = myTaskService.saveMyTask(myTasksDto);
            return new ResponseEntity<>(myTasksEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyTasksEntity> updateMyTask(@RequestBody @Valid MyTasksDto myTasksDto, @PathVariable("id") Long id) {
        Optional<MyTasksDto> changeTask = myTaskService.getTaskById(id);
        if (changeTask.isPresent()) {
            return new ResponseEntity<>(myTaskService.updateMyTask(myTasksDto, id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMyTaskById(@PathVariable("id") Long id) {
        try {
            myTaskService.deleteMyTaskById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
