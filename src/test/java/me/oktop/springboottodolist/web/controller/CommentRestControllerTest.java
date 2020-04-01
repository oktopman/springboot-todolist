package me.oktop.springboottodolist.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.service.CommentService;
import me.oktop.springboottodolist.web.dto.CommentDto;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CommentRestControllerTest {

    @InjectMocks
    CommentRestController commentRestController;

    @Mock
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void before() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(commentRestController).build();
    }

    @Test
    void comment_저장_테스트() throws Exception {
        //given
        String content = "댓글입니다";

        CommentVo vo = new CommentVo();
        vo.setContent(content);
        Long taskId = -1L;

        CommentDto dto = new CommentDto();
        dto.setContent(content);

        when(commentService.saveComment(any(CommentVo.class))).thenReturn(dto);

        //when
        mockMvc.perform(
                post("/comment/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vo)))
                .andDo(print())
                .andExpect(jsonPath("$.data.content", is(content)))
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(status().isOk());
    }

    @Test
    void comment_수정_테스트() throws Exception {
        //given
        Long taskId = -1L;
        Long commentId = -1L;
        String content = "댓글수정";

        CommentVo vo = new CommentVo();
        vo.setContent(content);
        vo.setCommentId(commentId);

        CommentDto dto = new CommentDto();
        dto.setContent(content);

        given(commentService.updateComment(any(CommentVo.class))).willReturn(dto);

        //when
        mockMvc.perform(
                put("/comment/{taskId}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vo)))
                .andDo(print())
                .andExpect(jsonPath("$.data.content", is(content)))
                .andExpect(status().isOk());
    }

    @Test
    void comment_삭제_테스트() throws Exception {

        mockMvc.perform(
                delete("/comment/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(status().isOk());
    }

}