package edu.kmust.bully.publish.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import edu.kmust.bully.common.dto.CommentDTO;
import edu.kmust.bully.common.model.Comment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-10
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 发表评论
     * @param commentDTO
     * @param userId
     * @param userName
     * @return
     */
    Comment post(CommentDTO commentDTO, Long userId, String userName);

    /**
     *
     * @param commentId
     * @param userId
     * @param role
     * @return
     */
    Comment delete(Integer commentId,Long userId,Integer role);

    /**
     *
     * @param answerId
     * @return 评论该评论的列表
     */
    PageInfo<Comment> getCommentByAnswerId(Integer answerId, Integer page);
}
