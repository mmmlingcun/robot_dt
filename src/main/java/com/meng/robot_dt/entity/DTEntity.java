package com.meng.robot_dt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DTEntity {
    private Integer entity_id;
    private String entity_name;
    private Integer entity_type_id;
    private String entity_type_name;
    private Integer attribute_id;
    private String attribute_name;
    private String attribute_note;
    private String is_required;
    private String example_data;
    private Integer value_type_id;
    private String value_type;
    private Integer value_id;
    private String value;

    @Override
    public String toString() {
        Map<String,String> map = new HashMap<String,String>();
        if(entity_id!=null) {
            map.put("\"entity_id\"","\""+entity_id+"\"");
        }
        if(entity_name!=null) {
            map.put("\"entity_name\"","\""+entity_name+"\"");
        }
        if(entity_type_id!=null) {
            map.put("\"entity_type_id\"","\""+entity_type_id+"\"");
        }
        if(entity_type_name!=null) {
            map.put("\"entity_type_name\"","\""+entity_type_name+"\"");
        }
        if(attribute_id!=null) {
            map.put("\"attribute_id\"","\""+attribute_id+"\"");
        }
        if(attribute_name!=null) {
            map.put("\"attribute_name\"","\""+attribute_name+"\"");
        }
        if(attribute_note!=null) {
            map.put("\"attribute_note\"","\""+attribute_note+"\"");
        }
        if(is_required!=null) {
            map.put("\"is_required\"","\""+is_required+"\"");
        }
        if(example_data!=null) {
            try{
                Object obj = JSON.parse(example_data);
                map.put("\"example_data\"",example_data);
            }catch (Exception e){
                map.put("\"example_data\"","\""+example_data+"\"");
            }
        }
        if(value_type_id!=null) {
            map.put("\"value_type_id\"","\""+value_type_id+"\"");
        }
        if(value_type!=null) {
            map.put("\"value_type\"","\""+value_type+"\"");
        }
        if(value_id!=null) {
            map.put("\"value_id\"","\""+value_id+"\"");
        }
        if(value!=null) {
            map.put("\"value\"","\""+value+"\"");
        }
        return map.toString().replaceAll("=",":");
    }
}
