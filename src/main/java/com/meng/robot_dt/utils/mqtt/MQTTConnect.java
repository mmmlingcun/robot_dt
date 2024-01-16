package com.meng.robot_dt.utils.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

/**
 * MQTT工具类操作
 */
@Slf4j
@Component
public class MQTTConnect {

//    private String HOST = "ws://127.0.0.1:8083/mqtt"; //mqtt服务器的地址和端口号
    private String HOST = "ws://121.41.65.127:8083/mqtt"; //mqtt服务器的地址和端口号
    //    private final String clientId = "DC" + (int) (Math.random() * 100000000);
    private final String clientId = "localjava";
    private MqttClient mqttClient;

    /**
     * 客户端connect连接mqtt服务器
     *
     * @param userName     用户名
     * @param passWord     密码
     * @param mqttCallback 回调函数
     **/
    public void setMqttClient(String userName, String passWord, MqttCallback mqttCallback) throws MqttException {
//        userName = "localjava";
//        passWord = "mqttjavaadmin";
        MqttConnectOptions options = mqttConnectOptions(userName, passWord);
        if (mqttCallback == null) {
            mqttClient.setCallback(new Callback());
        } else {
            mqttClient.setCallback(mqttCallback);
        }
        mqttClient.connect(options);
    }

    /**
     * MQTT连接参数设置
     */
    private MqttConnectOptions mqttConnectOptions(String userName, String passWord) throws MqttException {
        mqttClient = new MqttClient(HOST, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(10);///默认：30
        options.setAutomaticReconnect(true);//默认：false
        options.setCleanSession(false);//默认：true
        //options.setKeepAliveInterval(20);//默认：60
        return options;
    }

    /**
     * 关闭MQTT连接
     */
    public void close() throws MqttException {
        mqttClient.close();
        mqttClient.disconnect();
    }

    /**
     * 向某个主题发布消息 默认qos：1
     *
     * @param topic:发布的主题
     * @param msg：发布的消息
     */
    public void pub(String topic, String msg) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        //mqttMessage.setQos(2);
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
        token.waitForCompletion();
    }

    /**
     * 向某个主题发布消息
     *
     * @param topic: 发布的主题
     * @param msg:   发布的消息
     * @param qos:   消息质量    Qos：0、1、2
     */
    public void pub(String topic, String msg, int qos) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
        token.waitForCompletion();
    }

    /**
     * 订阅某一个主题 ，此方法默认的的Qos等级为：1
     *
     * @param topic 主题
     */
    public void sub(String topic) throws MqttException {
        mqttClient.subscribe(topic);
    }

    /**
     * 订阅某一个主题，可携带Qos
     *
     * @param topic 所要订阅的主题
     * @param qos   消息质量：0、1、2
     */
    public void sub(String topic, int qos) throws MqttException {
        mqttClient.subscribe(topic, qos);
    }

    /**
     * 六轴速度json数据
     */
    public String msg_joint(int j1,int j2,int j3,int j4,int j5,int j6){
        return "{\n" +
                "        \"joint1\":" + j1 + ",\n" +
                "        \"joint2\":" + j2 + ",\n" +
                "        \"joint3\":" + j3 + ",\n" +
                "        \"joint4\":" + j4 + ",\n" +
                "        \"joint5\":" + j5 + ",\n" +
                "        \"joint6\":" + j6 + "\n" +
                "}";
    }

    /**
     * main函数自己测试用
     */
//    public static void main(String[] args) throws MqttException {
//        MQTTConnect mqttConnect = new MQTTConnect();
//        mqttConnect.setMqttClient("javaadmin", "mqttjavaadmin", new Callback());
//        mqttConnect.sub("joint_msg",0);
//        mqttConnect.pub("joint_msg", mqttConnect.msg_joint(1,0,0,0,0,0));
//    }
}