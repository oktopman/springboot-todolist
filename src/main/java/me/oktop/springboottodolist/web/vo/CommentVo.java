package me.oktop.springboottodolist.web.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class CommentVo {

    @NotNull
    private Long taskId;

    private Long commentId;

    @NotNull
    private String content;
}
