package edu.kmust.bully.search.respository;

import edu.kmust.bully.search.pojo.PublicModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author BunnyAndOak0
 * @Description 继承之后已经提供了增删改查的方法
 * @Date 2020/8/28 12:53
 **/
public interface PublicModelRepository extends ElasticsearchRepository<PublicModel, Long> {
}
