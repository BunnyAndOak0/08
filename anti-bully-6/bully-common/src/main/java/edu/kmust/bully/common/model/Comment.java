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
@TableName("comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 回答id
     */
    @TableField("answer_id")
    private Integer answerId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;


}
