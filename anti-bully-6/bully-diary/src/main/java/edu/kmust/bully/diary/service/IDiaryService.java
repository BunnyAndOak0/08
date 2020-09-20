package edu.kmust.bully.diary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.kmust.bully.diary.DTO.DiaryDTO;
import edu.kmust.bully.diary.model.Diary;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yym
 * @since 2020-08-10
 */
public interface IDiaryService extends IService<Diary> {
    /**
     * 发布日记
     * @param diary
     * @param id 当前用户ID
     */
    void inserDiary(DiaryDTO diary, Integer id);

    /**
     * 删除日记
     * @param id 日记ID
     * @return
     */
    Diary deletDiaryById(Integer id);

    /**
     * 获取所有日记
     * @return
     */
    List<Diary> getAllDiary();

    /**
     * 更新日记
     * @param diaryDTO
     */
    void updateDiaryById(DiaryDTO diaryDTO, String diaryId);

    /**
     * 查找单个日记
     * @param diaryId
     * @return
     */
    Diary getDiaryById(String diaryId);
}
