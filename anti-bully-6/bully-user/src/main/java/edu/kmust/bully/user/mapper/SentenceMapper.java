package edu.kmust.bully.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.kmust.bully.user.model.Sentence;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 每日一句的轮换句子 Mapper 接口
 * </p>
 *
 * @author BunyAndOak0
 * @since 2020-08-18
 */
@Repository
public interface SentenceMapper extends BaseMapper<Sentence> {

}
