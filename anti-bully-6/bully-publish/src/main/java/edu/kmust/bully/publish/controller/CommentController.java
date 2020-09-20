package edu.kmust.bully.publish.controller;

import edu.kmust.bully.common.security.R;
import edu.kmust.bully.common.security.UserInfo;
import edu.kmust.bully.common.dto.CommentDTO;
import edu.kmust.bully.common.model.Comment;
import edu.kmust.bully.publish.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/publish/comments")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    // http://localhost:20000//publish/comments/post?answerId=4&content=Comment---2
    @RequestMapping("/post")
    public R<Comment> post(CommentDTO commentDTO,
                           @AuthenticationPrincipal UserInfo userInfo) {
        Comment comment = commentService.post(commentDTO, userInfo.getId(), userInfo.getUsername());
        return R.ok(comment);
    }

    @GetMapping("/answerId/{answerId}")
    public R<List<Comment>> getListByAnswerId(Integer page, @PathVariable("answerId") Integer answerId){
        return R.ok(commentService.getCommentByAnswerId(answerId,page));

    }

    @RequestMapping("/{commentId}/delete")
    public R<Comment> delete(@PathVariable("commentId") Integer commentId,
                             @AuthenticationPrincipal UserInfo userInfo) {
        Comment comment = commentService.delete(commentId, userInfo.getId(), userInfo.getRole());
        return R.ok(comment);
    }
}
