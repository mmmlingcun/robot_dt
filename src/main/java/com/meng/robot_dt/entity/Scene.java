package com.meng.robot_dt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scene {
    private Integer idD;
    private String scene_id;
    private String scene_name;
    private String cover_img;
    private String upload_user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date upload_time;
    private String file_format;
    private String file_size;
    private String file_path;
    private Integer download_times;

    @Override
    public String toString() {
        return "{\"sceneId\":\""+scene_id+
                "\",\"sceneName\":\""+scene_name+
                "\",\"coverImg\":\""+cover_img+
                "\",\"uploadUser\":\""+upload_user+
                "\",\"uploadTime\":\""+upload_time+
                "\",\"fileFormat\":\""+file_format+
                "\",\"fileSize\":\""+file_size+
                "\",\"filePath\":\""+file_path+
                "\",\"downloadTimes\":\""+download_times+
                "\"}";
    }
}
