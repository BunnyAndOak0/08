package edu.kmust.bully.publish.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.exception.CommentNotFoundException;
import edu.kmust.bully.common.exception.DeleteException;
import edu.kmust.bully.common.exception.InsertException;
import edu.kmust.bully.common.exception.PermissionDeniedException;
import edu.kmust.bully.common.dto.CommentDTO;
import edu.kmust.bully.publish.mapper.CommentMapper;
import edu.kmust.bully.common.model.Comment;
import edu.kmust.bully.publish.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Comment post(CommentDTO commentDTO, Long userId, String userName) {
        //创建Commen对象
        Comment comment = new Comment();
        // 向Comment对象中封装数据：user_id			>>> 参数userId
        comment.setUserId(userId);
        // 向Comment对象中封装数据：user_nick_name	>>> 参数userNickName
        comment.setUserName(userName);
        // 向Comment对象中封装数据：answer_id		>>> commentDTO
        comment.setAnswerId(commentDTO.getAnswerId());
        // 向Comment对象中封装数据：content			>>> commentDTO
        comment.setContent(commentDTO.getContent());
        // 向Comment对象中封装数据：created_time		>>> 创建当前时间对象
        comment.setCreatedTime(LocalDateTime.now());
        // 调用int commentMapper.insert(Comment comment)方法插入评论数据，获取返回的受影响行数
        int rows = commentMapper.insert(comment);
        // 判断返回值是否不为1
        if (rows!=1) {

            // 是：抛出InsertException
            throw new InsertException("服务器忙!评论失败,请稍后再试!");
        }
        // 返回Comment对象
        return comment;
    }

    @Override
    public Comment delete(Integer commentId, Long userId, Integer role) {
        // 根据参数commentId调用mapper.selectById()查询被删除的“评论”的信息
        Comment comment = commentMapper.selectById(commentId);
        // 判断查询结果是否为null
        if (comment==null){
            // 是：该“评论”不存在，抛出CommentNotFoundException异常
            throw new CommentNotFoundException("该评论不存在");
        }

        // 基于查询结果中的userId，结合参数userId，判断查询结果数据是否是当前登录用户的，

        // 或基于参数userType，判断当前登录的用户的身份是“老师”，
        // 如果这2个条件都不符合，则不允许删除，抛出PermissionDeniedException
        if(!comment.getUserId().equals(userId) && role !=2){
            throw new PermissionDeniedException("删除失败!仅发布者和官方人员可以删除该评论");

        }
        // 根据参数commentId调用mapper.deleteById()执行删除，并获取返回的受影响行数
        int rows = commentMapper.deleteById(commentId);
        // 判断返回值是否不为1
        if (rows!=1) {
            // 是：抛出DeleteException
            throw new DeleteException("删除评论失败！删除时出现未知错误，请联系系统管理员！");

        }
        // 返回查询结果
        return comment;
    }

    /**
     * 通过回复的Id查询该回复的评论列表
     * @param answerId
     * @return 该回复的评论列表
     */
    @Override
    public PageInfo<Comment> getCommentByAnswerId(Integer answerId,Integer page) {
        PageHelper.startPage(page,10);
        List<Comment> comments = commentMapper.findByAnswerId(answerId);
        return new PageInfo<>(comments);
    }
}
