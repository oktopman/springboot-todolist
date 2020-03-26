package me.oktop.springboottodolist.domain.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.oktop.springboottodolist.domain.BaseTimeEntity;
import me.oktop.springboottodolist.domain.task.Task;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    //todo author 추가

    @Builder
    public Comment(String content) {
        this.content = content;
    }

    public void addTask(Task task) {
        if (this.task != null) {
            this.task.getComments().remove(this);
        }
        this.task = task;
        task.getComments().add(this);
    }
}
