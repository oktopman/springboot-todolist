package me.oktop.springboottodolist.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.service.CommentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void before() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(commentRestController).build();
    }

    @Test
    void comment_저장_테스트() throws Exception {
        Comment comment = Comment.builder()
                .content("댓글입니다.")
                .build();

        given(commentService.saveComment(any())).willReturn(comment);

        mockMvc.perform(
                post("/comment")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data.content", is("댓글입니다.")))
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(status().isOk());
    }

}