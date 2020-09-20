package edu.kmust.bully.publish.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.kmust.bully.common.model.User;
import edu.kmust.bully.publish.mapper.UserMapper;
import edu.kmust.bully.publish.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
