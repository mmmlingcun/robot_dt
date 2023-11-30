package com.meng.robot_dt.service;

import com.meng.robot_dt.entity.Model;
import com.meng.robot_dt.entity.Scene;
import com.meng.robot_dt.mapper.ModelMapper;
import com.meng.robot_dt.mapper.SceneMapper;
import com.sun.corba.se.impl.encoding.CDROutputObject;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SceneService {
    @Autowired
    SceneMapper sceneMapper;
    public int addScene(String scene_name, String upload_user, String file_format,MultipartFile coverImg, MultipartFile[] sceneFiles) throws IOException {
        // 下载封面图和场景json
//        String scenePath = "/userData/robotDT/static/scene";
        String filePath = "/static/scene";
        String scenePath = "D:\\documents\\temp";

        File path = new File(scenePath);
        //创建上传文件夹
        if (!path.exists()) {
            path.mkdirs();
        }
        //上传文件
        if (sceneFiles != null && sceneFiles.length > 0) {
            for (MultipartFile file : sceneFiles) {
                if (!file.isEmpty()) {
                    try {
                        file.transferTo(new File(scenePath, scene_name + "." + file_format));
                    } catch (IOException e) { 
                        e.printStackTrace();
                    }
                }
            }
        }

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(coverImg.getInputStream());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(scenePath+"/"+scene_name+".png");
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Scene scene = new Scene();

        String file_size = String.format("%.2f", sceneFiles[0].getSize()/1024d/1024d).toString()+"MB";
        String file_path = filePath + "/" + scene_name +"."+file_format;
        String cover_img = filePath + "/" + scene_name +".png";

        scene.setScene_name(scene_name);
        scene.setCover_img(cover_img);
        scene.setUpload_user(upload_user);
        scene.setFile_format(file_format);
        scene.setFile_size(file_size);
        scene.setFile_path(file_path);

        return sceneMapper.addScene(scene);
    }

    public int updateScene(Scene scene){return sceneMapper.updateScene(scene);}
    public int deleteSceneBySceneId(String sceneId){
        return sceneMapper.deleteSceneBySceneId(sceneId);
    }
    public Scene getSceneById(String sceneId){
        return sceneMapper.getSceneById(sceneId);
    }
    public List<Scene> getAllScenes(){
        return sceneMapper.getAllScenes();
    }

}
