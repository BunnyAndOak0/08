package edu.kmust.bully.publish.mappertests;

import edu.kmust.bully.publish.mapper.CommentMapper;
import edu.kmust.bully.common.model.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CommentMapperTests {
    @Autowired
    CommentMapper commentMapper;
    @Test
    void deleteById(){
        Integer id = 2;
        int rows = commentMapper.deleteById(id);
        log.debug("delete ok, rows={}", rows);
    }

    @Test
    void selectById() {
        Integer id = 3;
        Comment comment = commentMapper.selectById(id);
        log.debug("query ok, result={}", comment);
    }

}
