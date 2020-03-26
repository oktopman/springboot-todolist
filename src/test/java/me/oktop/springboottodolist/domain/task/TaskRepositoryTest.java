package me.oktop.springboottodolist.domain.task;

import me.oktop.springboottodolist.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class TaskRepositoryTest {

    private String title = "todolist 만들기";
    private String content = "차근차근 요구사항 추가 시키면서 만들어보자~";
    private TaskStatus status = TaskStatus.TODO;
    private LocalDate expectedDate = LocalDate.of(2020, 03, 26);

    @Autowired
    TaskRepository taskRepository;

    @Test
    void task_저장후조회_테스트() {
        //given
        Task task = Task.builder()
                .title(title)
                .content(content)
                .status(status)
                .build();

        task.setExpectedDate(expectedDate);

        //when
        Task getTask = taskRepository.save(task);

        //then
        assertNotNull(getTask);
        assertThat(getTask.getTitle(), is(title));
        assertThat(getTask.getExpectedDate(), is(expectedDate));
    }

    @Test
    void task를_pageable로_조회_테스트() {
        //given
        Task task = Task.builder()
                .title(title)
                .content(content)
                .status(status)
                .build();

        taskRepository.save(task);
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Task> taskPage = taskRepository.findAll(pageRequest);

        //then
        assertThat(taskPage.getTotalElements(), is(equalTo(1L)));
        assertThat(taskPage.getContent().get(0).getContent(), is(content));
    }

}