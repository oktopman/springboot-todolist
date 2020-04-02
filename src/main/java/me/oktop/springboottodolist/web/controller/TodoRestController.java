package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.service.TodoService;
import me.oktop.springboottodolist.web.dto.ResponseDto;
import me.oktop.springboottodolist.web.dto.TaskDto;
import me.oktop.springboottodolist.web.dto.TodoDto;
import me.oktop.springboottodolist.web.vo.TaskVo;
import me.oktop.springboottodolist.web.vo.TodoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/todo")
public class TodoRestController {

    private final TodoService todoService;

    @GetMapping("/list")
    public ResponseEntity getTodolist(@RequestParam Integer page) {
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<TodoDto> todoDtoPage = todoService.getTodolist(pageRequest);
        return ResponseEntity.ok(ResponseDto.success(todoDtoPage));
    }

    @PostMapping
    public ResponseEntity saveTodo(@Valid @RequestBody TaskVo vo) {
        TaskDto taskDto = todoService.saveTask(vo);
        return ResponseEntity.ok(ResponseDto.success(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTodo(@PathVariable Long id, @Valid @RequestBody TodoVo vo) {
        TaskDto taskDto = todoService.updateTodo(new TodoVo(id, vo));
        return ResponseEntity.ok(ResponseDto.success(taskDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok(ResponseDto.success());
    }


}
