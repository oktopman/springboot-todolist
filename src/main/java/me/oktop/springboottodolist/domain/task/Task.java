package me.oktop.springboottodolist.domain.task;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.oktop.springboottodolist.domain.BaseTimeEntity;
import me.oktop.springboottodolist.domain.comment.Comment;
import me.oktop.springboottodolist.enums.TaskStatus;
import me.oktop.springboottodolist.web.dto.TodoDto;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate expectedDate;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments = new ArrayList<>();

    //todo author 추가

    @Builder
    public Task(String title, String content, TaskStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public TodoDto toDto() {
        TodoDto dto = new TodoDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
