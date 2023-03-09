package com.meng.robot_dt.service;

import com.meng.robot_dt.entity.DTEntity;
import com.meng.robot_dt.mapper.DTEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTEntityService {
    @Autowired
    DTEntityMapper dtEntityMapper;
    public int addDTType(DTEntity dtEntity){return dtEntityMapper.addDTType(dtEntity);}
    public int deleteDTTypeById(Integer dtTypeId){return dtEntityMapper.deleteDTTypeById(dtTypeId);}
    public int updateDTType(DTEntity dtEntity){return dtEntityMapper.updateDTType(dtEntity);}
    public DTEntity getDTTypeById(Integer dtTypeId){return dtEntityMapper.getDTTypeById(dtTypeId);}
    public List<DTEntity> getAllDTTypes(){return dtEntityMapper.getAllDTTypes();}

    public int addDTEntity(DTEntity dtEntity){return dtEntityMapper.addDTEntity(dtEntity);}
    public int deleteDTEntityById(Integer dtEntityId){return dtEntityMapper.deleteDTEntityById(dtEntityId);}
    public int updateDTEntity(DTEntity dtEntity){return dtEntityMapper.updateDTEntity(dtEntity);}
    public DTEntity getDTEntityById(Integer dtEntityId){return dtEntityMapper.getDTEntityById(dtEntityId);}
    public List<DTEntity> getDTEntityByTypeId(Integer dtTypeId){return dtEntityMapper.getDTEntityByTypeId(dtTypeId);}
    public List<DTEntity> getAllDTEntitys(){return dtEntityMapper.getAllDTEntitys();}

    public int addAttribute(DTEntity dtEntity){return dtEntityMapper.addAttribute(dtEntity);}
    public int deleteAttributeById(Integer attributeId){return dtEntityMapper.deleteAttributeById(attributeId);}
    public int updateAttribute(DTEntity dtEntity){return dtEntityMapper.updateAttribute(dtEntity);}
    public DTEntity getAttributeById(Integer attributeId){return dtEntityMapper.getAttributeById(attributeId);}
    public List<DTEntity> getAttributeByTypeId(Integer entity_type_id){return dtEntityMapper.getAttributeByTypeId(entity_type_id);}
    public List<DTEntity> getAllAttributes(){return dtEntityMapper.getAllAttributes();}

    public List<DTEntity> getValueTypeById(Integer TypeId){return dtEntityMapper.getValueTypeById(TypeId);}
    public List<DTEntity> getAllValueTypes(){return dtEntityMapper.getAllValueTypes();}

    public int addValue(DTEntity dtEntity){return dtEntityMapper.addValue(dtEntity);}
    public int deleteValueById(DTEntity dtEntity){return dtEntityMapper.deleteValueById(dtEntity);}
    public int updateValue(DTEntity dtEntity){return dtEntityMapper.updateValue(dtEntity);}
    public DTEntity getValueById(DTEntity dtEntity){return dtEntityMapper.getValueById(dtEntity);}
    public List<DTEntity> getAllValue(Integer value_type_id){return dtEntityMapper.getAllValue(value_type_id);}

}
