package edu.kmust.bully.publish.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.dto.AnswerDTO;
import edu.kmust.bully.common.model.Answer;
import edu.kmust.bully.common.vo.AnswerVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-10
 */
public interface IAnswerService extends IService<Answer> {
    /**
     * 提交动态回复
     * .
     * @param answersDTO
     * @param userId
     * @param userName
     */
    Answer post(AnswerDTO answersDTO, Long userId, String userName);


    Answer delete(Integer answerId,Long userId,Integer role);

    /**
     * 通过动态Id查询回复列表
     * @param modelId
     * @return
     */
    PageInfo<AnswerVO> getListByModelId(Integer modelId, Integer page);

}
