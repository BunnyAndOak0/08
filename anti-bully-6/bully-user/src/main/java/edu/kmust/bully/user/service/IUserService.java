package edu.kmust.bully.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.kmust.bully.common.model.User;

import java.time.LocalDateTime;

/**
 * <p>
 * 用于注册登录的基本信息的存储 服务类
 * </p>
 *
 * @author BunyAndOak
 * @since 2020-08-14
 */
public interface IUserService extends IService<User> {
    void userRegister(User user);

    void sendCode(String phone);

    String getPhone(String username);

    int userReport(String username);

    int getReport(String username);

    void reportReset(String username);

    int updateUser(String oldName, String newName, LocalDateTime birth,
                           int sexNum, String location);

    void updateByPhone(String phone, String password);

    User getUserById(Integer id);
}
