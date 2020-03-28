package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.service.CommentService;
import me.oktop.springboottodolist.web.dto.ResponseDto;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity saveComment(CommentVo vo) {
        Comment comment = commentService.saveComment(vo);
        return ResponseEntity.ok(ResponseDto.success(comment));
    }
}
