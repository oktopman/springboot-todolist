package me.oktop.springboottodolist.service;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Task;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.enums.TaskStatus;
import me.oktop.springboottodolist.web.dto.TaskDto;
import me.oktop.springboottodolist.web.dto.TodoDto;
import me.oktop.springboottodolist.web.vo.TaskVo;
import me.oktop.springboottodolist.web.vo.TodoVo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class TodoService {

    private final TaskRepository taskRepository;

    public TaskDto saveTask(TaskVo vo) {
        Task task = Task.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .status(TaskStatus.TODO)
                .build();

        if (vo.getExpectedDate() != null) {
            task.setExpectedDate(vo.getExpectedDate());
        }
        Task saveTask = taskRepository.save(task);
        ModelMapper modelMapper = new ModelMapper();
        return saveTask.toDto(modelMapper);
    }

    public TaskDto updateTodo(TodoVo vo) {
        Task task = taskRepository.findById(vo.getId())
                .orElseThrow(EntityNotFoundException::new);

        task.update(vo);
        ModelMapper modelMapper = new ModelMapper();
        return task.toDto(modelMapper);
    }

    public Page<TodoDto> getTodolist(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
//        long count = taskRepository.count();
        List<TodoDto> todoDtoList = entityToDto(taskPage);
        return new PageImpl<>(todoDtoList, pageable, taskPage.getTotalElements());
    }

    private List<TodoDto> entityToDto(Page<Task> taskPage) {
        List<TodoDto> todoDtoList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Task task : taskPage.getContent()) {
            TodoDto dto = new TodoDto();
            dto.setTask(task.toDto(modelMapper));
            todoDtoList.add(dto);
        }
        return todoDtoList;
    }

    public void deleteTodo(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        taskRepository.delete(task);
    }
}
