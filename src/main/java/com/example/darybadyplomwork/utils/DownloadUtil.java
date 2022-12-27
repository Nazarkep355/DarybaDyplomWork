package com.example.darybadyplomwork.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DownloadUtil {
    static public String saveImage(MultipartFile file){
        String name = Date.from(Instant.now()).getTime()+Math.random()*Integer.MAX_VALUE+ file.getOriginalFilename();
        try {
            file.transferTo(new File("C://images/"+name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
    static public List<String> savePhotos(MultipartFile[] photos){
        List<String> res = new ArrayList<>();
        for(MultipartFile file: photos){
            res.add(saveImage(file));
        }
        return res;
    }
}
