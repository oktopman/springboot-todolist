package me.oktop.springboottodolist.web.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.oktop.springboottodolist.enums.TaskStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TodoVo {

    private String title;

    private String content;

    private LocalDate expectedDate;

    private TaskStatus status;

}
