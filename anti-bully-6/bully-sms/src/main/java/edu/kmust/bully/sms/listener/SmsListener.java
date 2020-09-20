package edu.kmust.bully.sms.listener;

import edu.kmust.bully.sms.config.SmsProperties;
import edu.kmust.bully.sms.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Author BunnyAndOak0
 * @Description 对rabbitmq的队列进行监听 并发送验证码
 * @Date 2020/8/16 16:44
 **/
@Component
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties smsProperties;


    /*
     * @Author BunnyAndOak0
     * @Description 监听队列
     * @Date 0:25 2020/8/17
     * @Param [map]
     * @return void
     **/
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BULLY.SMS.QUEUE",
                durable = "true"),
            exchange = @Exchange(value = "BULLY.SMS.EXCHANGE",
                durable = "true",
                type = ExchangeTypes.TOPIC),
            key = {"bully.sendMessage"}
    ))
    public void sendMessage(Map<String, String> map){
        if (CollectionUtils.isEmpty(map)){
            return;
        }
        String phone = map.get("phone");
        String code = map.get("code");
        //判断不为空的话就发送消息
        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(code)){
            smsUtils.sendSms(phone, code, smsProperties.getSignName(),
                    smsProperties.getTemplateCode());
        }
        System.out.println("已发信息！");
    }
}
