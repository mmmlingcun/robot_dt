package com.meng.robot_dt.mapper;

import com.meng.robot_dt.entity.DTEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DTEntityMapper {
    // 孪生类增删改查
    int addDTType(DTEntity dtEntity);
    int deleteDTTypeById(Integer dtTypeId);
    int updateDTType(DTEntity dtEntity);
    DTEntity getDTTypeById(Integer dtTypeId);
    List<DTEntity> getAllDTTypes();

    // 孪生体增删改查
    int addDTEntity(DTEntity dtEntity);
    int deleteDTEntityById(Integer dtEntityId);
    int updateDTEntity(DTEntity dtEntity);
    DTEntity getDTEntityById(Integer dtEntityId);
    List<DTEntity> getDTEntityByTypeId(Integer dtTypeId);
    List<DTEntity> getAllDTEntitys();

    // 数据类型查询
    List<DTEntity> getValueTypeById(Integer TypeId);
    List<DTEntity> getAllValueTypes();

    // 属性表增删改查
    int addAttribute(DTEntity dtEntity);
    int deleteAttributeById(Integer attributeId);
    int updateAttribute(DTEntity dtEntity);
    DTEntity getAttributeById(Integer attributeId);
    List<DTEntity> getAttributeByTypeId(Integer entity_type_id);
    List<DTEntity> getAllAttributes();

    // 数据值
    int addValue(DTEntity dtEntity);
    int deleteValueById(DTEntity dtEntity);
    int updateValue(DTEntity dtEntity);
    DTEntity getValueById(DTEntity dtEntity);
    List<DTEntity> getAllValue(Integer value_type_id);

    // 综合查询
    // 孪生类属性表
    List<DTEntity> getAllAttribute(Integer entity_type_id);
    // 孪生体集合表
    List<DTEntity> getAllDT(Integer entity_type_id);
}
