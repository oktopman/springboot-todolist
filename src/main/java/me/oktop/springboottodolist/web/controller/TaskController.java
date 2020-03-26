package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.service.TaskService;
import me.oktop.springboottodolist.web.dto.TodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/todolist")
    public Page<TodoDto> getTodolist() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return taskService.getTodolist(pageRequest);
    }
}
