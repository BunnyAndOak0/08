package edu.kmust.bully.user;

import edu.kmust.bully.user.util.EncodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;


/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/14 10:58
 **/
@SpringBootTest
@Slf4j
public class BullyUserApplicationTest {

    @Test
    public void test1(){
        log.info("将springsecurity的密码做转换：{}",
                EncodeUtils.encode("1234"));
        //转换过后：$2a$10$E0zPE/Zxo7hPekFrAWlPgeHN2rdO5oKF7urQpHwZN.akKq4ZI04D
    }

    @Test
    public void test2(){
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int day = cal.get(Calendar.SUNDAY);

        log.info("day = {}", day);

    }
}
