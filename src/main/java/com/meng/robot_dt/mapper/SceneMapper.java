package com.meng.robot_dt.mapper;

import com.meng.robot_dt.entity.Scene;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SceneMapper {
    int addScene(Scene scene);
    int deleteSceneBySceneId(String sceneId);
    int updateScene(Scene scene);
    Scene getSceneById(String sceneId);
    List<Scene> getAllScenes();
}
