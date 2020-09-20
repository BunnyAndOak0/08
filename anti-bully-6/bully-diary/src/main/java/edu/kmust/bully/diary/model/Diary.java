package edu.kmust.bully.diary.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yym
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("diary")
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Diary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日记的唯一id
     */
    @TableId(value = "diary_id", type = IdType.AUTO)
    private Integer diaryId;

    /**
     * 发表日记的用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 发表的日记的主题，不可为空
     */
    @TableField("diary_title")
    private String diaryTitle;

    /**
     * 日记内容可以为空
     */
    @TableField("diary_content")
    private String diaryContent;

    /**
     * 日记的创建的时间，不可为空
     */

    @TableField("diary_createtime")
    private LocalDateTime diaryCreatetime;

    /**
     * 日记的最新修改时间，可以为空
     */

    @TableField("diary_updatetime")
    private LocalDateTime diaryUpdatetime;

    /**
     * 查看日记是否删除，0为默认值不删除，1为删除
     */
    @TableField("diary_isdelete")
    private Integer diaryIsdelete;


}
