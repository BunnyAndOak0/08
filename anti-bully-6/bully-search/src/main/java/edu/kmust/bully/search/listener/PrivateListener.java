package edu.kmust.bully.search.listener;

import edu.kmust.bully.search.service.PrivateSearchService;
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
 * @Date 2020/9/1 7:00
 **/
@Component
public class PrivateListener {

    @Autowired
    PrivateSearchService privateSearchService;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.SAVE.PRIVATE.QUEUE",
                durable = "true"),
            exchange = @Exchange(value = "BULLY.SAVE.PRIVATE.EXCHANGE",
                ignoreDeclarationExceptions = "true",
                type = ExchangeTypes.TOPIC),
            key = {"private.save"}
    ))
    public void save(Long id){
        if (id == null){
            return;
        }
        System.out.println("私有收到消息！");
        privateSearchService.save(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.DELETE.PRIVATE.QUEUE"
                , durable = "true"),
            exchange = @Exchange(value = "BULLY.DELETE.PRIVATE.EXCHANGE"
                , ignoreDeclarationExceptions = "true"
                , type = ExchangeTypes.TOPIC),
            key = {"private.delete"}
    ))
    public void delete(Long id){
        if (id == null){
            return;
        }
        privateSearchService.deletePrivate(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.UPDATE.PRIVATE.QUEUE"
                    , durable = "true"),
            exchange = @Exchange(value = "BULLY.UPDATE.PRIVATE.EXCHANGE"
                    , ignoreDeclarationExceptions = "true"
                    , type = ExchangeTypes.TOPIC),
            key = {"private.update"}
    ))
    public void update(Long id){
        if (id == null){
            return;
        }
        privateSearchService.save(id);
    }

}
