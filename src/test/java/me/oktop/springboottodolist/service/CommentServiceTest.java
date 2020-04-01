package me.oktop.springboottodolist.service;

import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.domain.todo.CommentRepository;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.web.dto.CommentDto;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    CommentRepository commentRepository;

    @Test
    void comment_저장시_task가_없을때_테스트() {
        //given
        Long taskId = -1L;

        CommentVo vo = new CommentVo();
        vo.setTaskId(taskId);
        vo.setContent("댓글");

        given(taskRepository.existsById(taskId)).willReturn(false);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            commentService.saveComment(vo);
        });
    }

    @Test
    void comment_저장_테스트() {
        //given
        Long taskId = -1L;

        CommentVo vo = new CommentVo();
        vo.setContent("댓글");
        vo.setTaskId(taskId);

        Comment comment = Comment.builder()
                .content(vo.getContent())
                .build();

        given(taskRepository.existsById(taskId)).willReturn(true);
        given(commentRepository.save(any())).willReturn(comment);
        CommentDto commentDto = commentService.saveComment(vo);
        assertThat(commentDto.getContent(), is("댓글"));
    }

    @Test
    void comment_수정_테스트() {
        //given
        Long taskId = -1L;
        Long commentId = -1L;
        String beforeContent = "댓글";
        String afterContent = "댓글수정";

        CommentVo vo = new CommentVo();
        vo.setContent(afterContent);
        vo.setCommentId(commentId);
        vo.setTaskId(taskId);

        Comment comment = Comment.builder()
                .content(beforeContent)
                .build();

        given(taskRepository.existsById(taskId)).willReturn(true);
        given(commentRepository.findById(vo.getCommentId())).willReturn(java.util.Optional.ofNullable(comment));

        //when
        CommentDto commentDto = commentService.updateComment(vo);
        assertThat(commentDto.getContent(), is(afterContent));
    }

    @Test
    void comment_삭제_테스트() {
        //given
        Comment comment = Comment.builder()
                .content("content...")
                .build();

        given(commentRepository.findById(any())).willReturn(java.util.Optional.ofNullable(comment));
        commentService.deleteComment(1L);
    }

}