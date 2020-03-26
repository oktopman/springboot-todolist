package me.oktop.springboottodolist.service;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.task.Task;
import me.oktop.springboottodolist.domain.task.TaskRepository;
import me.oktop.springboottodolist.web.dto.TodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public Page<TodoDto> getTodolist(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        long count = taskRepository.count();
        List<TodoDto> todoDtoList = entityToDto(taskPage);
        return new PageImpl<>(todoDtoList, pageable, count);
    }

    private List<TodoDto> entityToDto(Page<Task> taskPage) {
        List<TodoDto> todoDtoList = new ArrayList<>();
        for (Task task : taskPage.getContent()) {
            todoDtoList.add(task.toDto());
        }
        return todoDtoList;
    }
}
