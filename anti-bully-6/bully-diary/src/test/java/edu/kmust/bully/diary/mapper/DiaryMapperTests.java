package edu.kmust.bully.diary.mapper;


import edu.kmust.bully.common.exception.InsertException;
import edu.kmust.bully.diary.DTO.DiaryDTO;
import edu.kmust.bully.diary.model.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class DiaryMapperTests {
    @Autowired
    DiaryMapper diaryMapper;

    @Test
    void insert(){
        Diary diary = new Diary()
                .setDiaryContent("第一个日记!")
                .setDiaryCreatetime(LocalDateTime.now())
                .setUserId(1)
                .setDiaryTitle("新人报道")
                .setDiaryIsdelete(0);
        diaryMapper.insert(diary);
    }

    @Test
    void deleteDiaryById(){
        diaryMapper.deleteDiaryById(1);
    }

    @Test
    void findAllDiary(){
        List<Diary> diarys = diaryMapper.findAllDiary();
        for (Diary diary : diarys) {
            System.err.println(diary);
        }
    }

//    @Test
//    void updateDiaryByTitle(){
//        DiaryDTO diaryDTO = new DiaryDTO()
//                .setDiaryId(8)
//                .setDiaryContent("我修改日记了")
//                .setDiaryTitle("第二次写日记");
//        diaryMapper.updateDiaryById(diaryDTO);
//    }

    @Test
    void a(){
        Throwable e = new InsertException("添加失败");
        System.err.println(e.getClass());
    }
}
