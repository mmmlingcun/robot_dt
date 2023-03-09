package com.meng.robot_dt.mapper;

import com.meng.robot_dt.entity.Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelMapper {
    int addModel(Model model);
    int deleteModelByModelId(String modelId);
    int updateModel(Model model);
    Model getModelById(String modelId);
    List<Model> getAllModels();
}
