package me.oktop.springboottodolist.web.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class CommentVo {

    private Long taskId;

    private Long commentId;

    @NotNull
    private String content;

    public CommentVo(Long taskId, CommentVo vo) {
        this.taskId = taskId;
        this.commentId = vo.commentId;
        this.content = vo.getContent();
    }
}
