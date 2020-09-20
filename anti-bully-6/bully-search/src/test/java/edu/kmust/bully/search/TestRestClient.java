package edu.kmust.bully.search;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/27 22:54
 **/
@SpringBootTest
public class TestRestClient {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest();
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }
}
