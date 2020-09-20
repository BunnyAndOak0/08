package edu.kmust.bully.search.controller;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.search.feign.SoulClient;
import edu.kmust.bully.search.pojo.PublicModel;
import edu.kmust.bully.search.pojo.SoulModel;
import edu.kmust.bully.search.respository.SoulModelRepository;
import edu.kmust.bully.search.service.SoulSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/9/3 12:53
 **/
@RestController
@RequestMapping("/search/soul")
public class SoulController {

    @Autowired
    SoulSearchService soulSearchService;


    @GetMapping("/index")
    public R saveAll(@RequestParam("page") Integer page){
        soulSearchService.soulIndex(page);
        return R.ok("索引添加成功！");
    }

    //进行搜索时候的结果返回
    @GetMapping("/searchByRequest")
    public R searchPublic(@RequestParam("request")String request) throws IOException, ParseException {
        List<SoulModel> soulModels = soulSearchService.search(request);
        return R.ok("es搜索后得到的公共结果为：", soulModels);
    }
}
