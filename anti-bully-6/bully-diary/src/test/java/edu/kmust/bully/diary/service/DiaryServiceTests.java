package edu.kmust.bully.diary.service;

import edu.kmust.bully.diary.DTO.DiaryDTO;
import edu.kmust.bully.diary.model.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiaryServiceTests {
    @Autowired
    IDiaryService diaryService;

    @Test
    void inserDiary(){
        DiaryDTO diaryDTO = new DiaryDTO()
                .setDiaryContent("第二个日记")
                .setDiaryTitle("第二次写日记");
        diaryService.inserDiary(diaryDTO,1);
    }

    @Test
    void deletDiaryById(){
        Diary diary = diaryService.deletDiaryById(10);
        System.err.println(diary);
    }

//    @Test
//    void updateDiaryByTitle(){
//        DiaryDTO diaryDTO = new DiaryDTO()
//                .setDiaryTitle("第二次写日记")
//                .setDiaryContent("我修改日记了")
//                .setDiaryId(11);
//        diaryService.updateDiaryById(diaryDTO);
//    }
}
