package edu.kmust.bully.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用于注册登录的基本信息的存储
 * </p>
 *
 * @author BunyAndOak0
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户注册的编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 注册的用户名，不可以重复
     */
    @TableField("username")
    private String username;

    /**
     * 登陆密码
     */
    @TableField("password")
    private String password;

    /**
     * 用于实名验证或者忘记密码时的验证
     */
    @TableField("phone")
    private String phone;

    /**
     * 存储头像的图片地址，可以为空
     */
    @TableField("portrait")
    private String portrait;

    /**
     * 用于查看该用户是否被禁言，举报三次被禁言，每次举报加一，为三时不能再发表评论和动态，初始都为0
     */
    @TableField("forbidden")
    private Integer forbidden;

    /**
     * 角色分为0，1，2，三档，0为普通用户，1为辅导专区人员，2为官方帐户，注册的时候做区分
     */
    @TableField("role")
    private Integer role;

    /**
     * 不可为空 默认为创建时间
     */
    @TableField("birth")
    private LocalDateTime birth;

    /**
     * 不可为空 1为男 0为女 未知为2
     */
    @TableField("sex")
    private Integer sex;

    /*
     * @Author BunnyAndOak0
     * @Description 默认值为未知
     **/
    @TableField("location")
    private String location;
}
