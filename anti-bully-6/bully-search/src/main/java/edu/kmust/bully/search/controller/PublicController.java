package edu.kmust.bully.search.controller;

import edu.kmust.bully.common.security.R;
import edu.kmust.bully.search.pojo.PublicModel;
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
@RequestMapping("/search/public")
@RestController
public class PublicController {
    @Autowired
    PublicSearchService searchService;

    //加这个是为了将数据库里面的信息创建索引
    @GetMapping("/index")
    public R saveAll(@RequestParam("page") Integer page) {
        searchService.publicIndex(page);
        return R.ok("索引添加成功！");
    }

    //进行搜索时候的结果返回
    @GetMapping("/searchByRequest")
    public R searchPublic(@RequestParam("request")String request) throws IOException, ParseException {
        List<PublicModel> publicModels = searchService.search(request);
        return R.ok("es搜索后得到的公共结果为：", publicModels);
    }
}
