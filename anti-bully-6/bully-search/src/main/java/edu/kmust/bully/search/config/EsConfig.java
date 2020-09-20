package edu.kmust.bully.search.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import java.net.InetAddress;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/27 17:08
 **/
@Configuration
public class EsConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        //定义客户端的配置对象 RestClient使用9200端口
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("39.101.201.68:9200").withConnectTimeout(120000)
                .withSocketTimeout(120000)
                .build();
        //通过RestClient对象创建
        return RestClients.create(clientConfiguration).rest();
    }
}
