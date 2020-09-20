package edu.kmust.bully.publish.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.exception.AnswerNotFoundException;
import edu.kmust.bully.common.exception.DeleteException;
import edu.kmust.bully.common.exception.InsertException;
import edu.kmust.bully.common.exception.PermissionDeniedException;
import edu.kmust.bully.common.dto.AnswerDTO;
import edu.kmust.bully.publish.mapper.AnswerMapper;
import edu.kmust.bully.common.model.Answer;
import edu.kmust.bully.publish.mapper.ModelMapper;
import edu.kmust.bully.publish.service.IAnswerService;
import edu.kmust.bully.common.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-10
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Answer post(AnswerDTO answersDTO, Long userId, String userName) {
        // 创建Answer对象
        Answer answer = new Answer();
        // 补全answer对象的属性值：content			<<< 参数answerDTO中的content
        answer.setContent(answersDTO.getContent());
        // 补全answer对象的属性值：count_of_likes	<<< 0
        answer.setCountOfLikes(0);
        // 补全answer对象的属性值：user_id			<<< 参数userId
        answer.setUserId(userId);
        // 补全answer对象的属性值：user_nick_name	<<< 参数userNickName
        answer.setUserName(userName);
        // 补全answer对象的属性值：model_id		<<< 参数answerDTO中的modelId
        answer.setModelId(answersDTO.getModelId());
        // 补全answer对象的属性值：created_time		<<< 当前时间
        answer.setCreatedTime(LocalDateTime.now());
        // 补全answer对象的属性值：status_of_accept	<<< 0
        answer.setStatusOfAccept(0);
        // 调用int answerMapper.insert(Answer answer)方法插入“回复”的数据，并获取返回结果
        int rows = answerMapper.insert(answer);
        // 判断返回值是否不为1
        if (rows!=1){
            // 是：抛出InsertException
            throw new InsertException("回复动态失败！服务器忙，请稍后再次尝试！");
        }
        return answer;
    }

    @Override
    public Answer delete(Integer answerId, Long userId, Integer role) {
        Answer answer = answerMapper.selectById(answerId);
        if (answer==null){
            throw new AnswerNotFoundException("该回复不存在!");
        }

        System.out.println(answer.getUserId());
        System.out.println(userId);
        if ((!answer.getUserId().equals(userId)) && role!=2){
            throw new PermissionDeniedException("删除失败,仅回复用户和管理员能够删除!");
        }
        Integer rows = answerMapper.deleteById(answerId);
        if (rows != 1){
            throw new DeleteException("删除异常，请检查数据库！");
        }
        return answer;
    }


    @Override
    public PageInfo<AnswerVO> getListByModelId(Integer modelId, Integer page) {
        // 设置分页参数
        PageHelper.startPage(page,2);
        List<AnswerVO> answers = answerMapper.findListByModelId(modelId);
        return new PageInfo<>(answers);
    }
}
