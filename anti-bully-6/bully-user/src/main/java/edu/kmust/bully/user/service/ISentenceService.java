package edu.kmust.bully.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.kmust.bully.user.model.Sentence;

/**
 * <p>
 * 每日一句的轮换句子 服务类
 * </p>
 *
 * @author BunyAndOak0
 * @since 2020-08-18
 */
public interface ISentenceService extends IService<Sentence> {
    String getSentence();
}
