package me.oktop.springboottodolist.domain.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.oktop.springboottodolist.domain.BaseTimeEntity;
import me.oktop.springboottodolist.enums.TaskStatus;
import me.oktop.springboottodolist.web.dto.TaskDto;
import me.oktop.springboottodolist.web.vo.TodoVo;
import org.modelmapper.ModelMapper;

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

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate expectedDate;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
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

    public void update(TodoVo vo) {
        this.title = vo.getTitle();
        this.content = vo.getContent();
        this.expectedDate = vo.getExpectedDate();
        this.status = vo.getStatus();

    }

    public TaskDto toDto(ModelMapper modelMapper) {
        return modelMapper.map(this, TaskDto.class);
    }
}
