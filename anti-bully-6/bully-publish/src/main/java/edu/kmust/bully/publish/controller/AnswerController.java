package edu.kmust.bully.publish.controller;

import edu.kmust.bully.common.security.R;
import edu.kmust.bully.common.security.UserInfo;
import edu.kmust.bully.common.dto.AnswerDTO;
import edu.kmust.bully.common.model.Answer;
import edu.kmust.bully.common.model.Comment;
import edu.kmust.bully.publish.service.IAnswerService;
import edu.kmust.bully.common.vo.AnswerVO;
import edu.kmust.bully.publish.utils.RedisForbbidenUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/publish/answers")
public class AnswerController {

    @Autowired
    private IAnswerService answerService;
    @Autowired
    RedisForbbidenUtils redisForbbidenUtils;

    @PostMapping("/post")
    public R<Answer> post(@Validated@RequestBody AnswerDTO answerDTO,
                          BindingResult bindingResult,
                          @AuthenticationPrincipal UserInfo userInfo) {
        if (redisForbbidenUtils.forbidden(userInfo.getUsername())){
            if (bindingResult.hasErrors()) {
                String message = bindingResult.getFieldError().getDefaultMessage();
            }
            Answer answer = answerService.post(answerDTO, userInfo.getId(),
                    userInfo.getUsername());
            return R.ok(answer);
        }
        return R.failure(R.State.ERR_INSERT, "该用户已经被禁言");
    }

    //获取该动态的所有评论
    @GetMapping("/model/{modelId}")
    public R<List<AnswerVO>> getListByModelId(@RequestParam("page") Integer page,
                       @PathVariable("modelId") Integer modelId){
        if (page == null || page < 1) {
            page = 1;
        }
        return R.ok(answerService.getListByModelId(modelId,page));
    }

    //删除评论
    @RequestMapping("/{answerId}/delete")
    public R<Comment> delete(@PathVariable("answerId") Integer answerId,
                             @AuthenticationPrincipal UserInfo userInfo) {
        Answer answer = answerService.delete(answerId, userInfo.getId(),
                userInfo.getRole());
        return R.ok(answer);
    }
}

