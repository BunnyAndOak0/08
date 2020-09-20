package edu.kmust.bully.search.service;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.search.feign.SoulClient;
import edu.kmust.bully.search.pojo.PublicModel;
import edu.kmust.bully.search.pojo.SoulModel;
import edu.kmust.bully.search.respository.SoulModelRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/9/3 12:54
 **/
@Service
public class SoulSearchService {

    @Autowired
    SoulClient soulClient;
    @Autowired
    SoulModelRepository soulModelRepository;
    @Resource
    RestHighLevelClient restHighLevelClient;

    public void soulIndex(Integer page) {
        List<Model> models = soulClient.soulFeignAll(page);
        if (models == null) {
            return;
        }

        SoulModel soulModel = new SoulModel();
        for (Model model : models) {
            soulModel.setId(model.getId().longValue());
            soulModel.setUserId(model.getUserId());
            soulModel.setContent(model.getContent());
            soulModel.setTitle(model.getTitle());
            soulModel.setUsername(model.getUsername());
            soulModel.setImage(model.getImage());
            soulModel.setCreateTime(model.getCreateTime());
            soulModelRepository.save(soulModel);
        }
    }

    public void deleteSoul(Long id){
        soulModelRepository.deleteById(id);
    }

    public void save(Long id){
        R<Model> model = soulClient.getModelById(id.intValue());

        Model data = model.getData();
        SoulModel soulModel = new SoulModel();
        soulModel.setId(data.getId().longValue());
        soulModel.setUserId(data.getUserId());
        soulModel.setUsername(data.getUsername());
        soulModel.setTitle(data.getTitle());
        soulModel.setContent(data.getContent());
        soulModel.setImage(data.getImage());
        soulModel.setCreateTime(data.getCreateTime());

        soulModelRepository.save(soulModel);
        System.out.println(soulModel);
        System.out.println("辅导区存储成功！");
    }

    public List<SoulModel> search(String request) throws IOException {
        SearchRequest searchRequest = new SearchRequest("soul");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        List<SoulModel> soulModels = new ArrayList<>();

        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(request,
                "title", "content", "username"))
                .from(0)
                .size(1000)
                .highlighter(new HighlightBuilder().field("*").requireFieldMatch(false)
                .preTags("<span style='color:red'>").postTags("</span>"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.
                search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits){
            Map<String, Object> maps = hit.getSourceAsMap();
            SoulModel soulModel = new SoulModel();
            soulModel.setId(Long.parseLong(hit.getId()));
            System.out.println(maps);
            System.out.println(maps.get("userId").toString());
            soulModel.setUserId(Long.parseLong(maps.get("userId").toString()));
            soulModel.setUsername(maps.get("username").toString());
            soulModel.setTitle(maps.get("title").toString());
            soulModel.setContent(maps.get("content").toString());
            //高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("title")){
                soulModel.setTitle(highlightFields.get("title").fragments()[0].toString());
            }
            if (highlightFields.containsKey("username")){
                soulModel.setContent(highlightFields.get("username").fragments()[0].toString());
            }
            if (highlightFields.containsKey("content")){
                soulModel.setContent(highlightFields.get("content").toString());
            }
            soulModels.add(soulModel);
        }
        return soulModels;
    }

}
