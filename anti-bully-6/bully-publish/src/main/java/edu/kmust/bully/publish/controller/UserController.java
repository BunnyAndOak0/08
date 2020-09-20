package edu.kmust.bully.publish.controller;

import com.baomidou.mybatisplus.extension.api.R;
import edu.kmust.bully.common.security.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/publish/user")
public class UserController {

    @Value("${bully.SessionName}")
    private String sessionName;
    /*
     * @Author BunnyAndOak0
     * @Description session共享测试 可以删除
     **/
    @GetMapping("/getUser")
    public R getUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session);
        UserInfo user = (UserInfo) session.getAttribute(sessionName);
        return R.ok("userInfo：" + user);
    }
}
