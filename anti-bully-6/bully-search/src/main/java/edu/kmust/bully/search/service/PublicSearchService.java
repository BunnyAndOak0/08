package edu.kmust.bully.search.service;

import edu.kmust.bully.common.model.Model;
import edu.kmust.bully.common.security.R;
import edu.kmust.bully.search.feign.PublicClient;
import edu.kmust.bully.search.pojo.PublicModel;
import edu.kmust.bully.search.respository.PublicModelRepository;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/31 14:46
 **/
@Service
public class PublicSearchService {
    @Autowired
    PublicModelRepository publicModelRepository;
    @Autowired
    PublicClient publicClient;
    @Resource
    RestHighLevelClient restHighLevelClient;

    public void publicIndex(Integer page) {
        List<Model> models = publicClient.publicFeignAllModel(page);
        if (models == null) {
            return;
        }

        PublicModel publicModel = new PublicModel();
        for (Model model : models) {
            publicModel.setId(model.getId().longValue());
            publicModel.setUserId(model.getUserId());
            publicModel.setContent(model.getContent());
            publicModel.setTitle(model.getTitle());
            publicModel.setUsername(model.getUsername());
            publicModel.setImage(model.getImage());
            publicModel.setCreateTime(model.getCreateTime());
            publicModelRepository.save(publicModel);
        }
    }

    public void deletePublic(Long id){
        publicModelRepository.deleteById(id);
    }

    public void save(Long id){
        R<Model> model = publicClient.getModelById(id.intValue());
        Model data = model.getData();
        PublicModel publicModel = new PublicModel();
        publicModel.setId(data.getId().longValue());
        publicModel.setUserId(data.getUserId());
        publicModel.setUsername(data.getUsername());
        publicModel.setTitle(data.getTitle());
        publicModel.setContent(data.getContent());
        publicModel.setImage(data.getImage());
        publicModel.setCreateTime(data.getCreateTime());
        publicModelRepository.save(publicModel);
        System.out.println(publicModel);
        System.out.println("进行储存成功！");
    }

    public List<PublicModel> search(String request) throws IOException, ParseException {
        //创建搜索请求
        SearchRequest searchRequest = new SearchRequest("public");
        //创建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        List<PublicModel> publicModels = new ArrayList<>();
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
            PublicModel publicModel = new PublicModel();
            publicModel.setId(Long.parseLong(hit.getId()));
            publicModel.setUserId(Long.parseLong(maps.get("userId").toString()));
            publicModel.setUsername(maps.get("username").toString());
            publicModel.setTitle(maps.get("title").toString());
            publicModel.setContent(maps.get("content").toString());
            //todo 时间这个问题还没有解决
//            publicModel.setImage(maps.get("image").toString());
//            publicModel.setCreateTime(LocalDateTime.parse((CharSequence) maps.get("createTime"),
//                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            //高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("title")){
                publicModel.setTitle(highlightFields.get("title").fragments()[0].toString());
            }
            if (highlightFields.containsKey("username")){
                publicModel.setUsername(highlightFields.get("username").fragments()[0].toString());
            }
            if (highlightFields.containsKey("content")){
                publicModel.setContent(highlightFields.get("content").fragments()[0].toString());
            }
            publicModels.add(publicModel);
        }
        return publicModels;
    }
}
