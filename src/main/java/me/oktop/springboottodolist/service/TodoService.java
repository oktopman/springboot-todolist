package me.oktop.springboottodolist.service;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Task;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.enums.TaskStatus;
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
import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class TodoService {

    private final TaskRepository taskRepository;

    public Task saveTask(TaskVo vo) {
        Task task = Task.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .status(TaskStatus.TODO)
                .build();

        if (vo.getExpectedDate() != null) {
            task.setExpectedDate(vo.getExpectedDate());
        }
        return taskRepository.save(task);
    }

    public Task updateTodo(Long id, TodoVo vo) {
        Task task = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        task.update(vo);
        return task;
    }

    public Page<TodoDto> getTodolist(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        long count = taskRepository.count();
        List<TodoDto> todoDtoList = entityToDto(taskPage);
        return new PageImpl<>(todoDtoList, pageable, count);
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
