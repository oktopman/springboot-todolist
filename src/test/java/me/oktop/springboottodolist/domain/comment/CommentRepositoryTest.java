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

    private String title = "todolist 만들기";
    private String content = "차근차근 요구사항 추가 시키면서 만들어보자~";
    private TaskStatus status = TaskStatus.TODO;
    private LocalDate expectedDate = LocalDate.of(2020, 03, 26);
    private String commentContent1 = "추가적으로 코멘트도 저장하기";
    private String commentContent2 = "추가적으로 코멘트도 저장하기2";

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void comment_저장후조회_테스트() {
        //given
        Task task = Task.builder()
                .title(title)
                .content(content)
                .status(status)
                .build();

        task.setExpectedDate(expectedDate);

        Comment comment = Comment.builder()
                .content(commentContent1)
                .build();

        Comment comment2 = Comment.builder()
                .content(commentContent2)
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
        assertThat(getTask.getComments().get(0).getContent(), is(commentContent1));
        assertThat(getTask.getComments().get(1).getContent(), is(commentContent2));
    }

    @Test
    void comment_수정_테스트() {
        //given
        Task task = Task.builder()
                .title(title)
                .content(content)
                .status(status)
                .build();

        task.setExpectedDate(expectedDate);

        Comment comment = Comment.builder()
                .content(commentContent1)
                .build();

        Comment comment2 = Comment.builder()
                .content(commentContent2)
                .build();

        comment.addTask(task);
        comment2.addTask(task);

        Task saveTask = taskRepository.save(task);
        commentRepository.save(comment);
        commentRepository.save(comment2);

        //when
        Task getTask = taskRepository.findById(saveTask.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertThat(getTask.getComments().get(0).getContent(), is(commentContent1));
        String updateContent = "수정합니다.";
        getTask.getComments().get(0).updateComment(updateContent);

        assertThat(getTask.getComments().get(0).getContent(), is(updateContent));
    }

    @Test
    void comment_삭제_테스트() {
        //given
        String content = "차근차근 요구사항 추가 시키면서 만들어보자~";
        Comment comment = Comment.builder()
                .content(content)
                .build();

        Comment save = commentRepository.save(comment);
        assertThat(save.getContent(), is(content));

        //when
        commentRepository.delete(save);

        boolean isExits = commentRepository.existsById(save.getId());
        assertThat(isExits, is(false));


    }

}