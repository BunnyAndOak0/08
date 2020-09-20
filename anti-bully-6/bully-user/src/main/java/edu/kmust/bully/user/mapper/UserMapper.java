package edu.kmust.bully.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.kmust.bully.common.model.User;
import edu.kmust.bully.common.security.UserInfo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 * 用于注册登录的基本信息的存储 Mapper 接口
 * </p>
 *
 * @author BunyAndOak
 * @since 2020-08-14
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    UserInfo loadUserByUsername(String username);

    String selectByName(String username);

    int forbiddenUser(String username);

    void reportReset(String username);

    int updateUser(String oldName, String newName, LocalDateTime birth,
                    int sexNum, String location);

    int updateByPhone(String phone, String epassword);
}
