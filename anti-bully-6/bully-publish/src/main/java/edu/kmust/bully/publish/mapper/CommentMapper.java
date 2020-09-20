package edu.kmust.bully.publish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.kmust.bully.common.model.Comment;
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
public interface CommentMapper extends BaseMapper<Comment> {
    Integer deleteById(Integer id);

    List<Comment> findByAnswerId(Integer id);
}
