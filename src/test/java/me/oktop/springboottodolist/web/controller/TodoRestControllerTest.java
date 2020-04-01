package me.oktop.springboottodolist.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import me.oktop.springboottodolist.domain.todo.Task;
import me.oktop.springboottodolist.enums.TaskStatus;
import me.oktop.springboottodolist.service.TodoService;
import me.oktop.springboottodolist.web.dto.CommentDto;
import me.oktop.springboottodolist.web.dto.TaskDto;
import me.oktop.springboottodolist.web.dto.TodoDto;
import me.oktop.springboottodolist.web.vo.TaskVo;
import me.oktop.springboottodolist.web.vo.TodoVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TodoRestControllerTest {

    private String title = "todolist 만들기";
    private String content = "차근차근 요구사항 추가 시키면서 만들어보자~";
    private TaskStatus status = TaskStatus.TODO;
    private LocalDate expectedDate = LocalDate.of(2020, 03, 26);

    @InjectMocks
    TodoRestController todoRestController;

    @Mock
    TodoService todoService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void before() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(todoRestController).build();
    }

    @Test
    void todolist_저장_테스트() throws Exception {
        //given
        TaskVo vo = new TaskVo(title, content);
        Task mockTask = Task.builder()
                .title(title)
                .content(content)
                .build();

        TaskDto dto = new TaskDto();
        dto.setTitle(title);
        dto.setContent(content);

        mockTask.setExpectedDate(expectedDate);
        given(todoService.saveTask(any())).willReturn(dto);
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        mockMvc.perform(
                post("/todo")
                        .content(objectMapper.writeValueAsString(vo))
//                        .content("{\"title\":\"title입니다\", \"content\":\"content입니다.\", \"expectedDate\":\"2020-03-27\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data.title", is(title)))
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(status().isOk());
    }

    @Ignore
    @Test
    void todo_수정_테스트() throws Exception {
        String content = "content입니다.";
        Long taskId = -1L;

        TodoVo vo = new TodoVo();
        vo.setContent(content);
        ObjectMapper objectMapper = new ObjectMapper();

        TaskDto dto = new TaskDto();
        dto.setTitle(title);
        dto.setContent(content);

        given(todoService.updateTodo(any(TodoVo.class))).willReturn(dto);

        mockMvc.perform(
                put("/todo/{id}", taskId)
                        .content(objectMapper.writeValueAsString(vo))
//                        .content("{\"id\":\1L, \"title\":\"title입니다.\", \"content\":\"content입니다.\"," +
//                                " \"expectedDate\":\"2020-03-27\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", is(title)))
                .andExpect(jsonPath("$.data.content", is(content)))
                .andExpect(jsonPath("$.code", is("200")))
                .andDo(print());
    }

    @Test
    void todolist_조회_테스트() throws Exception {
        int page = 0;
        Pageable pageable = PageRequest.of(0, 10);
        given(todoService.getTodolist(pageable)).willReturn(createMock());

        mockMvc.perform(
                get("/todo/list?page=" + page)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data.content[0].task.title", is("todolist 만들기")))
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(jsonPath("$.data.content[0].task.comments[0].content", is("댓글1")))
                .andExpect(jsonPath("$.data.content[0].task.comments[1].content", is("댓글2")))
                .andExpect(status().isOk());
    }

    private Page<TodoDto> createMock() {
        TodoDto todoDto = new TodoDto();
        TaskDto taskDto = new TaskDto();
        List<CommentDto> commentDtoList = new ArrayList<>();
        CommentDto commentDto1 = new CommentDto();
        commentDto1.setContent("댓글1");

        CommentDto commentDto2 = new CommentDto();
        commentDto2.setContent("댓글2");
        commentDtoList.add(commentDto1);
        commentDtoList.add(commentDto2);

        taskDto.setTitle(title);
        taskDto.setExpectedDate(expectedDate);
        taskDto.setComments(commentDtoList);

        List<TodoDto> todoDtoList = new ArrayList<>();
        todoDto.setTask(taskDto);
        todoDtoList.add(todoDto);
        Pageable pageable = PageRequest.of(0, 10);
        return new PageImpl<>(todoDtoList, pageable, 1);
    }

    @Test
    void todo_삭제_테스트() throws Exception {
        mockMvc.perform(
                delete("/todo/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(status().isOk());

    }

}