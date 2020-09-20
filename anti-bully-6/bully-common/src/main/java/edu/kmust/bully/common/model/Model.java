package edu.kmust.bully.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公开动态的数据存储
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("model")
@Accessors(chain = true)
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用于公开的动态信息的存储，唯一的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发布动态的用户的用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 发布动态的作者
     */
    @TableField("username")
    private String username;

    /**
     * 动态的标题
     */
    @TableField("title")
    private String title;

    /**
     * 用户的发布内容的存储
     */
    @TableField("content")
    private String content;

    /**
     * 内容含有的图片的地址，没有的话可以为空
     */
    @TableField("image")
    private String image;

    /**
     * 发表的动态的时间
            
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 是否删除该消息，逻辑删除，0为默认值不删除，1为删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 该问题是否公开，所有学生都可见，0-》否，1-》是
     */
    @TableField("is_public")
    private Integer isPublic;

    /**
     * 发布该问题是否匿名
     */
    @TableField("is_anonymousness")
    private Integer isAnonymousness;

    /**
     * 发布该问题是否是在心灵辅导专区
     */
    @TableField("is_soul")
    private Integer isSoul;

    /**
     * 公开动态的点赞人数，一开始的时候可以为空
     */
    @TableField("likes")
    private Integer likes;
}
