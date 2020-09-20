package edu.kmust.bully.diary.controller;


import edu.kmust.bully.common.security.UserInfo;
import edu.kmust.bully.diary.DTO.DiaryDTO;
import edu.kmust.bully.diary.model.Diary;
import edu.kmust.bully.diary.service.IDiaryService;
import edu.kmust.bully.diary.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yym
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/diary")
public class DiaryController {
    //localhost:8080/portal/diary/
    @Autowired
    IDiaryService diaryService;
    @PostMapping("/write")
    public R inserDiary(@RequestBody DiaryDTO diaryDTO, @AuthenticationPrincipal UserInfo user){
        System.out.println("DiaryController.inserDiary");
        System.err.println(diaryDTO);
        diaryService.inserDiary(diaryDTO, user.getId().intValue());
        return R.ok("发布成功!");
    }

    @GetMapping("/{diaryId}/delete")
    public R deleteDiary(@PathVariable String diaryId){
        System.out.println("DiaryController.deleteDiary");

        Diary diary = diaryService.deletDiaryById(Integer.parseInt(diaryId));
        return R.ok("删除成功!", diary);
    }

    @GetMapping("/")
    public R getAllDiary(){
        List<Diary> diarys = diaryService.getAllDiary();
        System.err.println(diarys.size());
        return R.ok(diarys);
    }

    @PostMapping("/{diaryId}/update")
    public R update(@PathVariable String diaryId, @RequestBody  DiaryDTO diaryDTO){
        System.err.println("diaryDTO:"+diaryDTO);
        System.err.println("删除ID:"+diaryId);
        diaryService.updateDiaryById(diaryDTO,diaryId);
        return R.ok("更新成功!");
    }
    //localhost:8081/portal/diary/10/details
    @GetMapping("/{diaryId}/details")
    public R findDiary(@PathVariable  String diaryId){
        System.err.println(diaryId);
        Diary diary = diaryService.getDiaryById(diaryId);
        System.err.println("diart="+diary);
        return R.ok(diary);
    }
}
