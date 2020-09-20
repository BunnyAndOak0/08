package edu.kmust.bully.publish.serviceTests;

import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.exception.ServiceException;
import edu.kmust.bully.common.dto.AnswerDTO;
import edu.kmust.bully.publish.service.IAnswerService;
import edu.kmust.bully.common.vo.AnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AnswerServiceTests {

    @Autowired
    IAnswerService service;

    @Test
    void post() {
        try {
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setModelId(3);
            answerDTO.setContent("这种人应该受到惩罚!");
            long userId = 5;
            String username= "alice";
            service.post(answerDTO, userId, username);
            log.debug("OK");
        } catch (ServiceException e) {
            log.debug("failure >>> ", e);
        }
    }

    @Test
    void getListByModelId(){
        Integer id = 4;
        Integer page = 0;
        PageInfo<AnswerVO> answers = service.getListByModelId(id,page);
            log.debug("answer >{}",answers);

    }

}