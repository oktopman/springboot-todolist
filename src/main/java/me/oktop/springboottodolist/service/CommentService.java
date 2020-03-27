package me.oktop.springboottodolist.service;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.domain.todo.CommentRepository;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public void saveComment(CommentVo vo) {
        boolean isExists = taskRepository.existsById(vo.getTaskId());
        if (!isExists) {
            throw new EntityNotFoundException();
        }
        Comment comment = Comment.builder()
                .content(vo.getContent())
                .build();

        commentRepository.save(comment);
    }
}
