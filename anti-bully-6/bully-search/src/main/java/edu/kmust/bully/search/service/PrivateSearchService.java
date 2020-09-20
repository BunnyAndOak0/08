package edu.kmust.bully.search.service;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.search.feign.PrivateClient;
import edu.kmust.bully.search.pojo.PrivateModel;
import edu.kmust.bully.search.pojo.PublicModel;
import edu.kmust.bully.search.respository.PrivateModelRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/31 19:00
 **/
@Service
public class PrivateSearchService {
    @Autowired
    PrivateModelRepository privateModelRepository;
    @Autowired
    PrivateClient privateClient;
    @Resource
    RestHighLevelClient restHighLevelClient;

    public void privateIndex(Integer page) {
        List<Model> models = privateClient.privateFeignAll(page);
        if (models == null) {
            return;
        }

        PrivateModel model = new PrivateModel();
        for (Model privateModel : models) {
            model.setId(privateModel.getId().longValue());
            model.setUserId(privateModel.getUserId());
            model.setContent(privateModel.getContent());
            model.setTitle(privateModel.getTitle());
            model.setUsername(privateModel.getUsername());
            model.setImage(privateModel.getImage());
            model.setCreateTime(privateModel.getCreateTime());
            privateModelRepository.save(model);
        }
    }

    public void deletePrivate(Long id){
        privateModelRepository.deleteById(id);
    }

    public void save(Long id){
        R<Model> model = privateClient.getModelById(id.intValue());
        Model data = model.getData();
        PrivateModel privateModel = new PrivateModel();
        privateModel.setId(data.getId().longValue());
        privateModel.setUserId(data.getUserId());
        privateModel.setUsername(data.getUsername());
        privateModel.setTitle(data.getTitle());
        privateModel.setContent(data.getContent());
        privateModel.setImage(data.getImage());
        privateModel.setCreateTime(data.getCreateTime());
        privateModelRepository.save(privateModel);
        System.out.println(privateModel);
        System.out.println("私有存储成功！");
    }

    public List<PrivateModel> search(String request) throws IOException, ParseException {
        //创建搜索请求
        SearchRequest searchRequest = new SearchRequest("private");
        //创建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        List<PrivateModel> privateModels = new ArrayList<>();
//        searchSourceBuilder.query(QueryBuilders.termQuery("content", request))
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(request, "title", "content", "username"))
                .from(0)
                .size(1000)
                .highlighter(new HighlightBuilder().field("*").requireFieldMatch(false)
                        .preTags("<span style='color:red'>").postTags("</span>"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //原始文档
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits){
//            System.out.println(hit);
            Map<String, Object> maps = hit.getSourceAsMap();
            PrivateModel privateModel = new PrivateModel();
            privateModel.setId(Long.parseLong(hit.getId()));
            System.out.println(maps);
            System.out.println(maps.get("userId").toString());
            privateModel.setUserId(Long.parseLong(maps.get("userId").toString()));
            privateModel.setUsername(maps.get("username").toString());
            privateModel.setTitle(maps.get("title").toString());
            privateModel.setContent(maps.get("content").toString());
//            publicModel.setImage(maps.get("image").toString());
//            publicModel.setCreateTime(LocalDateTime.parse((CharSequence) maps.get("createTime"),
//                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            //高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("title")){
                privateModel.setTitle(highlightFields.get("title").fragments()[0].toString());
            }
            if (highlightFields.containsKey("username")){
                privateModel.setUsername(highlightFields.get("username").fragments()[0].toString());
            }
            if (highlightFields.containsKey("content")){
                privateModel.setContent(highlightFields.get("content").fragments()[0].toString());
            }
            privateModels.add(privateModel);
        }
        return privateModels;
    }
}
