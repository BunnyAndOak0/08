package edu.kmust.bully.diary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.kmust.bully.common.exception.DeleteException;
import edu.kmust.bully.common.exception.InsertException;
import edu.kmust.bully.diary.DTO.DiaryDTO;
import edu.kmust.bully.diary.mapper.DiaryMapper;
import edu.kmust.bully.diary.model.Diary;
import edu.kmust.bully.diary.service.IDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yym
 * @since 2020-08-10
 */
@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements IDiaryService {

    @Autowired
    DiaryMapper diaryMapper;
    @Override
    public void inserDiary(DiaryDTO diaryDTO, Integer id) {
        Diary diary = new Diary()
                .setDiaryContent(diaryDTO.getDiaryContent())
                .setUserId(id)
                .setDiaryIsdelete(0)
                .setDiaryTitle(diaryDTO.getDiaryTitle())
                .setDiaryCreatetime(LocalDateTime.now());

        int row = diaryMapper.insert(diary);

        if(row != 1){
            throw new InsertException("发布失败,请稍后再试（´Д`）");
        }

    }

    @Override
    public Diary deletDiaryById(Integer id) {
        Diary diary = diaryMapper.selectById(id);
        if(diary == null){
            throw new DeleteException("删除失败,请稍后再试（´Д`）");
        }
        Integer row = diaryMapper.deleteDiaryById(id);

        if(row != 1){
            throw new DeleteException("删除失败,请稍后再试（´Д`）");
        }
        return diary;
    }

    @Override
    public List<Diary> getAllDiary() {

        List<Diary> diarys = diaryMapper.findAllDiary();
        return diarys;
    }
    @Override
    public void updateDiaryById(DiaryDTO diaryDTO,String diaryId) {
        Diary diary = new Diary()
                .setDiaryContent(diaryDTO.getDiaryContent())
                .setDiaryTitle(diaryDTO.getDiaryTitle())
                .setDiaryUpdatetime(LocalDateTime.now())
                .setDiaryId(Integer.parseInt(diaryId));
        Integer row = diaryMapper.updateDiaryById(diary);

        if(row != 1){
            throw new DeleteException("更新失败,请稍后再试（´Д`）");
        }
    }

    @Override
    public Diary getDiaryById(String diaryId) {
        Diary diary = diaryMapper.selectById(diaryId);

        return diary;
    }
}
