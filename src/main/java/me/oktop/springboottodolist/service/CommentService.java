package me.oktop.springboottodolist.service;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.domain.todo.CommentRepository;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.web.dto.CommentDto;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public CommentDto saveComment(CommentVo vo) {
        boolean isExists = taskRepository.existsById(vo.getTaskId());
        if (!isExists) {
            throw new EntityNotFoundException();
        }
        Comment comment = Comment.builder()
                .content(vo.getContent())
                .build();

        Comment saveComment = commentRepository.save(comment);
        return saveComment.toDto();
    }

    public CommentDto updateComment(CommentVo vo) {
        boolean isExists = taskRepository.existsById(vo.getTaskId());
        if (!isExists) {
            throw new EntityNotFoundException();
        }
        Comment comment = commentRepository.findById(vo.getCommentId())
                .orElseThrow(EntityNotFoundException::new);

        Comment updateComment = comment.updateComment(vo.getContent());
        return updateComment.toDto();
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        commentRepository.delete(comment);
    }
}
