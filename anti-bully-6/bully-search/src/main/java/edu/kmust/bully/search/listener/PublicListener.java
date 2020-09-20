package edu.kmust.bully.search.listener;

import edu.kmust.bully.search.service.PublicSearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/9/1 6:59
 **/
@Component
public class PublicListener {
    @Autowired
    PublicSearchService publicSearchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.SAVE.PUBLIC.QUEUE"
                , durable = "true"),
            exchange = @Exchange(value = "BULLY.SAVE.PUBLIC.EXCHANGE"
                , ignoreDeclarationExceptions = "true"
                , type = ExchangeTypes.TOPIC),
            key = {"public.save"}
    ))
    public void save(Long id){
        if (id == null){
            return;
        }
        System.out.println("收到公开消息了哦！");
        publicSearchService.save(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.DELETE.PUBLIC.QUEUE"
                , durable = "true"),
            exchange = @Exchange(value = "BULLY.DELETE.PUBLIC.EXCHANGE"
                , ignoreDeclarationExceptions = "true"
                , type = ExchangeTypes.TOPIC),
            key = {"public.delete"}
    ))
    public void delete(Long id){
        if (id == null){
            return;
        }
        publicSearchService.deletePublic(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.UPDATE.PUBLIC.QUEUE"
                    , durable = "true"),
            exchange = @Exchange(value = "BULLY.UPDATE.PUBLIC.EXCHANGE"
                    , ignoreDeclarationExceptions = "true"
                    , type = ExchangeTypes.TOPIC),
            key = {"public.update"}
    ))
    public void update(Long id){
        if (id == null){
            return;
        }
        publicSearchService.save(id);
    }
}
