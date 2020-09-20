package edu.kmust.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.kmust.bully.common.model.User;
import edu.kmust.bully.common.security.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/21 17:27
 **/
@Repository
public interface UserMapper extends BaseMapper<User> {
    UserInfo loadUserByPhone(String phone);
}
