package edu.kmust.bully.publish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.kmust.bully.common.model.Answer;
import edu.kmust.bully.common.vo.AnswerVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-10
 */
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {
    /**
     * 查询回复的信息以及评论
     * @param modelId
     * @return 回复信息以及评论
     */
    List<AnswerVO> findListByModelId(Integer modelId);

    Integer deleteById(Integer id);
}
