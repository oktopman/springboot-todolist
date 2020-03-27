package me.oktop.springboottodolist.service;

import jdk.nashorn.internal.ir.annotations.Ignore;
import me.oktop.springboottodolist.domain.todo.Task;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.enums.TaskStatus;
import me.oktop.springboottodolist.web.dto.TaskDto;
import me.oktop.springboottodolist.web.vo.TodoVo;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Transactional
@Ignore
public class TestTest {

    @Autowired
    TodoService todoService;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void todo_수정시_modelmapper_테스트() {
        Task task = Task.builder()
                .title("title")
                .content("content")
                .status(TaskStatus.TODO)
                .build();

        task.setExpectedDate(LocalDate.of(2020,03,25));
        System.out.println("첫번재 저장");
        Task save = taskRepository.save(task);

        TodoVo vo = new TodoVo();
        vo.setId(save.getId());
        vo.setTitle("title 수정");
        vo.setContent("content 수정");
        vo.setExpectedDate(LocalDate.now());
        vo.setStatus(TaskStatus.DOING);

        System.out.println("updateTodo 호출.........");
        todoService.updateTodo(vo);

        Optional<Task> byId = taskRepository.findById(save.getId());
        System.out.println("==============");
    }

    @Test
    void modelmapper_테스트() {
        TodoVo vo = new TodoVo();
        vo.setId(1L);
        vo.setTitle("title 수정");
        vo.setContent("content 수정");
        vo.setExpectedDate(LocalDate.now());
        vo.setStatus(TaskStatus.DOING);

        ModelMapper modelMapper = new ModelMapper();
        Task map = modelMapper.map(vo, Task.class);
        System.out.println("============");
    }

    @Test
    void modelMapper_테스트2() {
        TaskDto dto = new TaskDto();
        dto.setId(1L);
        dto.setTitle("title 수정");
        dto.setContent("content 수정");
        dto.setExpectedDate(LocalDate.now());
        dto.setStatus(TaskStatus.DOING);

        ModelMapper modelMapper = new ModelMapper();
        Task map = modelMapper.map(dto, Task.class);
        System.out.println("============");
    }

    @Test
    void entity를_dto로_modelmapper_테스트() {
        Task task = Task.builder()
                .title("title")
                .content("content")
                .status(TaskStatus.DOING)
                .build();

        ModelMapper modelMapper = new ModelMapper();
        TaskDto map = modelMapper.map(task, TaskDto.class);
        System.out.println("============");

    }
}
