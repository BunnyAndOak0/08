package edu.kmust.bully.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.kmust.bully.user.mapper.SentenceMapper;
import edu.kmust.bully.user.model.Sentence;
import edu.kmust.bully.user.service.ISentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 每日一句的轮换句子 服务实现类
 * </p>
 *
 * @author BunyAndOak0
 * @since 2020-08-18
 */
@Service
public class SentenceServiceImpl extends ServiceImpl<SentenceMapper, Sentence> implements ISentenceService {
    @Autowired
    SentenceMapper sentenceMapper;

    public String getSentence(){
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        //根据星期推送每日一句
        QueryWrapper<Sentence> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", day);
        Sentence sentence = sentenceMapper.selectOne(queryWrapper);
        return sentence.getContent();
    }
}
