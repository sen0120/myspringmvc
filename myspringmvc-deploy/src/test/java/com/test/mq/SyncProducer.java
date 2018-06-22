package com.test.mq;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SyncProducer {
    public static void main(String[] args) throws Exception {
//        send2();
        send1();

    }

    private static void send1() throws MQClientException, UnsupportedEncodingException, RemotingException, MQBrokerException, InterruptedException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("producer0");
        producer.setNamesrvAddr("192.168.1.109:9876");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 1; i++) {
            //Create a message instance, specifying topic, tag and message body.
            String message = "Hello RocketMQ " + i + "  " + new SimpleDateFormat("mm:ss.SSS").format(new Date());
            Message msg = new Message("Topic3" /* Topic */,
                    "TagA" /* Tag */,
                    (message).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf(message + "%s%n", sendResult);
            Thread.sleep(1000L);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

    private static void send2() throws MQClientException, UnsupportedEncodingException, RemotingException, MQBrokerException, InterruptedException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("producer0");
        producer.setNamesrvAddr("192.168.1.109:9876");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 3; i++) {
            //Create a message instance, specifying topic, tag and message body.
            String message = "Hello RocketMQ " + i + "  " + new SimpleDateFormat("mm:ss.SSS").format(new Date());
            Message msg = new Message("Topic1" /* Topic */,
                    "TagA" /* Tag */,
                    (message).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf(message + "%s%n", sendResult);
            Thread.sleep(1000L);
        }
        for (int i = 0; i < 3; i++) {
            //Create a message instance, specifying topic, tag and message body.
            String message = "Hello RocketMQ " + i + "  " + new SimpleDateFormat("mm:ss.SSS").format(new Date());
            Message msg = new Message("Topic1",
                    "TagB",
                    (message).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf(message + "%s%n", sendResult);
            Thread.sleep(1000L);
        }
        for (int i = 0; i < 3; i++) {
            //Create a message instance, specifying topic, tag and message body.
            String message = "Hello RocketMQ " + i + "  " + new SimpleDateFormat("mm:ss.SSS").format(new Date());
            Message msg = new Message("Topic2" /* Topic */,
                    "TagA" /* Tag */,
                    (message).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf(message + "%s%n", sendResult);
            Thread.sleep(1000L);
        }
        for (int i = 0; i < 3; i++) {
            //Create a message instance, specifying topic, tag and message body.
            String message = "Hello RocketMQ " + i + "  " + new SimpleDateFormat("mm:ss.SSS").format(new Date());
            Message msg = new Message("Topic2",
                    "TagB",
                    (message).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf(message + "%s%n", sendResult);
            Thread.sleep(1000L);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }


}