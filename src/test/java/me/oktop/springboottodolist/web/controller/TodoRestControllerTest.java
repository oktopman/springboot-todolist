package me.oktop.springboottodolist.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        TaskVo vo = new TaskVo(title, content, expectedDate);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String content = objectMapper.writeValueAsString(vo);
        mockMvc.perform(
                post("/todo")
//                        .content(content)
                        .content("{\"title\":\"title입니다\", \"content\":\"content입니다.\", \"expectedDate\":\"2020-03-27\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("200")))
                .andDo(print());
    }

    @Test
    void todolist_조회_테스트() throws Exception {
        int page = 0;
        Pageable pageable = PageRequest.of(0, 10);
        given(todoService.getTodolist(pageable)).willReturn(createMock());

        mockMvc.perform(
                get("/todolist?page=" + page)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].tasks.title", is("todolist 만들기")))
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(jsonPath("$.data.content[0].tasks.comments[0].content", is("댓글1")))
                .andExpect(jsonPath("$.data.content[0].tasks.comments[1].content", is("댓글2")))
                .andDo(print());
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
        todoDto.setTasks(taskDto);
        todoDtoList.add(todoDto);
        Pageable pageable = PageRequest.of(0, 10);
        return new PageImpl<>(todoDtoList, pageable, 1);
    }

    @Test
    void todo_수정_테스트() throws Exception {
        TodoVo vo = new TodoVo();
        vo.setId(1L);
        vo.setContent("content입니다.");
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(vo);
        mockMvc.perform(
                put("/todo")
                        .content(body)
//                        .content("{\"id\":\1L, \"title\":\"title입니다.\", \"content\":\"content입니다.\"," +
//                                " \"expectedDate\":\"2020-03-27\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("200")))
                .andDo(print());

    }

}