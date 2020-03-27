package me.oktop.springboottodolist.domain.comment;

import me.oktop.springboottodolist.domain.todo.Comment;
import me.oktop.springboottodolist.domain.todo.CommentRepository;
import me.oktop.springboottodolist.domain.todo.Task;
import me.oktop.springboottodolist.domain.todo.TaskRepository;
import me.oktop.springboottodolist.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void comment_저장후조회_테스트() {
        //given
        String title = "todolist 만들기";
        String content = "차근차근 요구사항 추가 시키면서 만들어보자~";
        TaskStatus status = TaskStatus.TODO;
        LocalDate expectedDate = LocalDate.of(2020, 03, 26);
        Task task = Task.builder()
                .title(title)
                .content(content)
                .status(status)
                .build();

        task.setExpectedDate(expectedDate);

        Comment comment = Comment.builder()
                .content("추가적으로 코멘트도 저장하기")
                .build();

        Comment comment2 = Comment.builder()
                .content("추가2번")
                .build();

        comment.addTask(task);
        comment2.addTask(task);

        taskRepository.save(task);
        commentRepository.save(comment);
        commentRepository.save(comment2);

        //when
        Task getTask = taskRepository.findById(1L)
                .orElseThrow(EntityNotFoundException::new);
        //then
        assertNotNull(getTask);
        assertThat(getTask.getTitle(), is(title));
        assertThat(getTask.getContent(), is(content));
        assertThat(getTask.getStatus(), is(status));
        assertThat(getTask.getExpectedDate(), is(expectedDate));
        assertThat(getTask.getComments().size(), is(equalTo(2)));
        assertThat(getTask.getComments().get(0).getContent(), is("추가적으로 코멘트도 저장하기"));
        assertThat(getTask.getComments().get(1).getContent(), is("추가2번"));
    }

}