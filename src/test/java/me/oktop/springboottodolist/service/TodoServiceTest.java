package me.oktop.springboottodolist.service;

import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.domain.todo.Task;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.enums.TaskStatus;
import me.oktop.springboottodolist.web.dto.TodoDto;
import me.oktop.springboottodolist.web.vo.TaskVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    private String title = "todolist 만들기";
    private String content = "차근차근 요구사항 추가 시키면서 만들어보자~";
    private TaskStatus status = TaskStatus.TODO;
    private LocalDate expectedDate = LocalDate.of(2020, 03, 26);

    @InjectMocks
    TodoService todoService;

    @Mock
    TaskRepository taskRepository;

    @Test
    void task_저장_테스트() {
        //given
        TaskVo vo = new TaskVo(title, content, expectedDate);

        Task mockTask = Task.builder()
                .title(vo.getTitle())
                .content(vo.getContent())
                .status(TaskStatus.TODO)
                .build();

        mockTask.setExpectedDate(expectedDate);

        given(taskRepository.save(any())).willReturn(mockTask);
        //when
        Task task = todoService.saveTask(vo);

        //then
        assertThat(task.getTitle(), is(title));
        assertThat(task.getContent(), is(content));
    }

    @Test
    void todolist_pageable_조회_테스트() {
        //given
        Task task = Task.builder()
                .title(title)
                .content(content)
                .status(TaskStatus.TODO)
                .build();
        task.setExpectedDate(expectedDate);

        String content1 = "댓글1";
        String content2 = "댓글2";

        Comment comment1 = Comment.builder()
                .content(content1)
                .build();

        Comment comment2 = Comment.builder()
                .content(content2)
                .build();
        task.getComments().add(comment1);
        task.getComments().add(comment2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> mockTaskPage = new PageImpl<>(Collections.singletonList(task), pageable, 1);
        given(taskRepository.findAll(pageable)).willReturn(mockTaskPage);
        given(taskRepository.count()).willReturn(1L);

        //when
        Page<TodoDto> todolistPage = todoService.getTodolist(pageable);

        //then
        assertThat(todolistPage.getTotalElements(), is(equalTo(1L)));
        assertThat(todolistPage.getContent().get(0).getTask().getTitle(), is(title));
        assertThat(todolistPage.getContent().get(0).getTask().getExpectedDate(), is(expectedDate));
        assertThat(todolistPage.getContent().get(0).getTask().getComments().get(0).getContent(), is(content1));
        assertThat(todolistPage.getContent().get(0).getTask().getComments().get(1).getContent(), is(content2));
    }

}