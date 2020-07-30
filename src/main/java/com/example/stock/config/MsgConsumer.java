package com.example.stock.config;

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
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = Constant.CONSUMER_GROUP, topic =Constant.TOPIC )
public class MsgConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    StockServiceImpl stockService;

    @Override
    public void onMessage(MessageExt messageExt) {
        String msg = null;
        try{
            msg = new String(messageExt.getBody(),"utf-8");
            Gson gson=new Gson();
            QueueMessage queueMessage= gson.fromJson(msg,QueueMessage.class);
            log.info("MsgConsumer 消费者 >>> " +gson.toJson(queueMessage.getMsgText()));
            Order order = gson.fromJson(gson.toJson(queueMessage.getMsgText().replaceAll("\\\\", "")),Order.class);
            stockService.updateStock(order.getOrderAmout(),order.getOrderSku());
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("MsgConsumer 消费者 >>> " + msg +", msgId = " + messageExt.getMsgId());
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {

    }
}
