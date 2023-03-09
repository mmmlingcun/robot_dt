package com.meng.robot_dt.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.StringRedisTemplate;
@Component
@Scope("prototype")
public class RedisReader{
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 接受位置数据，判断是否异常
     */
    public Boolean positionCheck(String key,String[] joint){
        switch (key){
            case "robot1" :
                if (Float.parseFloat(joint[0]) < -170 || Float.parseFloat(joint[0]) > 170
                        || Float.parseFloat(joint[1]) < -100 || Float.parseFloat(joint[1]) > 135
                        || Float.parseFloat(joint[2]) < -200 || Float.parseFloat(joint[2]) > 70
                        || Float.parseFloat(joint[3]) < -270 || Float.parseFloat(joint[3]) > 270
                        || Float.parseFloat(joint[4]) < -130 || Float.parseFloat(joint[4]) > 130
                        || Float.parseFloat(joint[5]) < -400 || Float.parseFloat(joint[5]) > 400
                        || Float.parseFloat(joint[6]) < -1500 || Float.parseFloat(joint[6]) > 1200
                ) {
                    return false;
                }
                return true;
            case "robot2":
                if(Float.parseFloat(joint[0]) < -170 || Float.parseFloat(joint[0]) > 170
                        || Float.parseFloat(joint[1]) < -100 || Float.parseFloat(joint[1]) > 135
                        || Float.parseFloat(joint[2]) < -200 || Float.parseFloat(joint[2]) > 70
                        || Float.parseFloat(joint[3]) < -270 || Float.parseFloat(joint[3]) > 270
                        || Float.parseFloat(joint[4]) < -130 || Float.parseFloat(joint[4]) > 130
                        || Float.parseFloat(joint[5]) < -400 || Float.parseFloat(joint[5]) > 400
                ){
                    return false;
                }
                return true;
            default: return true;
        }
    }

    public String toJSON(String key,String[] joint){
        switch (key){
            case "robot1":
                return "{\n" +
                        "        \"joint1\":" + joint[0] + ",\n" +
                        "        \"joint2\":" + joint[1] + ",\n" +
                        "        \"joint3\":" + joint[2] + ",\n" +
                        "        \"joint4\":" + joint[3] + ",\n" +
                        "        \"joint5\":" + joint[4] + ",\n" +
                        "        \"joint6\":" + joint[5] + ",\n" +
                        "        \"position\":" + joint[6] + ",\n" +
                        "        \"tool\":" + joint[7] + ",\n" +
                        "        \"state\":\"" + state + "\"\n" +
                        "}";
            case "robot2":
                return "{\n" +
                        "        \"joint1\":" + joint[0] + ",\n" +
                        "        \"joint2\":" + joint[1] + ",\n" +
                        "        \"joint3\":" + joint[2] + ",\n" +
                        "        \"joint4\":" + joint[3] + ",\n" +
                        "        \"joint5\":" + joint[4] + ",\n" +
                        "        \"joint6\":" + joint[5] + ",\n" +
                        "        \"tool\":" + joint[6] + ",\n" +
                        "        \"state\":\"" + state + "\"\n" +
                        "}";
            case "lathe":
                return "{\n" +
                        "        \"status\":" + joint[0] + ",\n" +
                        "        \"state\":\"" + state + "\"\n" +
                        "}";
            case "ER20-1":
                return "{\n" +
                        "        \"rotation\":[" + joint[0] + ","+ joint[1] + ","+ joint[2] + "]\n" +
                        "}";
            case "ER20":
                return "{\n" +
                        "        \"ER20-1\":" + joint[0] + ",\n" +
                        "        \"ER20-2\":" + joint[1] + ",\n" +
                        "        \"ER20-3\":" + joint[2] + ",\n" +
                        "        \"ER20-4\":" + joint[3] + ",\n" +
                        "        \"ER20-5\":" + joint[4] + ",\n" +
                        "        \"ER20-6\":" + joint[5] + "\n" +
                        "}";
            case "ER20(1)":
                return "{\n" +
                        "        \"ER20-1_1\":" + joint[0] + ",\n" +
                        "        \"ER20-2_1\":" + joint[1] + ",\n" +
                        "        \"ER20-3_1\":" + joint[2] + ",\n" +
                        "        \"ER20-4_1\":" + joint[3] + ",\n" +
                        "        \"ER20-5_1\":" + joint[4] + ",\n" +
                        "        \"ER20-6_1\":" + joint[5] + "\n" +
                        "}";
            case "ER20(1)(1)":
                return "{\n" +
                        "        \"ER20-1_2\":" + joint[0] + ",\n" +
                        "        \"ER20-2_2\":" + joint[1] + ",\n" +
                        "        \"ER20-3_2\":" + joint[2] + ",\n" +
                        "        \"ER20-4_2\":" + joint[3] + ",\n" +
                        "        \"ER20-5_2\":" + joint[4] + ",\n" +
                        "        \"ER20-6_2\":" + joint[5] + "\n" +
                        "}";
            case "ER20(1)(1)(1)":
                return "{\n" +
                        "        \"ER20-1_3\":" + joint[0] + ",\n" +
                        "        \"ER20-2_3\":" + joint[1] + ",\n" +
                        "        \"ER20-3_3\":" + joint[2] + ",\n" +
                        "        \"ER20-4_3\":" + joint[3] + ",\n" +
                        "        \"ER20-5_3\":" + joint[4] + ",\n" +
                        "        \"ER20-6_3\":" + joint[5] + "\n" +
                        "}";
            case "ER20(1)(1)(1)(1)":
                return "{\n" +
                        "        \"ER20-1_4\":" + joint[0] + ",\n" +
                        "        \"ER20-2_4\":" + joint[1] + ",\n" +
                        "        \"ER20-3_4\":" + joint[2] + ",\n" +
                        "        \"ER20-4_4\":" + joint[3] + ",\n" +
                        "        \"ER20-5_4\":" + joint[4] + ",\n" +
                        "        \"ER20-6_4\":" + joint[5] + "\n" +
                        "}";
            default:return "";
        }
    }
    /**
     * get获取redis的string数据类型
     * key：joint
     * 内容类似：”1,2,3,4,5,6“
     * 如果内容没变化，则返回"stop"，不发布新消息
     */
    private String oldMsg = "";
    public String state = "normal";
    public String getMsg(String key){
        String msg = stringRedisTemplate.opsForValue().get(key);
        System.out.println(msg);
        if(msg == null){
            return "stop";
        }
        if(!msg.equals(oldMsg)){
            oldMsg = msg;
            String[] joint = msg.split(",");
            if(!positionCheck(key,joint)){
                state = "error";
            }else{
                state = "normal";
            }
            return toJSON(key,joint);
        }else {
            return "stop";
        }
    }
}