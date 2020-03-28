package me.oktop.springboottodolist.service;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.domain.todo.CommentRepository;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
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

    public Comment saveComment(CommentVo vo) {
        boolean isExists = taskRepository.existsById(vo.getTaskId());
        if (!isExists) {
            throw new EntityNotFoundException();
        }
        Comment comment = Comment.builder()
                .content(vo.getContent())
                .build();

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        commentRepository.delete(comment);
    }
}
