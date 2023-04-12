package com.meng.robot_dt.controller;

import com.meng.robot_dt.entity.DTEntity;
import com.meng.robot_dt.service.DTEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class DTController {
    @Autowired
    DTEntityService dtEntityService;

    @RequestMapping(value = "/addDTType", method = RequestMethod.GET)
    public String addDTType(String entity_type_name){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setEntity_type_name(entity_type_name);
        try {
            dtEntityService.addDTType(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "add success!\n" + dtEntity.toString();
    }

    @RequestMapping(value = "/deleteDTTypeById", method = RequestMethod.GET)
    public String deleteDTTypeById(Integer entity_type_id){
        try {
            dtEntityService.deleteDTTypeById(entity_type_id);
        }catch (Exception e){
            return e.toString();
        }
        return "delete success!";
    }

    @RequestMapping(value = "/updateDTType", method = RequestMethod.GET)
    public String updateDTType(Integer entity_type_id,String entity_type_name){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setEntity_type_id(entity_type_id);
        dtEntity.setEntity_type_name(entity_type_name);
        try {
            dtEntityService.updateDTType(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "update success\n" + dtEntity.toString();
    }

    @RequestMapping(value = "/getDTTypeById", method = RequestMethod.GET)
    public String getDTTypeById(Integer entity_type_id){
        DTEntity dtEntity = dtEntityService.getDTTypeById(entity_type_id);
        return dtEntity.toString();
    }

    @RequestMapping("/getAllDTTypes")
    public String getAllDTTypes(){
        List<DTEntity> dtEntities = dtEntityService.getAllDTTypes();
        return dtEntities.toString();
    }

    @RequestMapping(value = "/addDTEntity", method = RequestMethod.GET)
    public String addDTEntity(Integer entity_id,String entity_name,Integer entity_type_id){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setEntity_id(entity_id);
        dtEntity.setEntity_name(entity_name);
        dtEntity.setEntity_type_id(entity_type_id);
        try {
            dtEntityService.addDTEntity(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "add success!\n" + dtEntity.toString();
    }

    @RequestMapping(value = "/deleteDTEntityById", method = RequestMethod.GET)
    public String deleteDTEntityById(Integer entity_id){
        try {
            dtEntityService.deleteDTEntityById(entity_id);
        }catch (Exception e){
            return e.toString();
        }
        return "delete success!";
    }

    @RequestMapping(value = "/updateDTEntity", method = RequestMethod.GET)
    public String updateDTEntity(Integer entity_id,String entity_name,Integer entity_type_id){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setEntity_id(entity_id);
        dtEntity.setEntity_name(entity_name);
        dtEntity.setEntity_type_id(entity_type_id);
        try {
            dtEntityService.updateDTEntity(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "update success\n" + dtEntity.toString();
    }

    @RequestMapping(value = "/getDTEntityById", method = RequestMethod.GET)
    public String getDTEntityById(Integer entity_id){
        DTEntity dtEntity = dtEntityService.getDTEntityById(entity_id);
        return dtEntity.toString();
    }

    @RequestMapping(value = "/getDTEntityByTypeId", method = RequestMethod.GET)
    public String getDTEntityByTypeId(Integer entity_type_id){
        List<DTEntity> dtEntities = dtEntityService.getDTEntityByTypeId(entity_type_id);
        return dtEntities.toString();
    }

    @RequestMapping("/getAllDTEntitys")
    public String getAllDTEntitys(){
        List<DTEntity> dtEntities = dtEntityService.getAllDTEntitys();
        return dtEntities.toString();
    }

    @RequestMapping(value = "/getValueTypeById", method = RequestMethod.GET)
    public String getValueTypeById(Integer entity_type_id){
        List<DTEntity> dtEntities = dtEntityService.getValueTypeById(entity_type_id);
        return dtEntities.toString();
    }

    @RequestMapping("/getAllValueTypes")
    public String getAllValueTypes(){
        List<DTEntity> dtEntities = dtEntityService.getAllValueTypes();
        return dtEntities.toString();
    }

    @RequestMapping(value = "/addAttribute", method = RequestMethod.GET)
    public String addAttribute(Integer attribute_id,String attribute_name,Integer entity_type_id,Integer value_type_id,String attribute_note){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setAttribute_id(attribute_id);
        dtEntity.setAttribute_name(attribute_name);
        dtEntity.setEntity_type_id(entity_type_id);
        dtEntity.setValue_type_id(value_type_id);
        dtEntity.setAttribute_note(attribute_note);
        try {
            dtEntityService.addAttribute(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "add success!\n" + dtEntity.toString();
    }
    @RequestMapping(value = "/deleteAttributeById", method = RequestMethod.GET)
    public String deleteAttributeById(Integer attribute_id){
        try {
            dtEntityService.deleteAttributeById(attribute_id);
        }catch (Exception e){
            return e.toString();
        }
        return "delete success!";
    }
    @RequestMapping(value = "/updateAttribute", method = RequestMethod.GET)
    public String updateAttribute(Integer attribute_id,String attribute_name,Integer entity_type_id,Integer value_type_id,String attribute_note){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setAttribute_id(attribute_id);
        dtEntity.setAttribute_name(attribute_name);
        dtEntity.setEntity_type_id(entity_type_id);
        dtEntity.setValue_type_id(value_type_id);
        dtEntity.setAttribute_note(attribute_note);
        try {
            dtEntityService.updateAttribute(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "update success\n" + dtEntity.toString();
    }
    @RequestMapping(value = "/getAttributeById", method = RequestMethod.GET)
    public String getAttributeById(Integer attributeId){
        DTEntity dtEntity = dtEntityService.getAttributeById(attributeId);
        return dtEntity.toString();
    }
    @RequestMapping(value = "/getAttributeByTypeId", method = RequestMethod.GET)
    public String getAttributeByTypeId(Integer entity_type_id){
        List<DTEntity> dtEntities = dtEntityService.getAttributeByTypeId(entity_type_id);
        return dtEntities.toString();
    }
    @RequestMapping("/getAllAttributes")
    public String getAllAttributes(){
        List<DTEntity> dtEntities = dtEntityService.getAllAttributes();
        return dtEntities.toString();
    }


    @RequestMapping(value = "/addValue", method = RequestMethod.GET)
    public String addValue(Integer value_id,Integer entity_id,Integer attribute_id,Integer value_type_id,String value){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setEntity_id(entity_id);
        dtEntity.setAttribute_id(attribute_id);
        dtEntity.setValue_type_id(value_type_id);
        dtEntity.setValue(value);
        try {
            dtEntityService.addValue(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "add success!\n" + dtEntity.toString();
    }
    @RequestMapping(value = "/deleteValueById", method = RequestMethod.GET)
    public String deleteValueById(Integer value_id,Integer entity_id,Integer attribute_id,Integer value_type_id){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setValue_id(value_id);
        dtEntity.setEntity_id(entity_id);
        dtEntity.setAttribute_id(attribute_id);
        dtEntity.setValue_type_id(value_type_id);
        try {
            dtEntityService.deleteValueById(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "delete success!";
    }
    @RequestMapping(value = "/updateValue", method = RequestMethod.GET)
    public String updateValue(Integer value_id,Integer entity_id,Integer attribute_id,Integer value_type_id,String value){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setValue_id(value_id);
        dtEntity.setEntity_id(entity_id);
        dtEntity.setAttribute_id(attribute_id);
        dtEntity.setValue_type_id(value_type_id);
        dtEntity.setValue(value);
        try {
            dtEntityService.updateValue(dtEntity);
        }catch (Exception e){
            return e.toString();
        }
        return "update success\n" + dtEntity.toString();
    }
    @RequestMapping(value = "/getValueById", method = RequestMethod.GET)
    public String getValueById(Integer value_id,Integer entity_id,Integer attribute_id,Integer value_type_id){
        DTEntity dtEntity = new DTEntity();
        dtEntity.setValue_id(value_id);
        dtEntity.setEntity_id(entity_id);
        dtEntity.setAttribute_id(attribute_id);
        dtEntity.setValue_type_id(value_type_id);
        DTEntity dtEntity1 = dtEntityService.getValueById(dtEntity);
        return dtEntity1.toString();
    }
    @RequestMapping(value="/getAllValue", method = RequestMethod.GET)
    public String getAllValue(Integer value_type_id){
        List<DTEntity> dtEntities = dtEntityService.getAllValue(value_type_id);
        return dtEntities.toString();
    }

    @RequestMapping(value="/getAllAttribute", method = RequestMethod.GET)
    public String getAllAttribute(Integer entity_type_id){
        List<DTEntity> dtEntities = dtEntityService.getAllAttribute(entity_type_id);
        return dtEntities.toString();
    }

    @RequestMapping(value="/getAllDT", method = RequestMethod.GET)
    public String getAllDT(Integer entity_type_id){
        List<DTEntity> dtEntities = dtEntityService.getAllDT(entity_type_id);
        return dtEntities.toString();
    }
}
