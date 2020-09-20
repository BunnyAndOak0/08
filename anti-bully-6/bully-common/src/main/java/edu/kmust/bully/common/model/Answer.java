package edu.kmust.bully.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 回复内容
     */
    @TableField("content")
    private String content;

    /**
     * 点赞数量
     */
    @TableField("count_of_likes")
    private Integer countOfLikes;

    /**
     * 回复动态的用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 回复者用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 对应的问题id
     */
    @TableField("model_id")
    private Integer modelId;

    /**
     * 回复时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;

    /**
     * 是否采纳
     */
    @TableField("status_of_accept")
    private Integer statusOfAccept;


}
