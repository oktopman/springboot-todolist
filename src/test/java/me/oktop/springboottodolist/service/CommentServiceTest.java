package me.oktop.springboottodolist.service;

import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.domain.todo.CommentRepository;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

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
    void 댓글_저장시_task가_없을때_테스트() {
        //given
        CommentVo vo = new CommentVo();
        vo.setTaskId(1L);
        vo.setCommentId(1L);
        vo.setContent("댓글");

        given(taskRepository.existsById(vo.getTaskId())).willReturn(false);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            commentService.saveComment(vo);
        });
    }

    @Test
    void 댓글_저장_테스트() {
        //given
        CommentVo vo = new CommentVo();
        vo.setTaskId(1L);
        vo.setCommentId(1L);
        vo.setContent("댓글");

        Comment comment = Comment.builder()
                .content(vo.getContent())
                .build();

        given(taskRepository.existsById(vo.getTaskId())).willReturn(true);
        given(commentRepository.save(any())).willReturn(comment);
        commentService.saveComment(vo);
    }

    @Test
    void 댓글_삭제_테스트() {
        //given
        Comment comment = Comment.builder()
                .content("content...")
                .build();

        given(commentRepository.findById(any())).willReturn(java.util.Optional.ofNullable(comment));

        commentService.deleteComment(1L);
    }

}