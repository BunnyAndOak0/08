package edu.kmust.bully.diary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.kmust.bully.diary.model.Diary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yym
 * @since 2020-08-10
 */
@Repository
public interface DiaryMapper extends BaseMapper<Diary> {
    /**
     * 根据ID删除日记
     * @param diaryId
     * @return
     */
    Integer deleteDiaryById(Integer diaryId);

    /**
     * 查找所有日记
     * @return
     */
    List<Diary> findAllDiary();

    /**
     * 更新日记
     * @param diaryDTO
     * @return
     */
    Integer updateDiaryById(Diary diary);
}
