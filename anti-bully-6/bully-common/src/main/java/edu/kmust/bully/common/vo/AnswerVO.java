package edu.kmust.bully.common.vo;

import edu.kmust.bully.common.model.Comment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class AnswerVO {
    private Integer id;
    private String content;
    private Integer countOfLikes;
    private Integer userId;
    private String userName;
    private Integer modelId;
    private LocalDateTime createdTime;
    private Integer statusOfAccept;
    private List<Comment> comments;
}
