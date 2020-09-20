package edu.kmust.bully.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain=true)
@Data
public class CommentDTO {
    private Integer answerId;
    private String content;
}
