package me.oktop.springboottodolist.web.controller;

import lombok.RequiredArgsConstructor;
import me.oktop.springboottodolist.service.CommentService;
import me.oktop.springboottodolist.web.dto.CommentDto;
import me.oktop.springboottodolist.web.dto.ResponseDto;
import me.oktop.springboottodolist.web.vo.CommentVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/comment")
@RestController
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/{taskId}")
    public ResponseEntity saveComment(@PathVariable Long taskId, @Valid @RequestBody CommentVo vo) {
        CommentDto commentDto = commentService.saveComment(new CommentVo(taskId, vo));
        return ResponseEntity.ok(ResponseDto.success(commentDto));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity updateComment(@PathVariable Long taskId, @Valid @RequestBody CommentVo vo) {
        CommentDto commentDto = commentService.updateComment(new CommentVo(taskId, vo));
        return ResponseEntity.ok(ResponseDto.success(commentDto));
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
