package edu.kmust.bully.search;

import edu.kmust.bully.search.pojo.PublicModel;
import edu.kmust.bully.search.respository.PublicModelRepository;
import edu.kmust.bully.search.service.PublicSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/28 13:00
 **/
@SpringBootTest
public class TestPpublicModelRepository {
    @Autowired
    private PublicModelRepository publicModelRepository;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    //保存 | 更新 id存在更新 不存在就添加
    @Test
    public void testSave(){
        PublicModel publicModel = new PublicModel();

        publicModel.setId(1l);
        publicModel.setContent("这是一个公开的模块动态");
        publicModel.setCreateTime(LocalDateTime.now());
        publicModel.setImage("123456.jpg");
        publicModel.setUsername("alice");
        publicModel.setTitle("公开模块标题");
        publicModel.setUserId(10l);

        publicModelRepository.save(publicModel);
    }

    @Test
    public void testDelete(){
        publicModelRepository.deleteById(1l);
    }

    //检索一条记录
    @Test
    public void testFindOne(){
        Optional<PublicModel> publicModel = publicModelRepository.findById(1l);
        System.out.println(publicModel);
    }

    //查询所有
    @Test
    public void testFindAll(){
        Iterable<PublicModel> all = publicModelRepository.findAll();
        all.forEach(publicModel ->
                System.out.println(publicModel));
    }

    //用restHighLevelClient可以完成高亮查询
    @Test
    public void testSearchQuery() throws IOException {
        //创建搜索请求
        SearchRequest searchRequest = new SearchRequest("public");
        //创建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //将原始文档 封装到对象中进行返回
        List<PublicModel> publicModels = new ArrayList<>();
        //设置查询条件
        searchSourceBuilder.query(QueryBuilders.termQuery("title", "公开"))
//        .sort()
            .from(0)   //分页   起始页数（当前页-1）*size的值
            .size(20)  //每页展示的条数
                //高亮处理  requireFieldMatch表示是否匹配字段都高亮处理
            .highlighter(new HighlightBuilder().field("*").requireFieldMatch(false)
                    .preTags("<span style='color:red'>").postTags("</span>"));
        ;
        //执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits){
            //打印原始文档
            System.out.println(hit);
            Map<String, Object> maps = hit.getSourceAsMap();
            PublicModel publicModel = new PublicModel();
            publicModel.setId(Long.parseLong(hit.getId()));
            publicModel.setUserId(Long.parseLong(maps.get("userId").toString()));
            publicModel.setUsername(maps.get("username").toString());
            publicModel.setTitle(maps.get("title").toString());
            publicModel.setContent(maps.get("content").toString());
//            publicModel.setImage(maps.get("image").toString());
//            System.out.println(publicModel);
            //高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("title")){
                publicModel.setTitle(highlightFields.get("title").fragments()[0].toString());
            }
            if (highlightFields.containsKey("content")){
                publicModel.setContent(highlightFields.get("content").fragments()[0].toString());
            }
            publicModels.add(publicModel);
        }
        publicModels.forEach(publicModel -> System.out.println(publicModel));
    }

    @Autowired
    PublicSearchService publicSearchService;

    @Test
    public void Test1() throws IOException, ParseException {
        List<PublicModel> publicModels = publicSearchService.search("今天");
        publicModels.forEach(publicModel -> {
            System.out.println(publicModel);
        });

    }

}

