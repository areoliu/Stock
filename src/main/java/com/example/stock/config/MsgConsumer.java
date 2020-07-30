package com.example.stock.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.api.service.StockService;
import com.example.stock.Constant;
import com.example.stock.entity.Order;
import com.example.stock.entity.QueueMessage;
import com.example.stock.service.impl.StockServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = Constant.CONSUMER_GROUP, topic =Constant.TOPIC )
public class MsgConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    StockServiceImpl stockService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(MessageExt messageExt) {

        String msg = null;
        try{
            msg = new String(messageExt.getBody(),"utf-8");
//            Gson gson=new Gson();
            QueueMessage queueMessage= JSONObject.parseObject(msg,QueueMessage.class);
            String orderData=JSON.parse(JSONObject.toJSONString(queueMessage.getMsgText())).toString();
            log.info("MsgConsumer 消费者 >>> " +orderData);
            Order order = JSONObject.parseObject(orderData,Order.class);
            String key=""+order.getId();
            if(stringRedisTemplate.opsForValue().get(key)!=null){
                stockService.updateStock(order.getOrderAmout(),order.getOrderSku());
                stringRedisTemplate.delete(key);
            }else{
                log.info("MsgConsumer 已消费 >>> " + msg +", msgId = " + messageExt.getMsgId());
            }
//            stockService.updateStock(order.getOrderAmout(),order.getOrderSku());

        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("MsgConsumer 消费者 >>> " + msg +", msgId = " + messageExt.getMsgId());
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {

    }
}
