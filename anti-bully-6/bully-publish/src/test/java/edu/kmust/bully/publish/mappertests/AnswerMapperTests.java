package edu.kmust.bully.publish.mappertests;

import edu.kmust.bully.publish.mapper.AnswerMapper;
import edu.kmust.bully.common.vo.AnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class AnswerMapperTests {
    @Autowired
    AnswerMapper answerMapper;

    @Test
    void findListByModelId(){
        Integer id = 1;
        List<AnswerVO> answers = answerMapper.findListByModelId(id);
        for (AnswerVO answer : answers) {
            log.debug("AnswerVO >>> {}", answer);
        }
    }

}
