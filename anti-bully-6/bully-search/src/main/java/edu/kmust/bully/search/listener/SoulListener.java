package edu.kmust.bully.search.listener;

import edu.kmust.bully.search.service.SoulSearchService;
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
 * @Date 2020/9/3 14:32
 **/
@Component
public class SoulListener {
    @Autowired
    SoulSearchService soulSearchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.SAVE.SOUL.QUEUE",
                    durable = "true") ,
            exchange = @Exchange(value = "BULLY.SAVE.SOUL.EXCHANGE",
                    type = ExchangeTypes.TOPIC),
            key = {"soul.save"}
    ))
    public void save(Long id){
        if (id == null){
            return;
        }
        System.out.println("辅导区收到消息");
        soulSearchService.save(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.DELETE.SOUL.QUEUE",
                    durable = "true"),
            exchange = @Exchange(value = "BULLY.DELETE.SOUL.EXCHANGE",
                    type = ExchangeTypes.TOPIC),
            key = {"soul.delete"}
    ))
    public void delete(Long id){
        if (id == null){
            return;
        }
        soulSearchService.deleteSoul(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.UPDATE.SOUL.QUEUE",
                    durable = "true")  ,
            exchange = @Exchange(value = "BULLY.UPDATE.SOUL.EXCHANGE",
                    type = ExchangeTypes.TOPIC),
            key = {"soul.update"}
    ))
    public void update(Long id){
        if (id == null){
            return;
        }
        soulSearchService.save(id);
    }
}
