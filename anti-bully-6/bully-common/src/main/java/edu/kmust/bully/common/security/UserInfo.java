package edu.kmust.bully.common.security;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/14 15:44
 **/
@Data
public class UserInfo implements UserDetails {
    private static final long serialVersionUID = 7985288215517245410L;

    private Long id;
    private String username;
    private String password;
    private String phone;
    private Integer role;
    private String portrait;
    private LocalDateTime birth;
    private Integer sex;
    private String location;

    /**
     * 用户权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 账户是否没有过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否没有被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否没有过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否没有被锁定
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", portrait='" + portrait + '\'' +
                ", birth=" + birth +
                ", sex=" + sex +
                ", location='" + location + '\'' +
                '}';
    }
}
