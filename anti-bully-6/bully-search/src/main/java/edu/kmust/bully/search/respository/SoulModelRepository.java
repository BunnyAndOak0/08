package edu.kmust.bully.search.respository;

import edu.kmust.bully.search.pojo.SoulModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/9/3 12:53
 **/
public interface SoulModelRepository extends ElasticsearchRepository<SoulModel, Long> {
}
