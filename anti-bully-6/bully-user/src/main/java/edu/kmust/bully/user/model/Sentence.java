package edu.kmust.bully.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 每日一句的轮换句子
 * </p>
 *
 * @author BunyAndOak0
 * @since 2020-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sentence")
public class Sentence implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每日一句的轮换句子的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 轮换句子的内容
     */
    @TableField("content")
    private String content;


}
