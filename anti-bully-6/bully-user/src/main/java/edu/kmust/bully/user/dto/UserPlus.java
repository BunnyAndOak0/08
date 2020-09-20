package edu.kmust.bully.user.dto;

import edu.kmust.bully.common.security.UserInfo;
import lombok.Data;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/18 11:47
 **/
@Data
public class UserPlus extends UserInfo {
    String sentence;
    String birthday;

    @Override
    public String toString() {
        return "UserPlus{" +
                "sentence='" + sentence + '\'' +
                ", birthday='" + birthday + '\'' +
                "} " + super.toString();
    }
}
