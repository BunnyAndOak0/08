package edu.kmust.bully.diary.controller;

import edu.kmust.bully.common.exception.FileSizeException;
import edu.kmust.bully.common.exception.FileTypeException;
import edu.kmust.bully.common.exception.FileUploadException;
import edu.kmust.bully.diary.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class uploadController {

    @Value("${project.image-upload-path}")
    private String path;//E:/upload

    @Value("${project.image-host}")
    private String host;//http://localhost:8081/

    @Value("${project.image-maxSize}")
    private Integer maxSize;//307200

    @Value("${project.image-contentType}")
    List<String> contentTypes = new ArrayList<>();

    @Autowired
    SimpleDateFormat sdf;

    @PostMapping("/upload-img")
    public R upload(MultipartFile imageFile){
        long size = imageFile.getSize();
        if(size > maxSize){
            throw new FileSizeException("上传文件大小超过" + maxSize/1024 + "k");
        }

        String contentType = imageFile.getContentType();
        if(!contentTypes.contains(contentType)){
            throw new FileTypeException("请上传"+ contentTypes+"类型的文件");
        }

        String format = sdf.format(new Date());
        //图片在硬盘中的真实地址:E:/upload/2020/2/4
        String realPath = path + "/" + format;

        File folder = new File(realPath);

        //文件加不存在
        if(!folder.exists()){
            folder.mkdirs();
        }

        String oldName = imageFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));

        try {
            imageFile.transferTo(new File(folder, newName));
        } catch (IOException e) {
            throw new FileUploadException("文件上传失败（´Д`）,请稍后再试!");
        }
        String imgURL = host + format + "/" + newName;//网络路径

        return R.ok(imgURL);
    }

}
