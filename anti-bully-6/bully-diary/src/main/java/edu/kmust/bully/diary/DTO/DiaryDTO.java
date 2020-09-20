package edu.kmust.bully.diary.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DiaryDTO {
//    private Integer diaryId;
    private String diaryTitle;
    private String diaryContent;
}
