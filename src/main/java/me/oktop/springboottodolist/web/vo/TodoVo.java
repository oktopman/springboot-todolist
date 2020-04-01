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

    private Long id;

    private String title;

    private String content;

    private LocalDate expectedDate;

    private TaskStatus status;

    public TodoVo(Long id, TodoVo vo) {
        this.id = id;
        this.title = vo.title;
        this.content = vo.getContent();
        this.expectedDate = vo.getExpectedDate();
        this.status = vo.status;
    }

}
