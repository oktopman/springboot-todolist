package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.service.TodoService;
import me.oktop.springboottodolist.web.dto.ResponseDto;
import me.oktop.springboottodolist.web.dto.TodoDto;
import me.oktop.springboottodolist.web.vo.TaskVo;
import me.oktop.springboottodolist.web.vo.TodoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class TodoRestController {

    private final TodoService todoService;

    @GetMapping("/todolist")
    public ResponseDto getTodolist(@RequestParam Integer page) {
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TodoDto> todoDtoPage = todoService.getTodolist(pageRequest);
        return ResponseDto.success(todoDtoPage);
    }

    @PostMapping("/todo")
    public ResponseDto saveTodo(@Valid @RequestBody TaskVo vo) {
        todoService.saveTask(vo);
        return ResponseDto.success();
    }

    @PutMapping("/todo")
    public ResponseDto updateTodo(@Valid @RequestBody TodoVo vo) {
        todoService.updateTodo(vo);
        return ResponseDto.success();
    }


}
