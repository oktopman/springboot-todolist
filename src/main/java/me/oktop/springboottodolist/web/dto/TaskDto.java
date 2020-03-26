package me.oktop.springboottodolist.web.dto;

import lombok.Getter;
import lombok.Setter;
import me.oktop.springboottodolist.enums.TaskStatus;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDto {

    private Long id;
    private String title;
    private String content;
    private TaskStatus status;
    private LocalDate expectedDate;
}
