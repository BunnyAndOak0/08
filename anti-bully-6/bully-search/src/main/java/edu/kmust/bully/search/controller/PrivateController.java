package edu.kmust.bully.search.controller;

import edu.kmust.bully.common.security.R;
import edu.kmust.bully.search.pojo.PrivateModel;
import edu.kmust.bully.search.pojo.PublicModel;
import edu.kmust.bully.search.service.PrivateSearchService;
import edu.kmust.bully.search.service.PublicSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @Author BunnyAndOak0
 * @Description 得到所有动态之后建立索引
 * @Date 2020/8/31 7:50
 **/
@RequestMapping("/search/private")
@RestController
public class PrivateController {
    @Autowired
    PrivateSearchService privateSearchService;

    @GetMapping("/index")
    public R saveAll(@RequestParam("page") Integer page) {
        privateSearchService.privateIndex(page);
        return R.ok("索引添加成功！");
    }

    @GetMapping("/searchByRequest")
    public R searchPublic(@RequestParam("request")String request) throws IOException, ParseException {
        List<PrivateModel> publicModels = privateSearchService.search(request);
        return R.ok("es搜索后得到的公共结果为：", publicModels);
    }
}
