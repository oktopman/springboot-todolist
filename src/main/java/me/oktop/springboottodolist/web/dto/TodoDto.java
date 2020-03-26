package me.oktop.springboottodolist.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDto {

    private TaskDto tasks;
    private CommentDto comments;
}
