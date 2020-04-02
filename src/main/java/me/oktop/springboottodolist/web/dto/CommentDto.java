package me.oktop.springboottodolist.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
