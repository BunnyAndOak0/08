package edu.kmust.gateway.config;

import edu.kmust.bully.common.security.UserInfo;
import edu.kmust.gateway.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author BunnyAndOak0
 * @Description 使用UserDetailConfig来进行对象的验证
 * @Date 2020/8/14 15:20
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        UserInfo userInfo = userMapper.loadUserByPhone(phone);
        if(userInfo == null){
            throw new UsernameNotFoundException("该用户尚未注册");
        }
        return userInfo;
    }
}
