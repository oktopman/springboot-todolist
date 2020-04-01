package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Task;
import me.oktop.springboottodolist.service.TodoService;
import me.oktop.springboottodolist.web.dto.ResponseDto;
import me.oktop.springboottodolist.web.dto.TodoDto;
import me.oktop.springboottodolist.web.vo.TaskVo;
import me.oktop.springboottodolist.web.vo.TodoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<TodoDto> todoDtoPage = todoService.getTodolist(pageRequest);
        return ResponseEntity.ok(ResponseDto.success(todoDtoPage));
    }

    @PostMapping
    public ResponseEntity saveTodo(@Valid @RequestBody TaskVo vo) {
        Task task = todoService.saveTask(vo);
        return ResponseEntity.ok(ResponseDto.success(task));
    }

    //todo pk를 pathvariable로 빼기
    @PutMapping("/{id}")
    public ResponseEntity updateTodo(@PathVariable Long id, @Valid @RequestBody TodoVo vo) {
        Task task = todoService.updateTodo(id, vo);
        return ResponseEntity.ok(ResponseDto.success(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok(ResponseDto.success());
    }


}
