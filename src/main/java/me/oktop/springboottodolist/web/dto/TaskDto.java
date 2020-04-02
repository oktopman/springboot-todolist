package me.oktop.springboottodolist.web.dto;

import lombok.Getter;
import lombok.Setter;
import me.oktop.springboottodolist.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskDto {

    private Long id;
    private String title;
    private String content;
    private TaskStatus status;
    private LocalDate expectedDate;
    private List<CommentDto> comments;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
