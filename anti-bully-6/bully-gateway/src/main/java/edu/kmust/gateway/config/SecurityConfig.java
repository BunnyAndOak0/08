package edu.kmust.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.common.security.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/14 2:24
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Value("${bully.SessionName}")
    private String sessionName;
    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }
    /*
     * @Author BunnyAndOak0
     * @Description 避免异步请求被拦截
     * @Date 2:31 2020/8/14
     * @Param [http]
     * @return void
     **/

    @Override
    public void configure(WebSecurity web) throws Exception {

//        web.ignoring().antMatchers("/user/register");
        web.ignoring().antMatchers("/user/register", "/register.html", "register",
                "/user/sendCode", "/user/forget/update", "/forget.html",
                "/user/forget/sendCode", "/user/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    //白名单 不需要登录就可以访问
    String[] antMatchers = {
            "/login.html",
            "/register.html",
            "/forget.html",
            "/login",
            "/api/bully-user/user/sendCode",
            "/api/bully-user/user/register",
            "/api/bully-user/user/forget/sendCode",
            "/api/bully-user/user/forget/update",
            "/success.html",
            "/logout",
            //todo 前后端联调的时候把这个开放便于联调 之后可以把这个打开
            "/api/**",
            /*
             * @Author BunnyAndOak0
             * @Description 静态资源
             **/
            "/bower_components/**",
            "/css/**",
            "/img/**",
            "/js/**",

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(antMatchers).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                /*使用手机号进行登录*/
                .usernameParameter("phone")
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler((req, resp, authentication) -> {
                    //authentication指当前登录的用户信息
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    UserInfo user = (UserInfo) authentication.getPrincipal();
//                    out.println(new ObjectMapper().writeValueAsString(R.ok("登录成功!", new String[]{user.getUsername(),
//                            user.getPhone(), user.getRole(), user.getPortrait()})));
                    /* todo 之后记得把这个输出注释掉 */
                    out.println(new ObjectMapper().writeValueAsString(R.ok("登录成功!", user)));
                    out.close();
//                    全局session共享的另外一种方法 但是还是决定采用老师的方法
////                    HttpSession session = req.getSession();
////                    session.setAttribute(sessionName, user);
////                    System.out.println(user);
////                    System.out.println("写入session中了！");
////                    session.setMaxInactiveInterval(60 * 60 * 24);

                })
                .failureHandler((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    R r = new R();
                    if(exception instanceof LockedException){
                        r = r.failure("账号被锁定,请联系管理员");
                    } else if(exception instanceof CredentialsExpiredException){
                        r =  r.failure("密码过期,请联系管理员");
                    } else if(exception instanceof AccountExpiredException){
                        r =  r.failure("账户过期,请联系管理员!");
                    }else if(exception instanceof BadCredentialsException){
                        r =  r.failure(R.State.ERR_RIGESTER, "用户名或密码输入,请重新输入!");
                    }
                    out.println(new ObjectMapper().writeValueAsString(r));
                    out.close();
                })
                .and()
                .logout()
//                .logoutSuccessUrl("/login.html?logout")
                .logoutUrl("/logout")
                .logoutSuccessHandler(
                        new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
                                    authentication) throws IOException, ServletException {
                                response.setContentType("application/json;charset=utf-8");
                                PrintWriter out = response.getWriter();
                                UserInfo hr = (UserInfo) authentication.getPrincipal();
                                String s = new ObjectMapper().writeValueAsString(R.ok("注销成功", null));
                                out.println(s);
                                out.close();
                            }
                        }
                );

        http.sessionManagement()
                .maximumSessions(3)
                .sessionRegistry(sessionRegistry());

    }
}
