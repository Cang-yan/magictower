package com.yibingo.race.core.service.mq;

import com.yibingo.race.common.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangyunxiong on 2018/5/29.
 */
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

//    public void send(Object message){
//        String msg = RedisService.beanToString(message);
//        log.info("send message:"+msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE, message);
//    }

    	public void sendTopic(Object message) {
		String msg = RedisUtils.beanToString(message);
		log.info("send topic message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
	}

    /**
     * 发送优先购秒杀消息
     * @param message
     */
    public void sendPriorSeckillMessage(SeckillMessage message){
        String msg = RedisUtils.beanToString(message);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.PRIOR_SECKILL_QUEUE, msg);

    }


    /**
     * 发送秒杀消息
     * @param message
     */
	public void sendSeckillMessage(SeckillMessage message){
        String msg = RedisUtils.beanToString(message);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);

    }


}
