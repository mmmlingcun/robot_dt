package com.meng.robot_dt.service;

import com.meng.robot_dt.entity.Model;
import com.meng.robot_dt.mapper.ModelMapper;
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
public class ModelService {
    @Autowired
    ModelMapper modelMapper;
    public int addModel(String model_name, String model_type, String upload_user, String file_format,MultipartFile coverImg, MultipartFile[] modelFiles) throws IOException {
        // 下载封面图和场景json
//        String modelPath = "/userData/robotDT/static/model";
        String filePath = "/static/model";
        String modelPath = "D:\\documents\\temp";

        File path = new File(modelPath);
        //创建上传文件夹
        if (!path.exists()) {
            path.mkdirs();
        }
        //保存文件
        if (modelFiles != null && modelFiles.length > 0) {
            for (MultipartFile file : modelFiles) {
                if (!file.isEmpty()) {
                    try {
                        file.transferTo(new File(modelPath, model_name + "." + file_format));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

//        //上传图片
//        if (!coverImg.isEmpty()) {
//            try {
//                coverImg.transferTo(new File(modelPath, model_name + ".png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // 下载base64编码图片
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(coverImg.getInputStream());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(modelPath+"/"+model_name+".png");
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

        Model model = new Model();

        String file_size = String.format("%.2f", modelFiles[0].getSize()/1024d/1024d).toString()+"MB";
        String file_path = filePath + "/" + model_name +"." +file_format;
        String cover_img = filePath + "/" + model_name +".png";

        model.setModel_name(model_name);
        model.setModel_type(model_type);
        model.setCover_img(cover_img);
        model.setUpload_user(upload_user);
        model.setFile_format(file_format);
        model.setFile_size(file_size);
        model.setFile_path(file_path);

        return modelMapper.addModel(model);
    }

    public int updateModel(Model model){return modelMapper.updateModel(model);}
    public int deleteModelByModelId(String modelId){
        return modelMapper.deleteModelByModelId(modelId);
    }
    public Model getModelById(String modelId){
        return modelMapper.getModelById(modelId);
    }
    public List<Model> getAllModels(){
        return modelMapper.getAllModels();
    }

}
