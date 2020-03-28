package me.oktop.springboottodolist.web.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class TaskVo {

    private Long id;

    @NotNull
    private String title;

    private String content;

    private LocalDate expectedDate;

    public TaskVo(String title, String content, LocalDate expectedDate) {
        this.title = title;
        this.content = content;
        this.expectedDate = expectedDate;
    }

    public TaskVo(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
