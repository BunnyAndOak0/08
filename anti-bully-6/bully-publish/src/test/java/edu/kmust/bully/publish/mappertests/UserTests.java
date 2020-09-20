package edu.kmust.bully.publish.mappertests;

import edu.kmust.bully.common.model.User;
import edu.kmust.bully.publish.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserTests {
    @Autowired
    UserMapper userMapper;
    @Test
    void insert(){
        User user = new User();
        user.setUsername("alice");
        user.setPassword("123456");
        user.setPhone("13988883293");
        user.setRole(2);
        int rows = userMapper.insert(user);
        log.debug("rows >",rows);
    }
}
