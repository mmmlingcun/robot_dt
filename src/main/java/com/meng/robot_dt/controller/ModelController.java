package com.meng.robot_dt.controller;

import com.meng.robot_dt.entity.Model;
import com.meng.robot_dt.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class ModelController {
    @Autowired
    ModelService modelService;
    @RequestMapping("/addModel")
    public int addModel(HttpServletRequest request,
                        @RequestParam(value = "modelFiles") MultipartFile[] modelFiles,
                        @RequestParam(value = "coverImg") MultipartFile coverImg) throws IOException {
        String model_name = request.getParameter("model_name");
        String model_type = request.getParameter("model_type");
        String upload_user = request.getParameter("upload_user");
        String file_format = request.getParameter("file_format");

        Date dNow = new Date( );
//        Model model = new Model(new Integer(idD),"10001",sceneName,"model22","/model1.jpg",createUser,dNow,"stl","23MB","/model1.stl",0);
        return modelService.addModel(model_name,model_type,upload_user,file_format,coverImg,modelFiles);
    }

    @RequestMapping("/updateModel")
    public int updateModel(){
        Date dNow = new Date( );
        Model model = new Model(1,"10001","model2","model22","/model1.jpg","meng",dNow,"stl","23MB","/model1.stl",0);
        return modelService.updateModel(model);
    }

    @RequestMapping(value = "/deleteModelByModelId",method = RequestMethod.POST)
    public int deleteModelByModelId(String model_id){
        System.out.println(model_id);
        return modelService.deleteModelByModelId(model_id);
    }

    @RequestMapping(value ="/getModelById",method = RequestMethod.GET)
    public String getModelById(String model_id){
        Model model = modelService.getModelById(model_id);
        return model.toString();
    }

    @RequestMapping("/getAllModels")
    public String getAllModels(){
        List<Model> models = modelService.getAllModels();
        return models.toString();
    }
}
