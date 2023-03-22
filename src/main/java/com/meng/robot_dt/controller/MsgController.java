package com.meng.robot_dt.controller;

import HslCommunication.Core.Types.OperateResult;
import HslCommunication.Core.Types.OperateResultExOne;
import HslCommunication.Profinet.Siemens.SiemensPLCS;
import HslCommunication.Profinet.Siemens.SiemensS7Net;
import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import com.github.s7connector.impl.serializer.converter.RealConverter;
import com.meng.robot_dt.utils.mqtt.Callback;
import com.meng.robot_dt.utils.mqtt.MQTTConnect;
import org.apache.tomcat.jni.Time;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class MsgController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    MQTTConnect mqttConnect;

    // 模拟设备更新数据
    @GetMapping("/msgset1")
    public String msgSet1() throws InterruptedException {
        stringRedisTemplate.opsForValue().set("ER20","0,90,0,0,0,0");
        stringRedisTemplate.opsForValue().set("ER20(1)","0,90,0,0,0,0");
        stringRedisTemplate.opsForValue().set("ER20(1)(1)","0,90,0,0,0,0");
        stringRedisTemplate.opsForValue().set("ER20(1)(1)(1)","0,90,0,0,0,0");
        int times = 10;
        while (times > 0){
            for (float i = 0; i <= 90; i++) {
                stringRedisTemplate.opsForValue().set("ER20",i + ",90,0,0,0,0");
                stringRedisTemplate.opsForValue().set("ER20(1)",90-i+",90,0,0,0,0");
                stringRedisTemplate.opsForValue().set("ER20(1)(1)",i+",90,0,0,0,0");
                stringRedisTemplate.opsForValue().set("ER20(1)(1)(1)",90-i+",90,0,0,0,0");
                Thread.sleep(16);
            }
            for (float i = 90; i >= 0; i--) {
                stringRedisTemplate.opsForValue().set("ER20",i + ",90,0,0,0,0");
                stringRedisTemplate.opsForValue().set("ER20(1)",90-i+",90,0,0,0,0");
                stringRedisTemplate.opsForValue().set("ER20(1)(1)",i+",90,0,0,0,0");
                stringRedisTemplate.opsForValue().set("ER20(1)(1)(1)",90-i+",90,0,0,0,0");
                Thread.sleep(16);
            }
            times--;
        }
        return "数据驱动";
    }

    // 模拟设备更新数据
    @GetMapping("/msgset2")
    public String msgSet2() throws InterruptedException, MqttException {
        mqttConnect.pub("抛光线传送带","{\"抛光线传送带animation\":true}");
        Thread.sleep(1000);
        mqttConnect.pub("抛光线传送带","{\"抛光线传送带animation\":false}");
        // 0 30 -30 0 0 0
        // 0 -6 -16 0 -67 90
        // ER20connect  抛光线夹爪0.22,0,0.1 角度0,0,0
        //108 -20 19 0 -88 -62
        // 抛光线设备3connect 移动底座0.51,0.31,1.13
        // 抛光线设备3animation

        for (double i = 0; i <= 60; i++) {
            double ER20_1 = 0;
            double ER20_2 = -i*36/60+30;
            double ER20_3 = i*14/60-30;
            double ER20_4 = 0;
            double ER20_5 = -i*67/60;
            double ER20_6 = i*90/60;
            mqttConnect.pub("data","{" +
                    "    \"timestamp\":1660531135," +
                    "    \"clientId\":\"ykg3557\"," +
                    "    \"version\":\"5.0\"," +
                    "    \"devices\":[" +
                    "        {" +
                    "            \"deviceId\":\"ER20\"," +
                    "            \"deviceState\":1," +
                    "            \"deviceData\":{" +
                    "                \"ER20-1\":"+ER20_1+"," +
                    "                \"ER20-2\":"+ER20_2+"," +
                    "                \"ER20-3\":"+ER20_3+"," +
                    "                \"ER20-4\":"+ER20_4+"," +
                    "                \"ER20-5\":"+ER20_5+"," +
                    "                \"ER20-6\":"+ER20_6+"" +
                    "            }" +
                    "        }" +
                    "    ]" +
                    "}");
            Thread.sleep(16);
        }
        Thread.sleep(200);
        mqttConnect.pub("ER20","{\"ER20connect\":true}");
        // 0 -6 -16 0 -67 90
        //108 -20 19 0 -88 -62
        for (float i = 0; i <= 90; i++) {
            float ER20_1 = i*108/90;
            float ER20_2 = -6-i*14/90;
            float ER20_3 = -16+i*35/90;
            float ER20_4 = 0;
            float ER20_5 = -67-i*21/90;
            float ER20_6 = 90-i*152/90;
            mqttConnect.pub("data","{" +
                    "    \"timestamp\":1660531135," +
                    "    \"clientId\":\"ykg3557\"," +
                    "    \"version\":\"5.0\"," +
                    "    \"devices\":[" +
                    "        {" +
                    "            \"deviceId\":\"ER20\"," +
                    "            \"deviceState\":1," +
                    "            \"deviceData\":{" +
                    "                \"ER20-1\":"+ER20_1+"," +
                    "                \"ER20-2\":"+ER20_2+"," +
                    "                \"ER20-3\":"+ER20_3+"," +
                    "                \"ER20-4\":"+ER20_4+"," +
                    "                \"ER20-5\":"+ER20_5+"," +
                    "                \"ER20-6\":"+ER20_6+"" +
                    "            }" +
                    "        }" +
                    "    ]" +
                    "}");
            Thread.sleep(16);
        }
        Thread.sleep(200);
        mqttConnect.pub("抛光线设备3","{\"抛光线设备3connect\":true}");
        Thread.sleep(100);
        mqttConnect.pub("抛光线设备3","{\"抛光线设备3animation\":true}");
        Thread.sleep(8000);
        mqttConnect.pub("ER20","{\"ER20connect\":true}");
        for (float i = 80; i >= 0; i--) {
            float ER20_1 = i*108/80;
            float ER20_2 = -6-i*14/80;
            float ER20_3 = -16+i*35/80;
            float ER20_4 = 0;
            float ER20_5 = -67-i*21/80;
            float ER20_6 = 90-i*152/80;
            mqttConnect.pub("data","{" +
                    "    \"timestamp\":1660531135," +
                    "    \"clientId\":\"ykg3557\"," +
                    "    \"version\":\"5.0\"," +
                    "    \"devices\":[" +
                    "        {" +
                    "            \"deviceId\":\"ER20\"," +
                    "            \"deviceState\":1," +
                    "            \"deviceData\":{" +
                    "                \"ER20-1\":"+ER20_1+"," +
                    "                \"ER20-2\":"+ER20_2+"," +
                    "                \"ER20-3\":"+ER20_3+"," +
                    "                \"ER20-4\":"+ER20_4+"," +
                    "                \"ER20-5\":"+ER20_5+"," +
                    "                \"ER20-6\":"+ER20_6+"" +
                    "            }" +
                    "        }" +
                    "    ]" +
                    "}");
            Thread.sleep(16);
        }
        return "数据驱动";
    }

    // 模拟设备更新数据
    @GetMapping("/msgplc")
    public String msgPlc() throws InterruptedException, MqttException {
        //PLC地址
        String ipAddress = "127.0.0.1";
        //默认端口
        int port = 102;
        S7Connector s7Connector = S7ConnectorFactory
                .buildTCPConnector()
                .withHost(ipAddress)
                .withPort(port)
//                .withTimeout(10000) //连接超时时间
                .withRack(0)
                .withSlot(1)
                .build();
        //第一个参数：DaveArea.DB 表示读取PLC的地址区域为DB
        //第二个参数：DB地址，若plc中是DB1082，则填1082
        //第三个参数：数据长度
        //第四个参数：偏移量
//        byte[] plcData = s7Connector.read(DaveArea.DB, 33, 8, 24);
//        String str = new String(plcData);
//        System.out.println("读取到的数据=" + str);
        RealConverter realConverter = new RealConverter();
//        StringConverter converter = new StringConverter();
//        IntegerConverter converter1 = new IntegerConverter();
        while(true){
            byte[] data1 = s7Connector.read(DaveArea.DB, 33, 16, 72);
            Float edata1 = realConverter.extract(Float.class, data1, 0, 0);
            byte[] data2 = s7Connector.read(DaveArea.DB, 33, 16, 76);
            Float edata2 = realConverter.extract(Float.class, data2, 0, 0)+90;
            byte[] data3 = s7Connector.read(DaveArea.DB, 33, 16, 80);
            Float edata3 = realConverter.extract(Float.class, data3, 0, 0);
            byte[] data4 = s7Connector.read(DaveArea.DB, 33, 16, 84);
            Float edata4 = realConverter.extract(Float.class, data4, 0, 0);
            byte[] data5 = s7Connector.read(DaveArea.DB, 33, 16, 88);
            Float edata5 = realConverter.extract(Float.class, data5, 0, 0);
            byte[] data6 = s7Connector.read(DaveArea.DB, 33, 16, 92);
            Float edata6 = realConverter.extract(Float.class, data6, 0, 0);
            System.out.println(edata1+","+edata2+","+edata3+","+edata4+","+edata5+","+edata6);
//            Thread.sleep(20);
            mqttConnect.pub("data","{" +
                    "    \"timestamp\":1660531135," +
                    "    \"clientId\":\"ykg3557\"," +
                    "    \"version\":\"5.0\"," +
                    "    \"devices\":[" +
                    "        {" +
                    "            \"deviceId\":\"ER20\"," +
                    "            \"deviceState\":1," +
                    "            \"deviceData\":{" +
//                        "                \"ER20-1\":"+data0+"," +
//                        "                \"ER20-2\":"+data1+"," +
//                        "                \"ER20-3\":"+data2+"," +
//                        "                \"ER20-4\":"+data3+"," +
//                        "                \"ER20-5\":"+data4+"," +
//                        "                \"ER20-6\":"+data5+"," +
                    "                \"ER20-1_1\":"+edata1+"," +
                    "                \"ER20-2_1\":"+edata2+"," +
                    "                \"ER20-3_1\":"+edata3+"," +
                    "                \"ER20-4_1\":"+edata4+"," +
                    "                \"ER20-5_1\":"+edata5+"," +
                    "                \"ER20-6_1\":"+edata6+"" +
//                        "                \"ER20-1_2\":"+data0_2+"," +
//                        "                \"ER20-2_2\":"+data1_2+"," +
//                        "                \"ER20-3_2\":"+data2_2+"," +
//                        "                \"ER20-4_2\":"+data3_2+"," +
//                        "                \"ER20-5_2\":"+data4_2+"," +
//                        "                \"ER20-6_2\":"+data5_2+"," +
//                        "                \"ER20-1_3\":"+data0_3+"," +
//                        "                \"ER20-2_3\":"+data1_3+"," +
//                        "                \"ER20-3_3\":"+data2_3+"," +
//                        "                \"ER20-4_3\":"+data3_3+"," +
//                        "                \"ER20-5_3\":"+data4_3+"," +
//                        "                \"ER20-6_3\":"+data5_3+"" +
                    "            }" +
                    "        }" +
                    "    ]" +
                    "}");
        }
//        return "plc读取";
    }

    @GetMapping("/msgplc2")
    public String msgPlc2() throws InterruptedException, MqttException {
        SiemensS7Net siemensTcpNet = new SiemensS7Net(SiemensPLCS.S1200, "192.168.150.60");
        siemensTcpNet.SetPersistentConnection();
        while (true){
            OperateResultExOne<float[]> er20_1 = siemensTcpNet.ReadFloat( "DB33.24", (short)6 );
            OperateResultExOne<float[]> er20_2 = siemensTcpNet.ReadFloat( "DB33.72", (short)6 );
            OperateResultExOne<float[]> er20_3 = siemensTcpNet.ReadFloat( "DB33.120", (short)6 );
            OperateResultExOne<float[]> er20_4 = siemensTcpNet.ReadFloat( "DB33.168", (short)6 );
//            OperateResultExOne<boolean[]> tool = siemensTcpNet.ReadBool("Q3.1",(short) 4);
//            if (er20_2.IsSuccess)
//            {
                float data0 = er20_1.Content[0];
                float data1 = er20_1.Content[1]+90;
                float data2 = er20_1.Content[2];
                float data3 = er20_1.Content[3];
                float data4 = er20_1.Content[4];
                float data5 = er20_1.Content[5];
                System.out.println("ER20:"+data0+","+data1+","+data2+","+data3+","+data4+","+data5);
//            boolean tool1 = tool.Content[1];
//                boolean tool2 = tool.Content[2];
//                boolean tool3 = tool.Content[3];
//                boolean tool4 = tool.Content[4];
//                System.out.println(tool1+","+tool2+","+tool3+","+tool4);
                float data0_1 = er20_2.Content[0];
                float data1_1 = er20_2.Content[1]+90;
                float data2_1 = er20_2.Content[2];
                float data3_1 = er20_2.Content[3];
                float data4_1 = er20_2.Content[4];
                float data5_1 = er20_2.Content[5];
                System.out.println("ER20-1:"+data0_1+","+data1_1+","+data2_1+","+data3_1+","+data4_1+","+data5_1);
                float data0_2 = er20_3.Content[0];
                float data1_2 = er20_3.Content[1]+90;
                float data2_2 = er20_3.Content[2];
                float data3_2 = er20_3.Content[3];
                float data4_2 = er20_3.Content[4];
                float data5_2 = er20_3.Content[5];
                System.out.println("ER20-2:"+data0_2+","+data1_2+","+data2_2+","+data3_2+","+data4_2+","+data5_2);
                float data0_3 = er20_4.Content[0];
                float data1_3 = er20_4.Content[1]+90;
                float data2_3 = er20_4.Content[2];
                float data3_3 = er20_4.Content[3];
                float data4_3 = er20_4.Content[4];
                float data5_3 = er20_4.Content[5];
                System.out.println("ER20-3:"+data0_3+","+data1_3+","+data2_3+","+data3_3+","+data4_3+","+data5_3);
                mqttConnect.pub("data","{" +
                        "    \"timestamp\":1660531135," +
                        "    \"clientId\":\"ykg3557\"," +
                        "    \"version\":\"5.0\"," +
                        "    \"devices\":[" +
                        "        {" +
                        "            \"deviceId\":\"ER20\"," +
                        "            \"deviceState\":1," +
                        "            \"deviceData\":{" +
//                        "                \"tool1\":"+tool1+"," +
//                        "                \"tool2\":"+tool1+"," +
//                        "                \"tool3\":"+tool1+"," +
//                        "                \"tool4\":"+tool1+"," +
                        "                \"ER20-1\":"+data0+"," +
                        "                \"ER20-2\":"+data1+"," +
                        "                \"ER20-3\":"+data2+"," +
                        "                \"ER20-4\":"+data3+"," +
                        "                \"ER20-5\":"+data4+"," +
                        "                \"ER20-6\":"+data5+"," +
                        "                \"ER20-1_1\":"+data0_1+"," +
                        "                \"ER20-2_1\":"+data1_1+"," +
                        "                \"ER20-3_1\":"+data2_1+"," +
                        "                \"ER20-4_1\":"+data3_1+"," +
                        "                \"ER20-5_1\":"+data4_1+"," +
                        "                \"ER20-6_1\":"+data5_1+"," +
                        "                \"ER20-1_2\":"+data0_2+"," +
                        "                \"ER20-2_2\":"+data1_2+"," +
                        "                \"ER20-3_2\":"+data2_2+"," +
                        "                \"ER20-4_2\":"+data3_2+"," +
                        "                \"ER20-5_2\":"+data4_2+"," +
                        "                \"ER20-6_2\":"+data5_2+"," +
                        "                \"ER20-1_3\":"+data0_3+"," +
                        "                \"ER20-2_3\":"+data1_3+"," +
                        "                \"ER20-3_3\":"+data2_3+"," +
                        "                \"ER20-4_3\":"+data3_3+"," +
                        "                \"ER20-5_3\":"+data4_3+"," +
                        "                \"ER20-6_3\":"+data5_3+"" +
                        "            }" +
                        "        }" +
                        "    ]" +
                        "}");
//            break;

//            }
//            Thread.sleep(10);
        }
//        siemensTcpNet.ConnectClose();
//        return "a";
    }
}
