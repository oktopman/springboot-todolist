package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.service.TodoService;
import me.oktop.springboottodolist.web.dto.TodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todolist")
    public Page<TodoDto> getTodolist() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return todoService.getTodolist(pageRequest);
    }
}
