package edu.kmust.bully.publish.serviceTests;

import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.exception.ServiceException;
import edu.kmust.bully.common.dto.CommentDTO;
import edu.kmust.bully.common.model.Comment;
import edu.kmust.bully.publish.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CommentServiceTests {
    @Autowired
    ICommentService commentService;
    @Test
    void post(){
        CommentDTO commentDTO = new CommentDTO()
                    .setAnswerId(2)
                    .setContent("我觉得你说得对!");
        long userId = 3;
        String username = "alice";
        Comment comment = commentService.post(commentDTO,userId,username);
        log.debug("OK, comment >>> {}", comment);
    }

    @Test
    void delete(){
        try{
        Integer commentId = 1;
        long userId = 3;
        Integer role = 2;
        Comment comment = commentService.delete(commentId,userId,role);
        log.debug("OK, comment >>> {}", comment);
    } catch (ServiceException e) {
        log.debug("【删除评论失败】");
        log.debug("错误类型：{}", e.getClass().getName());
        log.debug("错误原因：{}", e.getMessage());
    }
    }

    @Test
    void getCommentByAnswerId(){
        Integer answerId = 2;
        Integer page = 0;
        PageInfo<Comment> comments = commentService.getCommentByAnswerId(answerId,page);
        log.debug("comment > {}",comments);
    }
}
