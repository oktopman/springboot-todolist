package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.service.CommentService;
import me.oktop.springboottodolist.web.dto.ResponseDto;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/comment")
@RestController
public class CommentRestController {

    private final CommentService commentService;

    //todo pk를 pathvariable로 빼기, 사용하지않는 commentId 빼기
    @PostMapping
    public ResponseEntity saveComment(CommentVo vo) {
        Comment comment = commentService.saveComment(vo);
        return ResponseEntity.ok(ResponseDto.success(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        commentService.deleteComment(id);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
