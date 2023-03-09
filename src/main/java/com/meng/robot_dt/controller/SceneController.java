package com.meng.robot_dt.controller;

import com.meng.robot_dt.entity.Scene;
import com.meng.robot_dt.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class SceneController {
    @Autowired
    SceneService sceneService;


    @RequestMapping("/getAllScenes")
    public String getAllScenes(){
        List<Scene> scenes = sceneService.getAllScenes();
        return scenes.toString();
    }

    @RequestMapping("/addScene")
    public int addScene(HttpServletRequest request,
                        @RequestParam(value = "sceneFiles") MultipartFile[] sceneFiles,
                        @RequestParam(value = "coverImg") MultipartFile coverImg) throws IOException {
        String scene_name = request.getParameter("scene_name");
        String upload_user = request.getParameter("upload_user");
        String file_format = request.getParameter("file_format");
//        Model model = new Model(new Integer(idD),"10001",sceneName,"model22","/model1.jpg",createUser,dNow,"stl","23MB","/model1.stl",0);
        return sceneService.addScene(scene_name,upload_user,file_format,coverImg,sceneFiles);
    }
}
